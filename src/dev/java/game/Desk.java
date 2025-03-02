package game;

public class Desk extends Item {
    public Desk(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        this.setUsed(true); // used!
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You sit at the desk and curiously sift through the contents of its drawers. Nothing interesting here.");
    }
}
