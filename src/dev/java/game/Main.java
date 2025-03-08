package game;

import java.sql.Connection;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

///  TODO: REMAINING FEATURES FOR CORE FUNCTIONALITY
///  TODO: 1. FIGURE OUT AN ACCEPTABLE PAR FOR EACH ROOM AND AN ACCEPTABLE MAX SCORE BEFORE GAMEOVER
///  TODO: 2. BUILD OUT GAMEOVER() METHOD
///  TODO: 3. NEED TO WORK OUT A METHOD TO RESET EVERYTHING IF PLAYER CHOOSES TO PLAY AGAIN

public class Main{
    public static void main(final String[] args) throws Exception {

        final Logger log = LogManager.getLogger(Main.class.getName());

        ScoreDB scoreDB = new ScoreDB();

        int gameOverScore = 5;

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


        // this loops until the user types 'exit'
        do {
            // Welcome message
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": Shadows encapsulate your ephemeral form. In the distance, a dim candle flickers, suspended by some unknown force. " +
                    "As you approach, transfixed, a quiet voice beckons...");
            System.out.println();
            Thread.sleep(300);
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'... Who are you?' ");
            System.out.print(ConsoleColors.YELLOW+"Enter your name: "+ConsoleColors.RESET);
            Thread.sleep(300);
            String playerName = scanner.nextLine(); // collects player name
            // instantiates player
            Player player = new Player(playerName, 0);

            /// player.getUpperName uses our function and unaryOperator examples
            System.out.println();
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'"+player.getUpperName() + "... I do not know you...' ");
            Thread.sleep(300);
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'BEGONE!'");
            Thread.sleep(300);
            System.out.println();
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": You awaken, groggily, to the sound of rain tapping against a fogged window. The air is heavy, thick with the scent of damp wood and something faintly metallic,\n" +
                    "like the tang of old blood. The room around you is dimly lit, its edges blurred by shadows that seem to shift when you aren't looking. A faint hum fills the air,\n" +
                    "low and resonant, as though the walls themselves are alive. The rain outside falls in a steady rhythm, but the fog beyond the window moves unnaturally, swirling and\n" +
                    "coiling like a living thing. The wallpaper peels at the edges, revealing patterns beneath that seem to shift when you look away. Your head throbs faintly, as though you've\n" +
                    "forgotten something important. This place feels like a threshold - a space between worlds - and you can't tell if you're meant to escape or if something is waiting for you to\n" +
                    "step further in...");

            // this one loops until gameOverScore has been reached
            do {
                System.out.println();
                System.out.print(ConsoleColors.YELLOW+"Enter input (or 'help' for a list of available commands): "+ConsoleColors.RESET);
                input = scanner.nextLine(); // user input
                log.debug(ConsoleColors.PURPLE+"user input received"+ConsoleColors.RESET);

                // processes user input
                /// ------------------------------------------------{ LOOK ACTION HANDLER }--------------------
                if (input.startsWith("look ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected look"+ConsoleColors.RESET);
                    System.out.println();
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
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You look " + direction.getDescription() + " and see " + room.describeItemsToPlayer(room.getItemsAtDirection(direction)));
                            }
                            else{
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You look " + direction.getDescription() + " and see a blank wall");
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid direction. Please enter one of the following: north, south, east, west."); // handles input issues
                        }
                    } else {
                        System.out.println("Invalid input. Please use the format 'look <direction>'."); // handles formatting issues
                    }
                    /// ------------------------------------------------{INSPECT ACTION HANDLER}--------------------
                } else if(input.startsWith("inspect ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected inspect"+ConsoleColors.RESET);
                    System.out.println();
                    final String[] parts = input.split(" "); // splits input into parts, storing in an array
                    if (parts.length == 2) { // ensures that input consists of two parts
                        try {
                            // declare item that is being inspected
                            Item item = room.getItems().get(parts[1].toLowerCase());
                            // Check if player has observed the item yet
                            if (item.isObserved()) {
                                System.out.print(item.getInspection());
                                System.out.println();
                            }
                            else{
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                                System.out.println();
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        }
                        // Handles input of unknown item
                        catch (Exception e) {
                            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                            System.out.println();
                        }
                    }
                    else {
                        System.out.println("Invalid input. Please use the format 'inspect <item>'."); // handles formatting issues
                        System.out.println();
                    }
                }
                /// ------------------------------------------------{ USE ACTION HANDLER }--------------------
                else if (input.startsWith("use ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected use"+ConsoleColors.RESET);
                    final String[] parts = input.split(" "); // splits input into parts, storing in an array
                    if (parts.length == 2) { // ensures that input consists of two parts
                        try {
                            // normalizes input to lowercase and trims whitespace
                            String itemName = parts[1].trim().toLowerCase();
                            log.debug(ConsoleColors.PURPLE+"Searching for item with key '" + itemName + "'"+ConsoleColors.RESET); // for debug purposes
                            // declare item that is being inspected
                            Item item = room.getItems().get(itemName);
                            ///  DEBUG MESSAGES BEGIN
                            if (item != null) {
                                log.debug(ConsoleColors.PURPLE+"Found item '" + item.getName() + "'"+ConsoleColors.RESET);
                                System.out.println();
                            } else {
                                log.debug(ConsoleColors.PURPLE+"Item not found in room's items map"+ConsoleColors.RESET);
                                System.out.println();
                            }
                            ///  DEBUG MESSAGES END
                            // Check if player has observed the item yet
                            if (item != null && item.isObserved()) {
                                ///  ------ BEGIN NEW ITEM HANDLING
                                // key item handling
                                if (item instanceof Key) {
                                    if (room.GetExitDoor().isObserved()) {
                                        item.use(); // uses the key
                                        room.GetExitDoor().unlockDoor(); // unlocks the door
                                    } else {
                                        System.out.println(ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": You have not seen anything to unlock");
                                    }
                                }
                                // lever handling
                                else if (item instanceof Lever) {
                                    item.use(); // toggles lever
                                }
                                // handles other items
                                else {
                                    item.use(); // default use behavior
                                }
                                player.setScore(player.getScore() + 1); // successful action increments player score by one
                                ///  ------ END NEW ITEM HANDLING
                            /*if (item.getName().equals("key")) {

                                if (room.GetExitDoor().isObserved()) {
                                    item.use();
                                    room.GetExitDoor().unlockDoor();
                                }
                                else{
                                    System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You have not seen anything to unlock");
                                }
                            }
                            else{
                                item.use();
                            }*/
                            }
                            else{
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                            }
                        }
                        // Handles input of unknown item
                        catch (Exception e) {
                            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+":You do not see any " + parts[1]);
                        }
                    }
                    else {
                        System.out.println("Invalid input. Please use the format 'use <item>'."); // handles formatting issues
                    }
                }
                /// ------------------------------------------------{ OPEN ACTION HANDLER }--------------------
                else if (input.startsWith("open ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected open"+ConsoleColors.RESET);
                    System.out.println();
                    final String[] parts = input.split(" "); // splits input into parts, storing in an array
                    if (parts.length == 2) { // ensures that input consists of two parts
                        try {
                            if (parts[1].equals("door")) {
                                // if player has observed the door
                                if(room.GetExitDoor().isObserved()) {
                                    // if door is not locked and not locked forever
                                    if (!room.GetExitDoor().getIsLocked() && !room.GetExitDoor().getLockedForever()) {
                                        if (room.getName().equalsIgnoreCase("Tutorial Room")) {
                                            room = SetNewRoom(log, "The Conservatory"); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                                        } else if (room.getName().equalsIgnoreCase("The Conservatory")) {
                                            room = SetNewRoom(log, "The Lab");
                                        }
                                        System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                                        System.out.println();
                                        Thread.sleep(300);
                                        // room intro blurb dependent on the room being entered. Tutorial room intro handled at start of main()
                                        if (room.getName().equalsIgnoreCase("The Conservatory")) {
                                            // prints conservatory intro
                                            System.out.println(room.getIntroBlurb());
                                        } else if (room.getName().equalsIgnoreCase("The Lab")) {
                                            // prints lab intro
                                        }
                                        else {
                                            // prints final room intro
                                        }
                                    }
                                    else{
                                        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The door is locked");
                                    }
                                }
                                else{
                                    System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                                }
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        } catch(Exception e){
                            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                        }
                    }
                }
                /// SPEEDRUN OPTION --------ONLY FOR USE IN DEVELOPMENT-----------------------
                else if (input.equalsIgnoreCase("speedrun to lab")){
                    room = SetNewRoom(log, "The Lab"); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                }
                /// ------------------------------------------------{ HELP ACTION HANDLER }--------------------
                else if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("?")) {
                    printCommands();
                }
                else if (input.equalsIgnoreCase("score")) {
                    printScore(player);
                }
                /// ------------------------------------------------{ HISCORES ACTION HANDLER }--------------------
                else if (input.equalsIgnoreCase("hiscores")) {
                    printHiScores(scoreDB);
                }
                else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                    System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
                }
            } while (player.getScore() < gameOverScore); // basic lose conditions
            if (player.getScore() >= gameOverScore) {
                gameOver();
            }
            do {
                System.out.print("Would you like to play again? (Y / N): "+ConsoleColors.RESET);
                input = scanner.nextLine().trim().toLowerCase(); // collects player choice
            } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
            if (input.equalsIgnoreCase("n")) {
                input = "exit"; // exits game
            }
            System.out.println();
            System.out.println();
            // else loop repeats due to intrinsic do-while rules
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
        System.out.println(ConsoleColors.YELLOW+"5. score"+ConsoleColors.RESET+": prints player score");
        System.out.println(ConsoleColors.YELLOW+"6. hiscores"+ConsoleColors.RESET+": displays the high scores");
        System.out.println(ConsoleColors.YELLOW+"7. help"+ConsoleColors.RESET+": prints this message");
        System.out.println(ConsoleColors.YELLOW+"8. exit"+ConsoleColors.RESET+": exits the game");
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

    //This is our example of the comparator. The difference between a comparator and comparable is that
    //a comparator is used to compare different aspects between two specific objects of a certain type. Whereas a
    //a comparable defines how you can sort a large collection of objects.
    Comparator<Player> nameComparator = new Comparator<>() {
        public int compare(Player p1, Player p2) {
            return p1.getName().compareTo(p2.getName());
        }
    };

    private static void printScore(Player player) {
        int playerScore = player.getScore();
        System.out.println();
        System.out.println(ConsoleColors.YELLOW+"Player Score"+ConsoleColors.RESET+": " + playerScore);
    }

    private static void printHiScores(ScoreDB scoreDB) throws Exception {


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

    private static void gameOver() {
        // game over logic here
        // could contain some kind of narrative text about being dragged back into the shadows where the game starts but this time the candle extinguishes and the last
        // thing player 'hears' is a diabolical laugh from the disembodied voice

        // if we have time, maybe some logic to add player score to hiscores DB if applicable (really only relevant on beating game)

        // placeholder logic here:
        System.out.println(ConsoleColors.YELLOW+"YOU DEAD!");
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
            room = roomSetup.MakeRoom_TutorialRoom();
        }
        else if (roomName.equalsIgnoreCase("The Conservatory")) {
            room = roomSetup.MakeRoom_Conservatory();
        }
        else if (roomName.equalsIgnoreCase("The Lab")) {
            room = roomSetup.MakeRoom_Lab();
        }
        // Sets room name
        if (room != null) {
            room.setName(roomName);
            roomSetup.AssignRandomItems(room); // waits until after room is named before randomizing props
        }
        else {
            throw new Exception("Room does not exist!"); // error handling in case room doesn't exist
        }

        // prints logs
        log.debug(ConsoleColors.PURPLE+"instantiating " + room.getName()+ConsoleColors.RESET);
        log.debug(ConsoleColors.PURPLE+"adding items to " + room.getName()+ConsoleColors.RESET);

        System.out.println();

        return room;
    }

    private static void ShowRoomName(Room currentRoom) {
        System.out.println(currentRoom.getName());
    }
}

