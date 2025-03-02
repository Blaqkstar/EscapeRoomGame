package game;

public abstract class Item {
    private String name;
    private String description;
    private boolean used;


    // Detailed description that is given when inspected
    private String inspection;

    // Keeps track of if item has been observed yet
    private boolean observed;

    public Item(String name, String description, String inspection) {
        this.name = name;
        this.description = description;
        this.inspection = inspection;
        this.observed = false;
        this.used = false;
    }

    public String getName() {
        if (used) {
            return name;
        } else {
            return name;
        }
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {this.description = description;}

    public void setObserved(boolean observed) {
        this.observed = observed;
    }

    public boolean isObserved() {
        return observed;
    }

    public String getInspection() {return inspection;}
    public void setInspection(String inspection) {this.inspection = inspection;}
    public boolean isUsed() {return used;}
    public void setUsed(boolean used) {this.used = used;}
    @Override
    public String toString() {
        return name + ": " + description;
    }

    /// -------------------------------------------{ METHODS BELOW }---------------------------------

    // abstract use method to be implemented by subclasses
    public abstract void use();
}
