package game;

public class Sandwich extends Item{

    private Item itemToSpitToUser;

    private Transmorgrifier transmorgrifier;

    public Sandwich(String name, String description, String inspection, Transmorgrifier transmorgrifier) {
        super(name, description, inspection);
        this.transmorgrifier = transmorgrifier;
    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable()) {
            System.out.println("The sandwich disappears, and the vat of liquid beside the machine bubbles intensely.");
            transmorgrifier.getItemsToTransmorgrify().remove(this);
        }
        else{
            System.out.println("You pick up the sandwich and it immediately starts crumbling as if made of gravel. You put it down.");
        }
    }
}
