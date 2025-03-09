package game;

import java.sql.Connection;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

///  TODO: REMAINING FEATURES FOR CORE FUNCTIONALITY
///  TODO: 1. NEED TO WORK OUT A METHOD TO RESET EVERYTHING IF PLAYER CHOOSES TO PLAY AGAIN

public class Main{
    public static void main(final String[] args) throws Exception {

        final Logger log = LogManager.getLogger(Main.class.getName());

        ScoreDB scoreDB = new ScoreDB();
        int gameOverScore = 0;

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
            /// TODO: method call to reset game state should go here

            // Welcome message
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": Shadows encapsulate your ephemeral form. In the distance, a dim candle flickers, suspended by some unknown force. " +
                    "As you approach, transfixed, a quiet voice beckons...");
            System.out.println();
            Thread.sleep(1300);
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'... Who are you?' ");
            System.out.print(ConsoleColors.YELLOW+"Enter your name: "+ConsoleColors.RESET);
            Thread.sleep(300);
            String playerName = scanner.nextLine(); // collects player name
            // instantiates player
            Player player = new Player(playerName, 0);

            /// player.getUpperName uses our function and unaryOperator examples
            System.out.println();
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'"+player.getUpperName() + "... I do not know you...' ");
            Thread.sleep(500);
            playIntro(); // plays narrative intro
            gameOverScore = setGameOverScore(room); // setsGameOverScore

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
                                        // recoups points to player relative to the predetermined par for the room they're leaving
                                        player.setScore(player.getScore() - room.getRoomPar());
                                        /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                                        if (room.getName().equalsIgnoreCase("Tutorial Room")) {
                                            room = SetNewRoom(log, "The Conservatory");
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
                /// SPEEDRUN OPTIONS --------ONLY FOR USE IN DEVELOPMENT-----------------------
                else if (input.equalsIgnoreCase("speedrun to lab")){
                    room = SetNewRoom(log, "The Lab"); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                }
                else if (input.equalsIgnoreCase("speedrun to conservatory")){
                    room = SetNewRoom(log, "The Conservatory"); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                    System.out.println();
                    System.out.println(room.getIntroBlurb());
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
                /// ------------------------------------------------{ ABOUT ACTION HANDLER }--------------------
                else if (input.equalsIgnoreCase("about")) {
                    printAboutInfo();
                }
                else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                    System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
                }
            } while (player.getScore() < gameOverScore); // basic lose conditions
            if (player.getScore() >= gameOverScore) {
                gameOver(player);
            }
            do {
                System.out.print(ConsoleColors.YELLOW+"Would you like to play again? (Y / N): "+ConsoleColors.RESET);
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
        System.out.println(ConsoleColors.YELLOW+"8. about"+ConsoleColors.RESET+": prints game info");
        System.out.println(ConsoleColors.YELLOW+"9. exit"+ConsoleColors.RESET+": exits the game");
    }
    private static void printTitle(){
        String ver = "v0.8.5"; // this is just a rough estimate based on what we've done so far vs what remains. Will say v1.0 when we hand in
        // text generated via https://patorjk.com/software/taag. This is the "Invita" font
        final String title = ConsoleColors.BLUE+"\n"+
                "\n" +
                "     _____)                   ______                        ______)         __    __)      \n" +
                "   /        /)               (, /    )               /)    (, /  /)        (, )  /    , /) \n" +
                "   )__   _ (/  ___ _  _        /---(   _     ____  _(/       /  (/   _        | /  _   //  \n" +
                " /      (__/ )(_)_(/_/_)_   ) / ____)_(/_(_/(_)/ ((_(_    ) /   / )_(/_       |/ _(/_((/_  \n" +
                "(_____)                    (_/ (        .-/              (_/                  |            \n" +
                "                                       (_/                                                 \n"+
                "                                                                                 "+ver+ConsoleColors.RESET;

        // text generated via https://patorjk.com/software/taag. This is the "Slant" font
//        final String oldTitle = ConsoleColors.RED+"\n" +
//                "    ___________ _________    ____  ______   ____  ____  ____  __  ___\n" +
//                "   / ____/ ___// ____/   |  / __ \\/ ____/  / __ \\/ __ \\/ __ \\/  |/  /\n" +
//                "  / __/  \\__ \\/ /   / /| | / /_/ / __/    / /_/ / / / / / / / /|_/ / \n" +
//                " / /___ ___/ / /___/ ___ |/ ____/ /___   / _, _/ /_/ / /_/ / /  / /  \n" +
//                "/_____//____/\\____/_/  |_/_/   /_____/  /_/ |_|\\____/\\____/_/  /_/   \n" +
//                "====================================================================\n"+ConsoleColors.RESET;
        System.out.println(title);
        System.out.println();
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
    private static void playIntro() throws InterruptedException {
        System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"'BEGONE!'");
        Thread.sleep(2000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": The voice's command reverberates through the astral plane, shaking the very fabric of your being. The dim candle flares violently, its light expanding into a blinding, otherworldly\n" +
                "radiance. Shadows writhe and twist as if alive, stretching toward you like grasping tendrils. The air grows heavy, pressing against your soul with an almost tangible weight.");
        Thread.sleep(6000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": A low, resonant hum begins to build, vibrating through your essence. It grows louder and louder, until it feels as though the sound is coming from within you. The candle's light intensifies,\n" +
                "its glow shifting from warm yellow to an eerie, pulsating blue. As the shadows around you fracture and splinter, you catch brief glimpses of impossible geometries - angles that shouldn't exist, colors\n" +
                "that have no name.");
        Thread.sleep(7000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": The voice echoes again, but this time it is not alone - it is a chorus - a cacophony of whispers and blood-curdling screams, all in unison: \""+ConsoleColors.RED+"BEGONE!, BEGONE!, BEGONE!"+ConsoleColors.RESET+"\" The sound is\n" +
                "deafening, overwhelming, as if the very fabric of the astral plane is rejecting you.");
        Thread.sleep(6000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": The light from the candle explodes outward in a blinding wave, engulfing everything. For a moment, you are weightless, suspended in a void of pure sensation. Colors and sounds blur together,\n" +
                "indistinguishable from one another. You feel as though you are being pulled apart and reassembled, your essence scattered across the cosmos and then drawn back together. And then... ");
        Thread.sleep(6000);
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": ... Silence...");
        Thread.sleep(3000);
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": ... Darkness...");
        Thread.sleep(3000);
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": ... A single, faint pulse of light - like a heartbeat - and then...");
        Thread.sleep(3000);
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": ... ");
        Thread.sleep(4000);
        System.out.println();
        System.out.println();
        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": You awaken, groggily, to the sound of rain tapping against a fogged window. The air is heavy, thick with the scent of damp wood and something faintly metallic,\n" +
                "like the tang of old blood. The room around you is dimly lit, its edges blurred by shadows that seem to shift when you aren't looking. A faint hum fills the air,\n" +
                "low and resonant, as though the walls themselves are alive. The rain outside falls in a steady rhythm, but the fog beyond the window moves unnaturally, swirling and\n" +
                "coiling like a living thing. The wallpaper peels at the edges, revealing patterns beneath that seem to shift when you look away. Your head throbs faintly, as though you've\n" +
                "forgotten something important. This place feels like a threshold - a space between worlds - and you can't tell if you're meant to escape or if something is waiting for you to\n" +
                "step further in...");
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
    private static void printAboutInfo() {
        String aboutHeader =
                        ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET+
                        "            ABOUT THIS GAME\n" +
                                ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        String aboutInfo = "Game Title: Echoes Beyond The Veil\n" +
                "Version: 1.0\n" +
                "Release Date: March 12 2025";
        String descHeader =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            GAME DESCRIPTION\n" +
                        ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        String descInfo =
                "Echoes Beyond The Veil is a text-based adventure game with strong cosmic horror / noir overtones. The player takes on the role of an unwitting explorer who finds themselves\n" +
                        "in a strange space between worlds with limited time to escape untold eldritch horrors and a fate worse than death.\n" +
                        "With every decision, you shape the story and dive deeper into this strange, liminal, place.";
        String devTeamHeader =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            DEVELOPMENT TEAM\n" +
                        ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        String devInfo =
                "Developed collaboratively by:\n" +
                        "- Joey Diestler\n" +
                        "- Kyle Favorite\n" +
                        "- Sean Lane\n" +
                        "- Hunter Pasterski";
        String thanksInfoHeader =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            SPECIAL THANKS\n" +
                        ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        String thanksInfo =
                "- Our families, for putting up with late nights and cranky mornings.\n" +
                        "- StackOverflow, for always sending us on a wild goose chase for an obscure forum post with the wrong answer.\n" +
                        "- Coffee, for doing what coffee do.\n" +
                        "- Josh, for helping us all nail down our understanding of interfaces.";
        String legalHeader =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            LEGAL INFORMATION\n" +
                        ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        String legalInfo =
                "© 2025 Echoes Beyond The Veil. All rights reserved.\n" +
                        "This game is a work of fiction. Any resemblance to actual events or persons, living or dead, is purely coincidental.";
        String thanksMessage =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            THANK YOU FOR PLAYING!\n" +
                        ConsoleColors.BLUE+"=========================================="+ConsoleColors.RESET;
        System.out.println(aboutHeader);
        System.out.println(aboutInfo);
        System.out.println();
        System.out.println(descHeader);
        System.out.println(descInfo);
        System.out.println();
        System.out.println(devTeamHeader);
        System.out.println(devInfo);
        System.out.println();
        System.out.println(thanksInfoHeader);
        System.out.println(thanksInfo);
        System.out.println();
        System.out.println(legalHeader);
        System.out.println(legalInfo);
        System.out.println();
        System.out.println(thanksMessage);
    }
    private static Integer setGameOverScore(Room room) {
        return room.getRoomPar() * 3;
    }
    private static void gameOver(Player player) {
        try {
            Thread.sleep(3000);
            System.out.println();
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": The shadows in the room deepen, their edges sharpening as they creep toward you. The air grows heavy, pressing down on your chest like a weight you can't shake. The faint hum that has haunted you\n" +
                    "since you arrived now rises to a deafening roar, and the walls seem to close in, their surfaces rippling like liquid darkness.");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+
                    ": You try to move, but your body refuses to obey. The shadows coil around you, their touch cold - unyielding. The last thing you see is the faint flicker of a candle in the distance, its light\n" +
                    "extinguished as the void consumes all. The last thing you hear is a low, hungry laugh - a sound that echoes in your mind long after everything else fades to black.");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.GREEN+"DISEMBODIED VOICE: "+ConsoleColors.RESET+"\""+player.getUpperName()+"... I told you to be gone. Now you will stay... Forever.");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": As the darkness envelops you completely, you feel a presence - vast, ancient, and utterly alien. It reaches for you, not with hands but with something indescribable - something that twists\n" +
                    "your very essence. You realize, too late, that death would have been a mercy. What comes next is not an end, but a beginning... An eternity as part of something far greater and far, far more\n" +
                    "terrible than you could ever comprehend...");
            System.out.println();
            System.out.println();
        } catch (InterruptedException ex) {
            System.out.println("Game Over Method Error");
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

