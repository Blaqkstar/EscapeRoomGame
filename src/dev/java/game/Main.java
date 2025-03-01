package game;

import java.sql.Connection;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main{
    public static void main(final String[] args) throws Exception {

        final Logger log = LogManager.getLogger(Main.class.getName());

        ScoreDB scoreDB = new ScoreDB();

        // prints game title
        printTitle();

        // Create list of rooms
        ArrayList<Room> rooms = new ArrayList<Room>();

        // Create tutorial room using SetNewRoom
        Room room = SetNewRoom(log, "Tutorial Room");
        // creates conservatory room
        //Room conservatoryRoom = SetNewRoom(log, "The Conservatory");

        //You could loop this and have an infinite number of rooms
        //Check out the RoomSetup Class for how it works!
        rooms.add(room);
        //rooms.add(conservatoryRoom);

        // this is going to read user input
        Scanner scanner = new Scanner(System.in);
        String input;

        // Welcome message
        System.out.println("Shadows encapsulate your ephemeral form. In the distance, a dim candle flickers, suspended by some unknown force. " +
                "As you approach, transfixed, a quiet voice beckons...");
        Thread.sleep(300);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'... Who are you?' ");
        System.out.print(ConsoleColors.YELLOW+"Enter your name: "+ConsoleColors.RESET);
        Thread.sleep(200);
        String playerName = scanner.nextLine(); // collects player name
        // instantiates player
        Player player = new Player(playerName, 0);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'"+player.getName() + "... I do not know you...' ");
        Thread.sleep(200);
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'BEGONE!'");
        Thread.sleep(300);
        System.out.println("You awaken, groggily, to find yourself in a strange room. Along each wall are items.");

        // this continues until the user types 'exit'
        do {
            System.out.println();
            System.out.print(ConsoleColors.YELLOW+"Enter input (or 'help' for a list of available commands): "+ConsoleColors.RESET);
            input = scanner.nextLine(); // user input
            log.debug("user input received");
            System.out.println();

            // processes user input
            if (input.startsWith("look ")) {
                log.debug("player selected look");
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        final Direction direction = Direction.valueOf(parts[1].toLowerCase()); // gets direction

                        if (!room.getItemsAtDirection(direction).isEmpty()) {
                            // Set all items in this direction to observed
                            for (Item item : room.getItemsAtDirection(direction)) {
                                item.setObserved(true);
                            }
                            // displays item in chosen direction
                            System.out.println("You look " + direction.getDescription() + " and see: " + room.describeItemsToPlayer(room.getItemsAtDirection(direction)));
                        }
                        else{
                            System.out.println("You look " + direction.getDescription() + " and see a blank wall");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid direction. Please enter one of the following: north, south, east, west."); // handles input issues
                    }
                } else {
                    System.out.println("Invalid input. Please use the format 'look <direction>'."); // handles formatting issues
                }
            } else if(input.startsWith("inspect ")) {
                log.debug("player selected inspect");
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        // declare item that is being inspected
                        Item item = room.getItems().get(parts[1].toLowerCase());
                        // Check if player has observed the item yet
                        if (item.isObserved()) {
                            System.out.print(item.getInspection());
                        }
                        else{
                            System.out.println("You do not see any " + parts[1]);
                        }
                    }
                    // Handles input of unknown item
                    catch (Exception e) {
                        System.out.println("You do not see any " + parts[1]);
                    }
                }
                else {
                    System.out.println("Invalid input. Please use the format 'inspect <item>'."); // handles formatting issues
                }
            }
            else if (input.startsWith("use ")) {
                log.debug("player selected use");
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        // declare item that is being inspected
                        Item item = room.getItems().get(parts[1].toLowerCase());
                        // Check if player has observed the item yet
                        if (item.isObserved()) {
                            // tracks user score
                            player.setScore(player.getScore() + 1);
                            if (item.getName().equals("key")) {

                                if (room.GetExitDoor().isObserved()) {
                                    item.use();
                                    room.GetExitDoor().unlockDoor();
                                }
                                else{
                                    System.out.println("You have not seen anything to unlock");
                                }
                            }
                            else{
                                item.use();

                            }
                        }
                        else{
                            System.out.println("You do not see any " + parts[1]);
                        }
                    }
                    // Handles input of unknown item
                    catch (Exception e) {
                        System.out.println("You do not see any " + parts[1]);
                    }
                }
                else {
                    System.out.println("Invalid input. Please use the format 'use <item>'."); // handles formatting issues
                }
            }
            else if (input.startsWith("open ")) {
                log.debug("player selected open");
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        if (parts[1].equals("door")) {

                            if(room.GetExitDoor().isObserved()) {
                                if (!room.GetExitDoor().getIsLocked()) {
                                    room = SetNewRoom(log, "The Conservatory");

                                    System.out.println("You open the door and go into a new room.");
                                }
                                else{
                                    System.out.println("The door is locked");
                                }
                            }
                            else{
                                System.out.println("You do not see any " + parts[1]);
                            }
                        }
                        // tracks user score
                        player.setScore(player.getScore() + 1);
                    } catch(Exception e){
                        System.out.println("You do not see any " + parts[1]);
                    }
                }
            }
            else if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("?")) {
                printCommands();
            }
            else if (input.equalsIgnoreCase("highscores")) {
                printHighScores(scoreDB);

            }
            else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
            }

        } while (!input.equalsIgnoreCase("exit")); // repeats loop until user types 'exit'

        // exit message
        log.info("exiting game...");
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static void printCommands() {
        // list of available commands
        System.out.println("AVAILABLE COMMANDS:");
        System.out.println("--------------------");
        System.out.println(ConsoleColors.YELLOW+"1. look <direction>"+ConsoleColors.RESET+": looks at the specified direction");
        System.out.println(ConsoleColors.YELLOW+"2. inspect <item>"+ConsoleColors.RESET+": inspects the specified item");
        System.out.println(ConsoleColors.YELLOW+"3. use <item>"+ConsoleColors.RESET+": attempts to use the specified item");
        System.out.println(ConsoleColors.YELLOW+"4. open <item>"+ConsoleColors.RESET+": attempts to open the specified item");
        System.out.println(ConsoleColors.YELLOW+"5. highscores"+ConsoleColors.RESET+": displays the high scores");
        System.out.println(ConsoleColors.YELLOW+"6. help"+ConsoleColors.RESET+": prints this message");
        System.out.println(ConsoleColors.YELLOW+"7. exit"+ConsoleColors.RESET+": exits the game");
    }
    private static void printTitle(){
        // text generated via https://patorjk.com/software/taag. This is the "Slant" font
        final String title = ConsoleColors.RED+"\n" +
                "    ___________ _________    ____  ______   ____  ____  ____  __  ___\n" +
                "   / ____/ ___// ____/   |  / __ \\/ ____/  / __ \\/ __ \\/ __ \\/  |/  /\n" +
                "  / __/  \\__ \\/ /   / /| | / /_/ / __/    / /_/ / / / / / / / /|_/ / \n" +
                " / /___ ___/ / /___/ ___ |/ ____/ /___   / _, _/ /_/ / /_/ / /  / /  \n" +
                "/_____//____/\\____/_/  |_/_/   /_____/  /_/ |_|\\____/\\____/_/  /_/   \n" +
                "====================================================================\n"+ConsoleColors.RESET;
        System.out.println(title);
    }

    //This is our example of the comparator. The difference bewtween a comparator and comparable is that
    //a comparator is used to compare different aspects bewtween two specfic objects of a certain type. Whereas a
    //a comparable defines how you can sort a large collection of objects.
    Comparator<Player> nameComparator = new Comparator<Player>() {
        public int compare(Player p1, Player p2) {
            return p1.getName().compareTo(p2.getName());
        }
    };

    private static void printHighScores(ScoreDB scoreDB) throws Exception {


        System.out.println("\n" +
                "██╗  ██╗██╗ ██████╗ ██╗  ██╗    ███████╗ ██████╗ ██████╗ ██████╗ ███████╗███████╗\n" +
                "██║  ██║██║██╔════╝ ██║  ██║    ██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝██╔════╝\n" +
                "███████║██║██║  ███╗███████║    ███████╗██║     ██║   ██║██████╔╝█████╗  ███████╗\n" +
                "██╔══██║██║██║   ██║██╔══██║    ╚════██║██║     ██║   ██║██╔══██╗██╔══╝  ╚════██║\n" +
                "██║  ██║██║╚██████╔╝██║  ██║    ███████║╚██████╗╚██████╔╝██║  ██║███████╗███████║\n" +
                "╚═╝  ╚═╝╚═╝ ╚═════╝ ╚═╝  ╚═╝    ╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝\n" +
                "                                                                                 \n");
        Connection conn = ScoreDB.Setup();
        if (conn != null) {
            List<Player> players = scoreDB.Test(conn);

            Collections.sort(players);

            System.out.println("This is the sorted players and scores using the Comparable and Comparator");

            for (Player player : players) {
                System.out.println(player.getName());
                System.out.println(player.getScore());
            }
        }


    }

    /// ---------------------------------------{ INLINE CLASSES AND ENUMS BEGIN HERE }--------------------------------------------------------
    // enum representing four cardinal directions
    public enum Direction {
        north("to the north"),
        south("to the south"),
        east("to the east"),
        west("to the west");

        private final String description; //

        Direction(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description; // returns the direction's description
        }
    }

    private static Room SetNewRoom(Logger log, String roomName) throws Exception {
        // Create RoomSetup object
        RoomSetup roomSetup = new RoomSetup();

        // Declare room variable
        Room room = null;

        // Assign previously declared room variable to a new room depending on roomName input
        if(roomName.equalsIgnoreCase("Tutorial Room")) {
            // Assigns room to return of roomSetup's makeRooms method
            room = roomSetup.MakeTutorialRoom();
        }
        else if (roomName.equalsIgnoreCase("The Conservatory")) {
            room = roomSetup.MakeConservatoryRoom();
        }
        // Sets room name
        room.setName(roomName);

        log.info("instantiating " + room.getName());

        log.debug("adding items to " + room.getName());

        System.out.println();

        return room;
    }

    private static void ShowRoomName(Room currentRoom) {
        System.out.println(currentRoom.getName());
    }
}

