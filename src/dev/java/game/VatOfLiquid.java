package game;

public class VatOfLiquid extends Item {

    public VatOfLiquid(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        if (!isUsed()) {
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You stir the " +ConsoleColors.CYAN+"vat"+ConsoleColors.RESET +" of liquid with a nearby stirring stick. The stick comes out scorched.");
            setUsed(true);
        } else {
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You've done enough damage here already. Let's poke around the room some more.");
        }

    }
}
