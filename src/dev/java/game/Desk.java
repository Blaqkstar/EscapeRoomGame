package game;

public class Desk extends Item {
    public Desk(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        System.out.println("You sit at the desk and curiously sift through the contents of its drawers. Nothing interesting here.");
    }
}
