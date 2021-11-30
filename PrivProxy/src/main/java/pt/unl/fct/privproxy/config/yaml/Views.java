package pt.unl.fct.privproxy.config.yaml;

import pt.unl.fct.privproxy.config.yaml.ViewConfig;

import java.util.List;

public class Views {

    private List<ViewConfig> views;

    public Views(List<ViewConfig> views) {
        this.views = views;
    }

    public Views(){}

    public List<ViewConfig> getViews() {
        return views;
    }

    public void setViews(List<ViewConfig> views) {
        this.views = views;
    }



}
