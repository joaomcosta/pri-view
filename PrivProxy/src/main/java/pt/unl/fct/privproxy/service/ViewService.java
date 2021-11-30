package pt.unl.fct.privproxy.service;

import chorus.mechanisms.*;
import chorus.rewriting.RewriterConfig;
import chorus.rewriting.differential_privacy.ElasticSensitivityConfig;
import chorus.schema.Database;
import chorus.util.DB;
import pt.unl.fct.privproxy.config.yaml.Connection;
import pt.unl.fct.privproxy.exception.MethodNotImplementedException;
import pt.unl.fct.privproxy.exception.NotFoundException;
import pt.unl.fct.privproxy.model.AnalyzeViewResult;
import pt.unl.fct.privproxy.model.ProxyView;
import pt.unl.fct.privproxy.model.ProxyViewResume;
import pt.unl.fct.privproxy.model.ViewResult;
import scala.collection.Iterator;
import scala.collection.immutable.List;

import java.util.ArrayList;
import java.util.Map;

//@Service
public class ViewService {

    private Connection connection;

    private Database database;

    private Map<String, ProxyView> viewsMap;

    private RewriterConfig rewriterConfig;


    public ViewService(Connection connection, Database database, Map<String, ProxyView> viewsMap){
        this.connection = connection;
        this.database = database;
        this.viewsMap = viewsMap;
        this.rewriterConfig = new RewriterConfig(database);
    }

    public ViewService(){}


    public ArrayList<ProxyViewResume> listAvailableViews(){
        ArrayList<ProxyViewResume> views = new ArrayList<>(viewsMap.size());

        for (ProxyView view : viewsMap.values()) {
            views.add(new ProxyViewResume(view.getId(), view.getViewString(), view.getDescription()));
        }

        return views;
    }

    public ViewResult readView(String viewID){
        ProxyView view = viewsMap.get(viewID);

        if(view == null) {
           throw new NotFoundException();
        }

        List<DB.Row> result;
        PrivacyAccountant accountant = new EpsilonCompositionAccountant();

        switch (view.getType()){
            case COUNT:
                result = new SimpleLaplace(view.getEpsilon(), view.getViewRoot(), view.getColNumber(),
                        this.rewriterConfig).execute(accountant);
                break;
            case SUM:
                result = new SumLaplace(view.getEpsilon(), view.getLowerBound(), view.getUpperBound(),
                        view.getViewRoot(), view.getColNumber(), this.rewriterConfig).execute(accountant);
                break;
            case AVG:
                result = new AvgLaplace(view.getEpsilon(), view.getLowerBound(), view.getUpperBound(),
                        view.getViewRoot(), view.getColNumber(), this.rewriterConfig).execute(accountant);
                break;
            case JOIN:
                //Experimental
                double defaultDelta = 1 / (Math.pow(100000, 2));
                ElasticSensitivityConfig config = new ElasticSensitivityConfig(view.getEpsilon(), defaultDelta, database, false);
                result = new JoinLaplace(view.getViewString(), config).execute(accountant);
                break;
            default:
                throw new MethodNotImplementedException();
        }
        return new ViewResult(toJavaList(result));
    }

    public AnalyzeViewResult readViewAnalyze(String viewID){
        long startTime = System.currentTimeMillis();
        ViewResult viewResult = readView(viewID);
        long elapsedTime = (System.currentTimeMillis() - startTime);
        return new AnalyzeViewResult(viewResult.getResult(), (double) elapsedTime);
    }

    private ArrayList<ArrayList<String>> toJavaList(List<DB.Row> results){
        ArrayList<ArrayList<String>> auxResults =  new ArrayList<ArrayList<String>>(results.length());

        Iterator<DB.Row> iterator = results.iterator();
        while (iterator.hasNext()){
            List<String> row = iterator.next().vals();
            ArrayList<String> auxRow = new ArrayList<String>(row.length());
            Iterator<String> auxIterator = row.iterator();

            while (auxIterator.hasNext()){
              auxRow.add(auxIterator.next());
            }
            auxResults.add(auxRow);
        }

        return auxResults;
    }

}
