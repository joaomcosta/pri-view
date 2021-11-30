/*
 * Author: Joao Costa
 * Pri-view: Privacy-preserving views for data analysis
 */

package chorus.mechanisms

import chorus.schema.Database
import chorus.rewriting.RewriterConfig
import chorus.sql.relational_algebra.Relation
import chorus.rewriting.differential_privacy.ClippingRewriter
import chorus.util.DB

/*
* Adds Laplace noise (scaled to (u-l)/E) to a COUNT query.
* Epsilon : privacy parameter value (0.1 recommended)
* L: lower bound
* U : Upper bound
* Column : column to add noise
*/
class SumLaplace(epsilon: Double, l: Double, u: Double,
  root: Relation, column: Integer, config: RewriterConfig)
    extends ChorusMechanism[List[DB.Row]] {

  /*
  * Executes the query in the database and adds noise to a specific column
  * Query is clipped to lie between the upper and lower bounds
  * Sensitivity = (upper - lower) / epsilon
  */
  def run() = {
    val clippedQuery = new ClippingRewriter(config, l, u).run(root).root
    val scale = ((u - l) / epsilon)
    val result = DB.execute(clippedQuery, config.database)
    (CustomMechanisms.laplaceColumn(result, scale, column), EpsilonDPCost(epsilon))
  }

}
