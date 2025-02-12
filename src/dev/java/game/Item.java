package game;

public class Item {
    private String name;
    private String description;

    // Detailed description that is given when inspected
    private String inspection;

    // Keeps track of if item has been observed yet
    private boolean observed;

    public Item(String name, String description, String inspection) {
        this.name = name;
        this.description = description;
        this.inspection = inspection;
        this.observed = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setObserved(boolean observed) {
        this.observed = observed;
    }

    public boolean isObserved() {
        return observed;
    }

    public String getInspection() {return inspection + "\n";}
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
