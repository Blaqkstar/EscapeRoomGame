package game;

import java.util.Random;

/**
 * Game item class
 */
public class Lamp extends Item {
    public Lamp(String name, String description, String inspection) {
        super(name, description, inspection);
    }

    boolean powered = true; // initializes as true
    boolean destroyed = false; // initializes as false
    int counter = 0;

    @Override
    public void use() {
        // first tests to see if lamp has been destroyed yet
        if (!destroyed) {
            if (counter >= 4 && powered) {
                this.setUsed(true); // used!
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You flip the lamp's power switch. The bulb flickers violently, casting eerie shadows around the room. Suddenly, \n" +
                        "it overcharges and bursts with a sharp pop, sending a shower of tiny glass shards into the air. Darkness swiftly consumes the space, \n" +
                        "leaving the shattered lamp as a silent testament to its final act.");
                counter++;
                powered = false; // lamp is off
                destroyed = true; // lamp is now broken
                this.setDescription("now broken - nice job");
                this.setInspection(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": A lamp with a broken bulb.");
            }
            else {
                if (powered) {
                    this.setUsed(true); // used!
                    System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You flip the lamp's power switch. The light flickers briefly before settling into darkness. The room's shadows deepen and the lamp stands quietly, awaiting your next command.");
                    counter++;
                    powered = false; // lamp is off
                    this.setDescription("now cold and lifeless, a silent sentinel in the encroaching darkness");
                } else {
                    this.setUsed(true); // used!
                    System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You flip the lamp's power switch. The light flickers briefly before casting a warm glow across the room. Shadows recede and the lamp stands quietly,\n" +
                            "illuminating the space and inviting your next move.");
                    counter++;
                    powered = true; // lamp is on
                    this.setDescription("judging by the occasional flickering of the bulb, it's on its last leg");
                }
            }
        } else {
            String[] roastArray = new String[] { "What, are you trying to start a fire?",
                    "Trying to resurrect the lamp, are we?",
                    "Do you have a secret talent for fixing broken things I don't know about?",
                    "Lamp's out of commission, I'm afraid. Maybe try a candle instead?",
                    "You really think it's gonna work this time? You know what, have at it, pal, and best of luck." };

            Random random = new Random();
            int randomNumber = random.nextInt(5);
            System.out.println(roastArray[randomNumber]);
        }
    }
}
