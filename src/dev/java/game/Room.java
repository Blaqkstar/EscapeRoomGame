package game;

import java.util.*;

/**
 * Class to hold all logic for each specific room
 */
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

}

