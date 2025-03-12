package game;

/**
 * Game item class
 */
public class Orrery extends Item{

    public Orrery(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        // at least for now, this is decorative. Using should result in a println where the planets orbit a solar system with 14 planets
        // some other stuff maybe happens
        System.out.println("You turn the orrery's crank, and the mechanism groans to life with a metallic groan. The planets begin to move, their orbits smooth and precise, but something about their motion feels wrong - too fast,\n" +
                "too fluid, as though time itself is bending around them. The solar system has fourteen planets, their paths intersecting in ways that defy anything you've ever seen, and their surfaces etched with shallow, angular\n" +
                "symbols. As the procession commences, the room's ambient hum grows louder, filling the room with a sound that feels less like a vibration and more like a voice, whispering something just beyond the edge of comprehension.\n" +
                "For a moment, the orrery's light dims, and you see the shadows on the walls twist into shapes resembling constellations - but you don't recognize any of them.");
    }
}
