package pt.unl.fct.privproxy.model;

import chorus.sql.relational_algebra.Relation;

import java.util.List;

public class ProxyView {

    private String id;

    private String viewString;

    private Relation viewRoot;

    private ViewType type;

    private Integer colNumber;

    private Double epsilon;

    private Integer lowerBound;

    private Integer upperBound;

    private String description;

    private List<Analyst> permissions;

    public ProxyView(String id, String viewString, Relation viewRoot, ViewType type, Integer colNumber, Double epsilon,
                     Integer lowerBound, Integer upperBound, String description, List<Analyst> permissions) {
        this.id = id;
        this.viewString = viewString;
        this.viewRoot = viewRoot;
        this.type = type;
        this.colNumber = colNumber;
        this.epsilon = epsilon;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.description = description;
        this.permissions = permissions;
    }

    public ProxyView(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewString() {
        return viewString;
    }

    public void setViewString(String viewString) {
        this.viewString = viewString;
    }

    public Relation getViewRoot() {
        return viewRoot;
    }

    public void setViewRoot(Relation viewRoot) {
        this.viewRoot = viewRoot;
    }

    public ViewType getType() {
        return type;
    }

    public void setType(ViewType type) {
        this.type = type;
    }

    public Integer getColNumber() {
        return colNumber;
    }

    public void setColNumber(Integer colNumber) {
        this.colNumber = colNumber;
    }

    public Double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(Double epsilon) {
        this.epsilon = epsilon;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = upperBound;
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
