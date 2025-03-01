package game;

public class Lever extends Item{
    boolean positionUp = true;
    boolean positionDown = false;
    String position = "up";

    public Lever(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        if (positionUp) {
            // swaps position
            positionUp = false;
            positionDown = true;
            position = "down"; // lever is now down
            System.out.println("You pull " + position + " on the lever. It settles into place with a heavy clunk.");
        } else {
            // swaps position
            positionUp = true;
            positionDown = false;
            position = "up"; // lever is now up
            System.out.println("You push the lever back " + position + ". It clicks into place with a sharp, metallic sound.");
        }
    }
}
