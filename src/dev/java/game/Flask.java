package game;

public class Flask extends Item{
    private Item itemToSpitToUser;

    public Item getItem() {
        return itemToSpitToUser;
    }

    public Flask(String name, String description, String inspection) {
        super(name, description, inspection);
    }

    @Override
    public void use() {
        System.out.println("Flask used, weird bubbles appear!");


    }
}
