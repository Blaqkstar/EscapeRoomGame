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
                        "open, revealing a dimly-lit corridor beyond. The air from the corridor carries a faint, acrid smell - chemicals, ozone, and something else, metallic and sharp. The room's ambient hum fades, leaving only the sound of your heartbeat and the\n" +
                        "faint, almost imperceptible whisper of something waiting.");
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
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"DEEP EVIL LAUGHTER");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"Did you really think you would be able to just leave?! HA!");
        Thread.sleep(2000);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"I must confess myself impressed, you greatly exceeded my expectations.");
        Thread.sleep(2000);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"But now, will you withstand this final challenge?!");
        Thread.sleep(2000);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'You have walked far, yet your past does not trail behind you—it walks beside you, step for step.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'The voices of the fallen still call your name.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'The friend you abandoned, the lover you failed, the child who never saw the dawn… they are not gone.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'They are here. In the cracks of your mind, in the marrow of your bones.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'You have changed? No. You have only buried the truth beneath time and dust.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'But now, it rises.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "'When you stand before the weight of your own sins, will you face them… or will you break beneath them?'");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"Only one of these four doors will lead to freedom, here is your clue, so listen closely!");
        Thread.sleep(1000);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'Four paths stretch where silence calls,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'One to rise and three to fall.");
        Thread.sleep(1500);
        System.out.println();

        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'North still echoes cries of pain,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'The friend you left, their plea in vain.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'South drips red where guilt once bled,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'A lover’s words, the last you said.");
        Thread.sleep(1500);
        System.out.println();

        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'East holds ghosts with hollow tongues,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'A debt unpaid, but not yet done.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'West is weighed with bones you bear,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'someone's fate you did not spare.");
        Thread.sleep(1500);
        System.out.println();

        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'One road mends, though scars remain,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'The rest will break you once again.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'Mark the cost of what you've done,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.CYAN + "'Choose with care, or be undone.");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "Understand this player, this is my pact with you, you either solve the riddle and choose the right door,");
        Thread.sleep(1500);
        System.out.println(ConsoleColors.GREEN + "DISEMBODIED VOICE: " + ConsoleColors.RESET + "or you'll be stuck entering doors like this for eternity! And no rush, I'm not going anywhere! HAHAHA!");
    }
}

