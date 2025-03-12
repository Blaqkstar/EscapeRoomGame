package game;

/**
 * Game item class
 */
public class Phonograph extends Item{

    public Phonograph(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        if (!this.isUsed()) {
            try {
                // initial activation
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+
                        ": You turn the crank and the mechanism groans in protest as it comes to life. The needle drops onto the record with a faint hiss, and the room fills with a haunting melody - slow, discordant,\n" +
                        "and somehow wrong.");
                System.out.println();
                Thread.sleep(3000);
                // music builds
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                        ": The music swells, each note resonating deep within your chest. The symbols etched into the phonograph begin to glow faintly, their light pulsing in time with the melody. The air grows colder,\n" +
                        "and you feel a presence - vast and incomprehensible, pressing in on you from all sides.");
                System.out.println();
                Thread.sleep(3000);
                // eerie effects
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                        ": Shadows in the room seem to lengthen and twist, their shapes forming patterns that make your mind feel... Detached. The hum in the air rises to a deafening crescendo, and for a moment, you think you\n" +
                        "hear whispers - voices speaking in a language you don't understand, yet somehow recognize.");
                System.out.println();
                Thread.sleep(3000);
                // climax
                String allCaps = ": The music reaches its peak, the notes spiraling into a chaotic, otherwordly harmony. The glow from the symbols intensifies to the point that looking at them directly is excruciating. The intense light\n" +
                        "casts caustic patterns on the walls of the room. You feel as though space itself is bending, the walls stretching and warping as if reality is unraveling around you.";
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+allCaps.toUpperCase());
                System.out.println();
                Thread.sleep(3000);
                // abrupt end
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                        ": The music stops abruptly, the needle lifting from the record with a sharp click. The glow fades, and the room falls silent, save for the faint hum that lingers in the air. The shadows\n" +
                        "return to their normal shapes, but the sense of unease remains.");
            } catch (InterruptedException ex) {
                System.out.println("The phonograph's effect was interrupted!");
            }

            this.setUsed(true);
        } else {
            System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": Nothing happens.");
        }
    }
}
