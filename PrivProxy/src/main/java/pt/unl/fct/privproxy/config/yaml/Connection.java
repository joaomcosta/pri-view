package pt.unl.fct.privproxy.config.yaml;

public class Connection {

    private String driver;

    private String url;

    private String database;

    private String username;

    private String password;

    public Connection(String driver, String url, String database, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public Connection(){}

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
