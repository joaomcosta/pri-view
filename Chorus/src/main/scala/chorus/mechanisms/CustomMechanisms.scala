/*
 * Author: Joao Costa
 * Pri-view: Privacy-preserving views for data analysis
 */

package chorus.mechanisms

import chorus.util.DB

object CustomMechanisms {

  /*
  * Returns laplace noise scaled to a certain value.
  */
  def laplaceSample(scale: Double): Double = {
    val u = 0.5 - scala.util.Random.nextDouble()
    val noise = -math.signum(u) * scale * math.log(1 - 2*math.abs(u))
    noise
  }

  /*
  * Adds laplace noise to a specific column of all rows
  */
  def laplaceColumn(vals: List[DB.Row], scale: Double, column: Integer) : List[DB.Row] = 
    vals.map { (row: DB.Row) => 
      DB.Row(row.vals.updated(column, 
        (row.vals(column).toDouble + laplaceSample(scale)).toString))
    }

}
