package game;

import java.awt.*;
import java.util.Random;

public class Diary extends Item {

    public Diary(String name, String description, String inspection) {
        super(name, description, inspection);
    }

    @Override
    public void use() {
        System.out.println();
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+
                ": You flip through the diary, hoping for some hidden wisdom or a secret map. Instead, you find a doodle of a stick figure holding a sign that says, 'You're still reading this?\n" +
                "Go solve the puzzle!' Beneath it, in tiny, hastily-scrawled letters: 'P.S. If you're reading this, you're probably doomed.'");
    }
}
