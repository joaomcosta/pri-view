package pt.unl.fct.privproxy.model;

import chorus.util.DB;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeViewResult extends ViewResult{

    private Double executionTime;

    public AnalyzeViewResult(ArrayList<ArrayList<String>> result, Double executionTime) {
        super(result);
        this.executionTime = executionTime;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }
}
