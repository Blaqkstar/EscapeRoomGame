package game;

public class Sandwich extends Item{

    private Item itemToSpitToUser;

    public Item getItem() {
        return itemToSpitToUser;
    }



    public Sandwich(String name, String description, String inspection) {
        super(name, description, inspection);
    }

    @Override
    public void use() {
        System.out.println("Sandwich used, weird bubbles appear!");

        TransmorgSupplier<Flask> flaskSupplier = () -> new Flask("flask","A clear crystal flask","You notice the flask has a tangy smell to it");
        itemToSpitToUser = flaskSupplier.get();
        itemToSpitToUser.use();
    }
}
