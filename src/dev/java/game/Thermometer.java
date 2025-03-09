package game;

public class Thermometer extends Item {

    private Transmorgrifier transmorgrifier;

    public Thermometer(String name, String description, String inspection, Transmorgrifier transmorgrifier) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable()) {
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            System.out.println("The thermometer transforms into a ball of liquid mercury. After a few moments, the ball starts moving and crawls sluglike up the south wall and into a crack in the ceiling.");
        }
        else{
            System.out.println("You check pick up a bottle of liquid and check the temperature. The thermometer temperature goes up despite the bottle being cold to the touch.");
        }
    }
}
