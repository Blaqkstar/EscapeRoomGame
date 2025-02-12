package game;

public class Bookshelf extends Item {
    public Bookshelf(String name, String description, String inspection) {
        super(name,description,inspection);
    }

    @Override
    public void use() {
        System.out.println("You flip through a few of the books on the shelf. Nothing seems to stand out.");
    }
}
