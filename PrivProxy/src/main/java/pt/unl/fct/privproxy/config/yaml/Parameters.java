package pt.unl.fct.privproxy.config.yaml;

public class Parameters {

    private String type;

    private Integer colNumber;

    private Double epsilon;

    private Integer lowerBound;

    private Integer UpperBound;

    public Parameters(String type, Integer colNumber, Double epsilon, Integer lowerBound, Integer upperBound) {
        this.type = type;
        this.colNumber = colNumber;
        this.epsilon = epsilon;
        this.lowerBound = lowerBound;
        UpperBound = upperBound;
    }

    public Parameters() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
        return UpperBound;
    }

    public void setUpperBound(Integer upperBound) {
        UpperBound = upperBound;
    }



}