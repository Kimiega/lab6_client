package connection;

import collection.City;

import java.io.Serializable;

public class NetPackage implements Serializable {
    private String cmd = "";
    private String arg = null;
    private City city = null;
    public String getCmd() {
        return cmd;
    }

    public String getArg() {
        return arg;
    }

    public City getCity() {
        return city;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
