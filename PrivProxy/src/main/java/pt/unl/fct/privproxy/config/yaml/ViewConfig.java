package pt.unl.fct.privproxy.config.yaml;

import pt.unl.fct.privproxy.config.yaml.Parameters;
import pt.unl.fct.privproxy.model.Analyst;

import java.util.List;

public class ViewConfig {

    private String id;

    private String file;

    private Parameters parameters;

    private String description;

    private List<Analyst> permissions;

    public ViewConfig(String id, String file, Parameters parameters, String description, List<Analyst> permissions) {
        this.id = id;
        this.file = file;
        this.parameters = parameters;
        this.description = description;
        this.permissions = permissions;
    }

    public ViewConfig(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Analyst> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Analyst> permissions) {
        this.permissions = permissions;
    }


}
