package pt.unl.fct.privproxy.model;

public class ProxyViewResume {

    private String id;

    private String query;

    private String description;

    public ProxyViewResume(String id, String query, String description) {
        this.id = id;
        this.query = query;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
