package game;

public class Window extends Item {
    public Window(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        System.out.println("You try and open the window but it won't budge.");
    }
}
