package game;
import java.util.ArrayList;
import java.util.List;

public class Lever extends Item{
    private boolean positionUp = true;
    private String position = "up";
    private String positionalIndicator = "UNDEFINED POSITIONAL INDICATOR";
    private Room room; // reference to room containing lever
    private List<LeverObserver> observers = new ArrayList<>(); // list of observers

    public Lever(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    @Override
    public void use() {
        if (positionUp) {
            setInspection(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": "+getPositionalIndicator() + " It's currently in the " + getPosition() + " position.");
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You pull down on the lever. It settles into place with a heavy clunk.");
            // swaps position
            setPosition("down"); // lever is now down
        } else {
            setInspection(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": "+getPositionalIndicator() + " It's currently in the " + getPosition() + " position.");
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You push the lever back up. It clicks into place with a sharp, metallic sound.");
            // swaps position
            setPosition("up"); // lever is now up
        }
    }
    public String getPosition() {return position;}
    public void setPosition(String position) {
        this.position = position; // changes string value
        this.positionUp = !positionUp; // flips bool value
        notifyObservers(); // notifies observers of change
    }
    // these allow us to set the position relative to the other levers on the wall
    public void setPositionalIndicator(String positionalIndicator) {this.positionalIndicator = positionalIndicator;}
    public String getPositionalIndicator() {return positionalIndicator;}

    /// observer methods
    // adds observer to list
    public void addObserver(LeverObserver observer) {observers.add(observer);}

    // notifies all observers of state change
    private void notifyObservers() {
        for (LeverObserver observer : observers) {
            observer.onLeverStateChange(this);
    }

    }
}
