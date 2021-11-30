package pt.unl.fct.privproxy.config;

import chorus.schema.Database;
import chorus.schema.Schema;
import chorus.sql.QueryParser;
import chorus.sql.relational_algebra.Relation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.unl.fct.privproxy.config.yaml.Connection;
import pt.unl.fct.privproxy.config.yaml.Parameters;
import pt.unl.fct.privproxy.config.yaml.ViewConfig;
import pt.unl.fct.privproxy.config.yaml.Views;
import pt.unl.fct.privproxy.model.ProxyView;
import pt.unl.fct.privproxy.model.ViewType;
import pt.unl.fct.privproxy.service.ViewService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProxyConfig {

    @Bean(name = "ViewService")
    ViewService viewService() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        //Connection configurations
        Connection connection = mapper.readValue(new File("src/main/resources/connection.yaml"), Connection.class);
        setChorusProperties(connection);
        Database database = Schema.getDatabase(connection.getDatabase());

        //View configurations
        Views views = mapper.readValue(new File("src/main/resources/views.yaml"), Views.class);
        Map<String, ProxyView> viewsMap = loadViewsAndTransform(views, database);

        return new ViewService(connection, database, viewsMap);
    }

    private void setChorusProperties(Connection connection){
        System.setProperty("dp.elastic_sensitivity.check_bins_for_release", "false");
        System.setProperty("db.use_dummy_database", "false");

        System.setProperty("db.driver", connection.getDriver());
        System.setProperty("db.url", connection.getUrl());
        System.setProperty("db.username", connection.getUsername());
        System.setProperty("db.password", connection.getPassword());
    }

    private Map<String, ProxyView> loadViewsAndTransform(Views views, Database database){
        List<ViewConfig> viewList = views.getViews();
        Map<String, ProxyView> viewsMap = new HashMap<>();

        for(ViewConfig viewConfig : viewList){
            String viewId = viewConfig.getId();
            String query = fileToString(viewConfig.getFile());
            Relation viewRoot = QueryParser.parseToRelTree(query, database);
            Parameters parameters = viewConfig.getParameters();
            ProxyView proxyView = new ProxyView(viewId, query, viewRoot, ViewType.valueOf(parameters.getType()),
                    parameters.getColNumber(), parameters.getEpsilon(), parameters.getLowerBound(), parameters.getUpperBound(),
                    viewConfig.getDescription(), viewConfig.getPermissions());

            viewsMap.put(viewId, proxyView);
        }

        return viewsMap;
    }

     public static String fileToString(String fileName){

        String query = "";

        try{
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/views/" + fileName));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            query = sb.toString();

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return query;
    }


}
