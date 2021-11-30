package examples

import chorus.schema.Schema
import chorus.sql.QueryParser
import chorus.mechanisms.LaplaceMechClipping
import chorus.mechanisms.SimpleLaplace
import chorus.mechanisms.AverageMechClipping
import chorus.mechanisms.EpsilonCompositionAccountant
import chorus.rewriting.RewriterConfig
import chorus.rewriting.differential_privacy.{ElasticSensitivityConfig, ElasticSensitivityRewriter}
import chorus.sql.relational_algebra.RelUtils
import chorus.util.DB

object Test extends App {
  System.setProperty("dp.elastic_sensitivity.check_bins_for_release", "false")
  System.setProperty("db.use_dummy_database", "false")

  System.setProperty("db.driver", "org.postgresql.Driver");
  System.setProperty("db.url", "jdbc:postgresql://localhost:8080/tpch?&ssl=false");
  System.setProperty("db.username", "postgres");
  System.setProperty("db.password", "My:pass")

  // Use the table schemas and metadata defined by the test classes
  System.setProperty("schema.config.path", "src/main/resources/schema.yaml")
  val database = Schema.getDatabase("tpch")

  val EPSILON = 0.1
  val DELTA = 1 / (math.pow(100000,2))
  
  def printQuery(query: String) = println(s"\n  " + query.replaceAll("\\n", s"\n  ") + "\n")

   val config = new ElasticSensitivityConfig(EPSILON, DELTA, database, false)

  // val config = new RewriterConfig(database)

  // Define simple test queries
  val query = "query here"

  //val root1 = QueryParser.parseToRelTree(query1, database)

  val rewrittenQuery = new ElasticSensitivityRewriter(config).run(query)  

  val newQuery = rewrittenQuery.toSql().replace("RAND()", "RANDOM()")
  printQuery(newQuery)

  val root2 = QueryParser.parseToRelTree(newQuery, database)

  val result = DB.execute(root2, config.database)

  print(result)

  // Define the privacy accountant
  // val accountant = new EpsilonCompositionAccountant()

  // Run the mechanisms
  //val r1 = new SimpleLaplace(0.1, root1, 1, config).execute(accountant)
  //val r1 = new LaplaceTest(0.1, 0, 0, root1, config).execute(accountant)

  //println("Count query result: " + r1)

  //println("Total privacy cost: " + accountant.getTotalCost())
}
