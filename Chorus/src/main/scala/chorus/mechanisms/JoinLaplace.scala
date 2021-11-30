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
import chorus.sql.QueryParser
import chorus.rewriting.differential_privacy.{ElasticSensitivityConfig, ElasticSensitivityRewriter}

/*
* Adds Laplace noise to a Join (only Counts aggr) query.
* Uses ElasticSensitivy to calculate noise to be added.
*/
class JoinLaplace(query: String, config: ElasticSensitivityConfig)
    extends ChorusMechanism[List[DB.Row]] {

  /*
  * Rewrites the query using ElasticSensitivity and runs it in the database
  * Default Delta : 1 / (math.pow(100000,2))
  */
  def run() = {
    val rewrittenQuery = new ElasticSensitivityRewriter(config).run(query) 

    //Postgres bug
    //val newQuery = rewrittenQuery.toSql().replace("RAND()", "RANDOM()")
    // val root2 = QueryParser.parseToRelTree(rewrittenQuery, config.database)

    val result = DB.execute(rewrittenQuery.root, config.database)

    (result, EpsilonDPCost(config.epsilon))
  }

}
