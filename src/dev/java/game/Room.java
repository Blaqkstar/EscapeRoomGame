package game;

import java.util.*;

public class Room implements LeverObserver {
    // this is the logical representation of the "walls" of each room
    private EnumMap<Main.Direction, List<Item>> walls; // map of directions to items. syntax: <key, value>

    // Keeps track of items in separate map not associated with direction
    // This way player can inspect item without specifying direction
    private Map<String, Item> items = new HashMap<>();

    private String name = "Undefined Room Name";

    private Door exitDoor;
    private String introBlurb = "UNDEFINED INTRO BLURB";
    private int roomPar = 0;

    private boolean chooseRightEnding;


    public Room() {
        walls = new EnumMap<>(Main.Direction.class); // initializes map

        for (Main.Direction direction : Main.Direction.values()) {
            walls.put(direction, new ArrayList<>());
        }
    }

    public Room(boolean chooseRightEnding) {
        walls = new EnumMap<>(Main.Direction.class); // initializes map

        for (Main.Direction direction : Main.Direction.values()) {
            walls.put(direction, new ArrayList<>());
        }

        this.chooseRightEnding = chooseRightEnding;
    }

    public void setItem(Main.Direction direction, Item item) {

        // Adds item to wall specified by direction parameter
        List<Item> itemList = walls.get(direction);
        itemList.add(item);
        items.put(item.getName().toLowerCase(), item); // adds item to item list - updated to ensure name is always lowercase
    }

    public void removeItem(Main.Direction direction, Item item) {
        items.remove(item.getName().toLowerCase());

        List<Item> itemList = walls.get(direction);
        itemList.remove(item);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setRoomPar(Integer roomPar) {
        this.roomPar = roomPar;
    }

    public Integer getRoomPar() {
        return roomPar;
    }

    public Door GetExitDoor() {
        return exitDoor;
    }

    public void SetExitDoor(Door exitDoor) {
        this.exitDoor = exitDoor;
    }

    public String getIntroBlurb() {
        return introBlurb;
    }

    public void setIntroBlurb(String introBlurb) {
        this.introBlurb = introBlurb;
    }

    public List<Item> getItemsAtDirection(Main.Direction direction) {
        return walls.get(direction); // gets items at the specified direction
    }

    public List<Door> getDoorAtDirection(Main.Direction direction) {
        List<Door> doors = new ArrayList<>();

        for(Item item : walls.get(direction)) {
            if(item instanceof Door) {
                doors.add((Door)item);
            }
        }
        return doors;
    }

    public String describeItemsToPlayer(List<Item> items) {
        StringBuilder desc = new StringBuilder();

        ///  PUNCTUATION FORMATTER
        for (int i = 0; i < items.size(); i++) {
            // if i is the second to last item in the list
            if (i == items.size() - 2) {
                // appends second to last item with an Oxford comma
                desc.append(items.get(i).getDescription()).append(", and ");
            } else if (i == items.size() - 1) {
                // appends last item
                desc.append(items.get(i).getDescription()).append(".");
            } else {
                // appends other items with a comma
                desc.append(items.get(i).getDescription()).append(", ");
            }
        }
        return desc.toString();
    }

    ///  method to check relative lever up/down status
    public boolean checkLevers(Lever leverA, Lever leverB, Lever leverC) {
        // defines correct config
        boolean correctConfigA = leverA.getPosition().equals("up");
        boolean correctConfigB = leverB.getPosition().equals("down");
        boolean correctConfigC = leverC.getPosition().equals("down");

        return correctConfigA && correctConfigB && correctConfigC;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }

    @Override
    public void onLeverStateChange(Lever lever) {
        // calls existing checkLevers method
        Lever leverA = (Lever) items.get("lever1");
        Lever leverB = (Lever) items.get("lever2");
        Lever leverC = (Lever) items.get("lever3");

        boolean checkLevers = checkLevers(leverA, leverB, leverC);

        if (checkLevers(leverA, leverB, leverC)) {
            if (lever.getPosition().equalsIgnoreCase("up")) {
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You push the lever back up. It clicks into place with a sharp, metallic sound.");
            } else {
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You pull down on the lever. It settles into place with a heavy clunk.");
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    System.out.println("LOOK AT ROOM.onLeverStateChange() PLEASE");
                }
                System.out.println();
                System.out.println(ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET +
                        ": ...");
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    System.out.println("LOOK AT ROOM.onLeverStateChange() PLEASE");
                }
                System.out.println();
                System.out.println(ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET +
                        ": The walls seem to ripple, their surfaces alive with faint, pulsating light. A heavy CLUNK echoes from the "+ConsoleColors.CYAN+"door"+ConsoleColors.RESET+", followed by the sound of grinding gears and the hiss of releasing pressure. The door creaks \n" +
                        "open, revealing a dimly-lit corridor beyond. The air from the corridor carries a faint, acrid smell - chemicals, ozone, and something else, metallic and sharp. The room's ambient hum fades, leaving only the sound\n" +
                        "of your heartbeat and the faint, almost imperceptible whisper of something waiting.");
                System.out.println();
                exitDoor.unlockDoor();
            }
        } else {
            if (lever.getPosition().equalsIgnoreCase("up")) {
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You push the lever back up. It clicks into place with a sharp, metallic sound.");
            } else {
                System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You pull down on the lever. It settles into place with a heavy clunk.");
                System.out.println(ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": A distant, rhythmic thud begins, like the heartbeat of some colossal machine. The sound fades, leaving the room in a silence that feels almost expectant.");
            }

        }
    }
    ///  DIALOG FOR FINAL ROOM
    public static void FinalRoomDialog() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": A deep, resonant chuckle fills the air, echoing as if emanating from everywhere and nowhere at once.");
        Thread.sleep(1500);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE"+ConsoleColors.RESET+
                ": 'Did you truly believe you could leave so easily? How... amusing.'");
        Thread.sleep(2000);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE"+ConsoleColors.RESET+
                ": 'I must confess, you've surprised me. Your resilience is... remarkable. But now, the true test begins. Can you withstand what lies ahead? Or will you crumble beneath the weight of your own past?'");
        Thread.sleep(2000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The voice grows colder, sharper, each word cutting like a blade.");
        Thread.sleep(1500);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE"+ConsoleColors.RESET+
                ": 'You think you've outrun your sins? "+ConsoleColors.RED+"NO!"+ConsoleColors.RESET+" They walk beside you, step for step, shadow for shadow. The friend you abandoned, the lover you failed, the child who died in your arms... they are not gone.\n" +
                "They are here, in the cracks of your mind. In the marrow of your bones.'");
        Thread.sleep(2000);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE" + ConsoleColors.RESET +
                ": 'You've changed, you say? "+ConsoleColors.RED+"NO!"+ConsoleColors.RESET+" You've merely buried the truth beneath layers of time and dust. But now, it rises. And when you stand before the weight of your own sins, will you face them? Or will you\n" +
                "break beneath them?'");
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The voice shifts, becoming almost melodic, like a dirge.");
        Thread.sleep(1500);
        System.out.println();
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE" + ConsoleColors.RESET +
                ": 'Four doors stand before you. Only one leads to freedom. The others? They lead to truths you may not wish to face. Listen closely, for this is your clue...'");
        Thread.sleep(1000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The voice recites, each line dripping with menace.");
        System.out.println();
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE" + ConsoleColors.RESET +":");

        System.out.println(ConsoleColors.BLUE+"Four paths stretch where silence calls,");
        Thread.sleep(1500);
        System.out.println("One to rise and three to fall.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("North echoes cries of pain,");
        Thread.sleep(1500);
        System.out.println("A friend's plea, left in vain.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("South drips red with guilt's refrain,");
        Thread.sleep(1500);
        System.out.println("A lover's words, your final stain.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("East holds ghosts with hollow tongues,");
        Thread.sleep(1500);
        System.out.println("A debt unpaid, yet still unsung.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("West is weighted with bones you bear,");
        Thread.sleep(1500);
        System.out.println("A fate you chose, yet did not spare.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("One road mends, though scars remain,");
        Thread.sleep(1500);
        System.out.println("The rest will break you once again.");
        Thread.sleep(2000);
        System.out.println();
        System.out.println("Mark the cost of what you've done,");
        Thread.sleep(1500);
        System.out.println("Choose with care, or be undone."+ ConsoleColors.RESET);
        System.out.println();
        Thread.sleep(2000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The voice returns to its cold, mocking tone.");
        Thread.sleep(1000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE" + ConsoleColors.RESET +
                ": 'Solve the riddle, choose wisely, and perhaps you'll find your way out. But be warned: failure is not an end. It is an eternity. And I? I have all the time in the world.'");
        Thread.sleep(1000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": A deep, resonant laugh echoes, fading into silence.");
    }
}

