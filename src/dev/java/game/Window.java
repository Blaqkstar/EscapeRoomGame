package game;

/**
 * Game item class
 */
public class Window extends Item {
    public Window(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        this.setUsed(true); // used!
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You try and open the window but it won't budge.");
    }
}
