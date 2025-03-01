package game;

public class Lever extends Item{
    private boolean positionUp = true;
    private String position = "up";
    private String positionalIndicator = "UNDEFINED POSITIONAL INDICATOR";

    public Lever(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        if (positionUp) {
            // swaps position
            setPosition("down"); // lever is now down
            setInspection(getPositionalIndicator() + " It's currently in the " + getPosition() + " position.");
            System.out.println("You pull " + position + " on the lever. It settles into place with a heavy clunk.");
        } else {
            // swaps position
            setPosition("up"); // lever is now up
            setInspection(getPositionalIndicator() + " It's currently in the " + getPosition() + " position.");
            System.out.println("You push the lever back " + position +". It clicks into place with a sharp, metallic sound.");
        }
    }
    public String getPosition() {return position;}
    public void setPosition(String position) {
        this.position = position; // changes string value
        this.positionUp = !positionUp; // flips bool value
    }
    // these allow us to set the position relative to the other levers on the wall
    public void setPositionalIndicator(String positionalIndicator) {this.positionalIndicator = positionalIndicator;}
    public String getPositionalIndicator() {return positionalIndicator;}
}
