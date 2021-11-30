/*
 * Author: Joao Costa
 * Pri-view: Privacy-preserving views for data analysis
 */

package chorus.mechanisms

import chorus.analysis.differential_privacy.GlobalSensitivityAnalysis
import chorus.schema.Database
import chorus.rewriting.RewriterConfig
import chorus.rewriting.differential_privacy.ClippingRewriter
import chorus.util.DB
import chorus.sql.relational_algebra.{RelUtils, Relation}
import chorus.dataflow.domain.UnitDomain
import org.apache.calcite.rel.core.Aggregate
import org.apache.calcite.sql.fun.SqlSumAggFunction
import chorus.exception.UnsupportedQueryException

import chorus.rewriting.rules.ColumnDefinition._
import chorus.rewriting.rules.Operations._
import chorus.rewriting.rules.ValueExpr
import chorus.rewriting.rules.Expr._

/*
* Adds Laplace noise to an AVG query.
* Epsilon : privacy parameter value (0.1 recommended)
* L: lower bound
* U : Upper bound
* Column : column to add noise
*/
class AvgLaplace(epsilon: Double, l: Double, u: Double,
  root: Relation, column: Integer, config: RewriterConfig)
    extends ChorusMechanism[List[DB.Row]] {

  def replaceAgg(root: Relation, aggFn: String) = {
    root.rewriteRecursive(UnitDomain) { (node, orig, _) =>
      node match {
        case Relation(a: Aggregate) => {
          val groupedCols = RelUtils.getGroupedCols(a)

          val origAlias = a.getRowType.getFieldNames.get(groupedCols.length)
          val origCol = a.getInput().getRowType.getFieldNames.get(groupedCols.length)

          val finalAgg = aggFn match {
            case "sum" => Sum(col(origCol)) AS origAlias
            case "count" => Count(col(origCol)) AS origAlias
          }

          val newR = Relation(a.getInput).agg(groupedCols: _*)(finalAgg)

          (newR, ())
        }
        case _ => (node, ())
      }
    }
  }

  /*
  * Average is divided into sum and count queries. Average value is calculated in the last step.
  */
  def run() = {
    val sumQuery = replaceAgg(root, "sum")
    val countQuery = replaceAgg(root, "count")

    val (sumResult, epCost1) = new SumLaplace(epsilon/2.0, l, u, sumQuery, column, config).run()
    val (countResult, epCost2) = new SimpleLaplace(epsilon/2.0, countQuery, column, config).run()

    val results = (sumResult zip countResult).map {
      case (DB.Row(vs1), DB.Row(vs2)) => 
        DB.Row(vs1.updated(column, (vs1(column).toDouble / vs2(column).toDouble).toString))
    }

    (results, epCost1 + epCost2)
  }

}
