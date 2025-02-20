package game;

import java.util.Random;

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
            if (counter >= 4) {
                System.out.println("You flip the lamp's power switch. The bulb flickers violently, casting eerie shadows around the room. Suddenly, \n" +
                        "it overcharges and bursts with a sharp pop, sending a shower of tiny glass shards into the air. Darkness swiftly consumes the space, \n" +
                        "leaving the shattered lamp as a silent testament to its final act.");
                counter++;
                powered = false; // lamp is off
                destroyed = true; // lamp is now broken
            }
            else {
                if (powered) {
                    System.out.println("You flip the lamp's power switch. The light flickers briefly before settling into darkness. The room's \n" +
                            "shadows deepen and the lamp stands quietly, awaiting your next command.");
                    counter++;
                    powered = false; // lamp is off
                } else {
                    System.out.println("You flip the lamp's power switch. The light flickers briefly before casting a warm glow across the room. Shadows recede \n" +
                            "and the lamp stands quietly, illuminating the space and inviting your next move.");
                    counter++;
                    powered = true; // lamp is on
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
