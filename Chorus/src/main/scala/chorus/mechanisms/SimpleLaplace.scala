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
* Adds Laplace noise (scaled to 1/E) to a COUNT query.
* Epsilon : privacy parameter value (0.1 recommended)
* Column : column to add noise
*/
class SimpleLaplace(epsilon: Double, root: Relation, column: Integer, config: RewriterConfig)
    extends ChorusMechanism[List[DB.Row]] {

  /*
  * Executes the query in the database and adds noise to a specific column
  */
  def run() = {
    val scale = 1 / epsilon
    val result = DB.execute(root, config.database)
    (CustomMechanisms.laplaceColumn(result, scale, column), EpsilonDPCost(epsilon))
  }

}
