package pt.unl.fct.privproxy;

import chorus.mechanisms.EpsilonCompositionAccountant;
import chorus.mechanisms.PrivacyAccountant;
import chorus.mechanisms.SimpleLaplace;
import chorus.mechanisms.SumLaplace;
import chorus.rewriting.RewriterConfig;
import chorus.schema.Database;
import chorus.schema.Schema;
import chorus.sql.QueryParser;
import chorus.sql.relational_algebra.Relation;
import chorus.util.DB;
import scala.collection.immutable.List;

public class Test {

    public static void main(String[] args) {

        System.setProperty("dp.elastic_sensitivity.check_bins_for_release", "false");
        System.setProperty("db.use_dummy_database", "false");

        System.setProperty("db.driver", "org.postgresql.Driver");
        System.setProperty("db.url", "jdbc:postgresql://localhost:8080/tpch?&ssl=false");
        System.setProperty("db.username", "postgres");
        System.setProperty("db.password", "My:pass");


        System.setProperty("schema.config.path", "src/main/resources/schema.yaml");
        Database database = Schema.getDatabase("tpch");

        RewriterConfig config = new RewriterConfig(database);

        String query = "select l_returnflag, l_linestatus, count(*) as count_order from lineitem where l_shipdate <= date '1998-09-01' group by l_returnflag, l_linestatus order by l_returnflag, l_linestatus";

        Relation root1 = QueryParser.parseToRelTree(query, database);

        PrivacyAccountant accountant = new EpsilonCompositionAccountant();

        List<DB.Row> result = new SimpleLaplace(0.1, root1, 2, config).execute(accountant);

        System.out.println(result);

    }


}
