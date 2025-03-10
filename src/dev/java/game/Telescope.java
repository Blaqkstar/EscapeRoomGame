package game;

import java.awt.*;

public class Telescope extends Item {

    public Telescope(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+
                ": You adjust the telescope's focus, the mechanism groaning faintly as the eyepiece shifts. The stars outsie come into sharp relief, their light piercing and unnatural. For a moment, you think you see\n" +
                "something moving among them - something vast, incomprehensible, its form shifting and writhing like smoke.");
    }
}
