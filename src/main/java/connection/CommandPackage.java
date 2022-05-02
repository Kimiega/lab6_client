package connection;

import cmd.ICommand;

public class CommandPackage {
    private String name;
    private String description;
    private boolean hasArg;
    private boolean hasObject;

    public CommandPackage(String name, String description, boolean hasArg, boolean hasObject){
        this.name = name;
        this.description = description;
        this.hasArg = hasArg;
        this.hasObject = hasObject;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public boolean isHasObject() {
        return hasObject;
    }
}
