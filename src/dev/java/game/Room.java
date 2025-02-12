package game;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Room {
    // this is the logical representation of the "walls" of each room
    private EnumMap<Main.Direction, Item> walls; // map of directions to items. syntax: <key, value>

    // Keeps track of items in separate map not associated with direction
    // This way player can inspect item without specifying direction
    // TODO: Are we adding items to this map after player "discovers" them via look?
    private Map<String, Item> items = new HashMap<>();

    private String name = "Undefined Room Name";

    public Room() {
        walls = new EnumMap<>(Main.Direction.class); // initializes map
    }

    public void setItem(Main.Direction direction, Item item) {
        walls.put(direction, item); // adds item to specified direction
        items.put(item.getName(), item); // adds item to item list
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Item getItemAtDirection(Main.Direction direction) {
        return walls.get(direction); // gets item at the specified direction
    }

    public Map<String, Item> getItems() {
        return items;
    }
}

