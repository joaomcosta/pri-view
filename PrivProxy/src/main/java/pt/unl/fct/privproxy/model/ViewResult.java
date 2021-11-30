package pt.unl.fct.privproxy.model;

import java.util.ArrayList;
import java.util.List;

public class ViewResult {

    private ArrayList<ArrayList<String>> result;

    public ViewResult(ArrayList<ArrayList<String>> result) {
        this.result = result;
    }

    public ArrayList<ArrayList<String>> getResult() {
        return result;
    }

    public void setResult(ArrayList<ArrayList<String>> result) {
        this.result = result;
    }
}
