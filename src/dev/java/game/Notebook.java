package game;

import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

public class Notebook extends Item {

    private Transmorgrifier transmorgrifier;

    public Notebook(String name, String description, String inspection, Transmorgrifier transmorgrifier) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
    }
    @Override
    public void use() {
        this.setUsed(true);
        LearnTransmorgrifier();
        System.out.println("Through incoherent ramblings about 'proving wrong his ghastly skeptics', you learn how to use the scientist's cunning machine. It is a " +ConsoleColors.CYAN+"transmorgrifier"+ConsoleColors.RESET +", a device \n"+
                "that has perfected the ancient goal of transforming one material into another.");
    }

    public void LearnTransmorgrifier(){
        transmorgrifier.setUsable(true);
    }
}
