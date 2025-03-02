package game;

import java.util.*;

public class Room {
    // this is the logical representation of the "walls" of each room
    private EnumMap<Main.Direction, List<Item>> walls; // map of directions to items. syntax: <key, value>

    // Keeps track of items in separate map not associated with direction
    // This way player can inspect item without specifying direction
    // TODO: Are we adding items to this map after player "discovers" them via look?
    private Map<String, Item> items = new HashMap<>();

    private String name = "Undefined Room Name";

    private Door exitDoor;

    public Room() {
        walls = new EnumMap<>(Main.Direction.class); // initializes map

        for (Main.Direction direction : Main.Direction.values()) {
            walls.put(direction, new ArrayList<>());
        }
    }

    public void setItem(Main.Direction direction, Item item) {

        // Adds item to wall specified by direction parameter
        List<Item> itemList = walls.get(direction);
        itemList.add(item);
        items.put(item.getName(), item); // adds item to item list
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Door GetExitDoor() {return exitDoor;}

    public void SetExitDoor(Door exitDoor) { this.exitDoor = exitDoor;}

    public List<Item> getItemsAtDirection(Main.Direction direction) {
        return walls.get(direction); // gets items at the specified direction
    }

    public String describeItemsToPlayer(List<Item> items) {
        StringBuilder desc = new StringBuilder();

        ///  GRAMMAR FORMATTER
        for (int i =0; i<items.size(); i++) {
            // if i is the second to last item in the list
            if (i == items.size() - 2) {
                // appends second to last item with a comma and 'and'
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

    public Map<String, Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }
}

