package game;

import java.sql.Connection;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main{
    public static void main(final String[] args) throws Exception {
        final Logger log = LogManager.getLogger(Main.class.getName());
        ScoreDB scoreDB = new ScoreDB();
        /// starts background music before the game loop to ensure that it plays in the background while the game is running
        BackgroundMusic backgroundMusic = new BackgroundMusic("resources/music/bgmusic.wav"); // sets up music ref
        Thread musicThread = new Thread(backgroundMusic); // creates thread for music
        musicThread.start(); // begins music
        // this is going to read user input
        Scanner scanner = new Scanner(System.in);
        String input;

        // this loops until the user types 'exit'
        do {
            /// ------------------------------------------------------------------------- { <GAME START> } -----------------------------------------
            // ensures that bg music is always set back to the intro song when player chooses to play again
            if (!backgroundMusic.getFilePath().equals("resources/music/bgmusic.wav")) {
                backgroundMusic.changeMusic("resources/music/bgmusic.wav");
            }
            // prints game title
            printTitle();
            // Create list of rooms
            ArrayList<Room> rooms = new ArrayList<Room>();
            int gameOverScore = 0; // sets gameOverScore to 0 at the top of the loop instead of outside of it
            // Create tutorial room using SetNewRoom
            Room room = SetNewRoom(log, "Tutorial Room",false);
            //You could loop this and have an infinite number of rooms
            //Check out the RoomSetup Class for how it works!
            rooms.add(room);

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

            //playIntro(); /// -------------------------------------------------------------------------------- {<NARRATIVE INTRO HERE>} ------------

            gameOverScore = setGameOverScore(room); // setsGameOverScore

            // this one loops until gameOverScore has been reached
            do {
                if (player.getPlayerWins()) {
                    break;
                }
                checkScore(player, room);
                System.out.println();
                System.out.print(ConsoleColors.YELLOW+"Enter input (or 'help' for a list of available commands): "+ConsoleColors.RESET);
                input = scanner.nextLine(); // user input
                if (input.equalsIgnoreCase("exit")) {
                    // exit message
                    log.debug(ConsoleColors.PURPLE+"exiting game"+ConsoleColors.RESET);
                    System.out.println("Thanks for playing!");
                    Thread.sleep(2000);
                    scanner.close();
                    System.exit(0); // quits
                }
                log.debug(ConsoleColors.PURPLE+"user input received"+ConsoleColors.RESET);

                // processes user input
                /// ------------------------------------------------------------------------ { <LOOK ACTION HANDLER> } --------------------
                if (input.startsWith("look ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected look"+ConsoleColors.RESET);
                    System.out.println();
                    final String[] parts = input.split(" "); // splits input into parts, storing in an array
                    if (parts.length == 2) { // ensures that input consists of two parts
                        try {
                            final Direction direction = Direction.valueOf(parts[1].toLowerCase()); // gets direction

                            /// Joey added this to assign a direction variable to the player cause I need it for my final room
                            player.setFacing(direction);

                            if(room.getName().equalsIgnoreCase("Final Room")) {
                                room.SetExitDoor(room.getDoorAtDirection(direction).getFirst());
                                System.out.println("Exit door is now on the " + direction + " wall.");
                            }

                            if (!room.getItemsAtDirection(direction).isEmpty()) {
                                // Set all items in this direction to observed
                                for (Item item : room.getItemsAtDirection(direction)) {
                                    item.setObserved(true);
                                }
                                // displays item in chosen direction
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You look " + direction.getDescription() + " and see " + room.describeItemsToPlayer(room.getItemsAtDirection(direction)));
                            }
                            else{
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You look " + direction.getDescription() + " and see a blank wall.");
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid direction. Please enter one of the following: north, south, east, west."); // handles input issues
                        }
                    } else {
                        System.out.println("Invalid input. Please use the format 'look <direction>'."); // handles formatting issues
                    }
                    /// -------------------------------------------------------------------- { <INSPECT ACTION HANDLER> } --------------------
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
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        }
                        // Handles input of unknown item
                        catch (Exception e) {
                            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                        }
                    }
                    else {
                        System.out.println("Invalid input. Please use the format 'inspect <item>'."); // handles formatting issues
                    }
                }
                /// ------------------------------------------------------------------------ { <USE ACTION HANDLER> } --------------------
                else if (input.startsWith("use ")) {
                    log.debug(ConsoleColors.PURPLE+"player selected use"+ConsoleColors.RESET);
                    final String[] parts = input.split(" "); // splits input into parts, storing in an array
                    if (parts.length == 2) { // ensures that input consists of two parts
                        try {
                            // normalizes input to lowercase and trims whitespace
                            String itemName = parts[1].trim().toLowerCase();
                            log.debug(ConsoleColors.PURPLE+"Searching for item with key '" + itemName + "'"+ConsoleColors.RESET); // for debug purposes

                            // Allow player to use transmorgrifer by calling it a "machine"
                            if (itemName.equalsIgnoreCase("machine")) {itemName="transmorgrifier";}

                            ///  DEBUG MESSAGES BEGIN
                            // declare item that is being inspected
                            Item item = room.getItems().get(itemName);
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
                                /// -------------------------------------------------------------{ <KEY USE HANDLER> } ----------------------------------------
                                if (item instanceof Key) {
                                    if (room.GetExitDoor().isObserved()) {
                                        item.use(); // uses the key
                                        room.GetExitDoor().unlockDoor(); // unlocks the door
                                    }
                                    else {
                                        System.out.println(ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": You have not yet seen anything to unlock.");
                                    }
                                }
                                /// -------------------------------------------------------------{ <LEVER USE HANDLER> } ----------------------------------------
                                else if (item instanceof Lever) {
                                    item.use(); // toggles lever
                                }
                                // handles other items
                                /// -------------------------------------------------------------{ <DEFAULT USE HANDLER> } ----------------------------------------
                                else {
                                    item.use(); // default use behavior
                                }
                                player.setScore(player.getScore() + 1); // successful action of any kind increments player score by one
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
                /// ----------------------------------------------------------------------  { <OPEN ACTION HANDLER> }--------------------
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
                                            room = SetNewRoom(log, "The Conservatory",false);
                                            backgroundMusic.changeMusic("resources/music/conservatoryMusic.wav"); /// -----------------  { MUSIC CHANGE }
                                        } else if (room.getName().equalsIgnoreCase("The Conservatory")) {
                                            room = SetNewRoom(log, "The Lab",false);
                                        } else if (room.getName().equalsIgnoreCase("The Lab")) {
                                            room = SetNewRoom(log, "Final Room",false);
                                            backgroundMusic.changeMusic("resources/music/finalRoomMusic.wav"); /// -----------------  { MUSIC CHANGE }
                                        }
                                        ///  FINAL ROOM AND MULTIPLE ENDINGS
                                        // player picks correct door
                                        else if (room.getName().equalsIgnoreCase("Final Room") && player.getFacing() == Direction.east) {
                                            room = SetNewRoom(log, "THE END",true);
                                        }
                                        // player picks incorrect door
                                        else if(room.getName().equalsIgnoreCase("Final Room") && player.getFacing() != Direction.east) {
                                            room = SetNewRoom(log, "Consequences",false);
                                            backgroundMusic.changeMusic("resources/music/consequencesMusic.wav"); /// -----------------  { MUSIC CHANGE }
                                        }
                                        System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and step into the next room.");
                                        System.out.println();
                                        Thread.sleep(300);
                                        // room intro blurb dependent on the room being entered. Tutorial room intro handled at start of main()
                                        if (room.getName().equalsIgnoreCase("The Conservatory")) {
                                            // prints conservatory intro
                                            System.out.println(room.getIntroBlurb());
                                        } else if (room.getName().equalsIgnoreCase("The Lab")) {
                                            System.out.println(room.getIntroBlurb());

                                        } else if (room.getName().equalsIgnoreCase("Final Room")) {
                                            Room.FinalRoomDialog();
                                        }
                                        else if (room.getName().equalsIgnoreCase("THE END")) {
                                            System.out.println(room.getIntroBlurb());
                                        }
                                        else if (room.getName().equalsIgnoreCase("Consequences")) {
                                            System.out.println(room.getIntroBlurb());
                                            gameOverSuccess(player); /// PLAYER WINS!
                                        }
                                    }
                                    else{
                                        System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": It's locked.");
                                    }
                                }
                                else{
                                    System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                                }
                            } else {
                                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You can't open that.");
                            }
                            player.setScore(player.getScore() + 1); // successful action increments player score by one
                        } catch(Exception e){
                            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You do not see any " + parts[1]);
                        }
                    }
                }
                /// SPEEDRUN OPTIONS --------ONLY FOR USE IN DEVELOPMENT-----------------------
                else if (input.equalsIgnoreCase("skip to lab")){
                    room = SetNewRoom(log, "The Lab",false); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                    System.out.println();
                    System.out.println(room.getIntroBlurb());
                }
                else if (input.equalsIgnoreCase("skip to conservatory")){
                    room = SetNewRoom(log, "The Conservatory",false); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    backgroundMusic.changeMusic("resources/music/conservatoryMusic.wav"); /// -----------------  { <MUSIC CHANGE> }
                    System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You open the door and enter a new room. Welcome to " + room.getName());
                    System.out.println();
                    System.out.println(room.getIntroBlurb());
                }
                else if (input.equalsIgnoreCase("skip to final")){
                    room = SetNewRoom(log, "Final Room",false); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    backgroundMusic.changeMusic("resources/music/finalRoomMusic.wav"); /// -----------------  { <MUSIC CHANGE> }
                    System.out.println();
                    System.out.println(room.getIntroBlurb());
                    Room.FinalRoomDialog();
                }
                else if (input.equalsIgnoreCase("skip to ending")){
                    room = SetNewRoom(log, "Consequences",false); /// DEFINES THE ROOM ON THE OTHER SIDE OF THE DOOR
                    backgroundMusic.changeMusic("resources/music/conservatoryMusic.wav"); /// -----------------  { <MUSIC CHANGE> }
                    System.out.println();
                    System.out.println(room.getIntroBlurb());
                    gameOverSuccess(player);
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
            } while (player.getScore() < gameOverScore && !player.getPlayerWins().equals(true)); // core failure conditions

            /// ------------------------------------------------------------------------- { <GAME OVER> } -----------------------------------------
            if (player.getScore() >= gameOverScore) {
                gameOver(player);
            }
            do {
                System.out.print(ConsoleColors.YELLOW+"Would you like to play again? (Y / N): "+ConsoleColors.RESET);
                input = scanner.nextLine().trim().toLowerCase(); // collects player choice
            } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
            if (input.equalsIgnoreCase("n")) {
                System.exit(0);
                //input = "exit"; // exits game
            } else continue;
            System.out.println();
            System.out.println();
            // else loop repeats due to intrinsic do-while rules
        } while (!input.equalsIgnoreCase("exit")); /// repeats loop until user types 'exit'... this is always true, but I created a band-aid by adding a check for 'exit' just after user input is accepted

        // exit message
        log.info("exiting game...");
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static void printCommands() {
        // how to play
        System.out.println();
        String howToPlayInfo = ConsoleColors.YELLOW+"CMD SYNTAX"+ConsoleColors.RESET+": verb (look/inspect/use/open) + noun (usable items are highlighted in "+ConsoleColors.CYAN+"CYAN"+ConsoleColors.RESET+" after using the 'look' command)";
        System.out.println(howToPlayInfo);
        System.out.println();
        // list of available commands
        System.out.println(ConsoleColors.BLUE+"AVAILABLE COMMANDS"+ConsoleColors.RESET+":");
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
        String ver = "v1.0"; // this is just a rough estimate based on what we've done so far vs what remains. Will say v1.0 when we hand in
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
        System.out.println();
        System.out.println("Silence...");
        Thread.sleep(3000);
        System.out.println();
        System.out.println();
        System.out.println("Darkness...");
        Thread.sleep(3000);
        System.out.println();
        System.out.println();
        System.out.println("A single, faint pulse of light, like a heartbeat...");
        Thread.sleep(3000);
        System.out.println();
        System.out.println();
        System.out.println("And then... ");
        Thread.sleep(4000);
        System.out.println();
        Thread.sleep(2000);
        System.out.println();
        Thread.sleep(2000);
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
                        "This game is a work of fiction. Any resemblance to actual events or persons, living or dead, is purely coincidental.\n\n" +
                        "Music by Cthulhu Mythos Music, Experia, Allan Ariza, & Iron Cthulhu Apocalypse. All rights reserved to their respective owners.";
        String thanksMessage =
                ConsoleColors.BLUE+"==========================================\n"+ConsoleColors.RESET +
                        "            THANKS FOR PLAYING!\n" +
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
    private static void checkScore(Player player, Room room) {
        int threshold = (room.getRoomPar()*3) / 2;
        if (player.getScore() == threshold) {
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": The hair on the back of your neck stands on end, a primal instinct warning you of something unseen.");
        }
    }
    private static void gameOver(Player player) {
        String gameOver = ConsoleColors.RED+"\n" +
                " ___   ___         ___        ___         ___   ___  \n" +
                "|     |   | |\\ /| |          |   | |  /  |     |   | \n" +
                "| +-  |-+-| | + | |-+-       |   | | +   |-+-  |-+-  \n" +
                "|   | |   | |   | |          |   | |/    |     |  \\  \n" +
                " ---               ---        ---         ---        \n" +
                "                                                     \n"+ConsoleColors.RESET;
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
            Thread.sleep(3000);
            System.out.println(gameOver);
            System.out.println();
            Thread.sleep(3000);
            System.out.println();
            Thread.sleep(300);
            System.out.println();
            Thread.sleep(300);
            System.out.println();
            Thread.sleep(300);
            System.out.println();
            Thread.sleep(300);
            System.out.println();
            Thread.sleep(8000);
        } catch (InterruptedException ex) {
            System.out.println("Game Over Method Error");
        }
    }
    private static void gameOverSuccess(Player player) {
        String gameOver = ConsoleColors.RED+"\n" +
                " ___   ___         ___        ___         ___   ___  \n" +
                "|     |   | |\\ /| |          |   | |  /  |     |   | \n" +
                "| +-  |-+-| | + | |-+-       |   | | +   |-+-  |-+-  \n" +
                "|   | |   | |   | |          |   | |/    |     |  \\  \n" +
                " ---               ---        ---         ---        \n" +
                "                                                     \n"+ConsoleColors.RESET;
        try {
            System.out.println();
            ///  the revelation
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": As you step forward, a voice echoes through the void - a voice that is both familiar and alien, as if it is speaking from within your own mind.");
            Thread.sleep(3000);
            System.out.println();
            //  voice speaks
            System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                    ": 'You have done well to escape the labyrinth but tell me, wanderer... Do you truly believe you have won?'");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                    ": 'You are already dead. The rooms you traversed were not a prison of flesh and stone, but a purgatory of your own making. A final test of your will, your resilience, your desire to escape.'");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": The words sink into your mind like stones dropped into a still pond. Memories flood back - fragmented, disjointed. A car crash. A hospital bed. The sound of a flatlining heart monitor.\n" +
                    "The realization hits you like a tidal wave - you never escaped death. You have been here all along.");
            Thread.sleep(3000);
            System.out.println();
            ///  the choice
            System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": The voice speaks again, its tone shifting from cold indifference to something almost... pitying.");
            Thread.sleep(3000);
            System.out.println();
            System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                    ": 'You have two choices, wanderer. You may step forward into the void and cease to exist - no pain, no fear, no memory. Or you may turn back and face the darkness that awaits. A darkness that\n" +
                    "will consume you, body and soul, for all eternity.'");
            Thread.sleep(3000);
            System.out.println();
            // player choice
            Scanner scanner = new Scanner(System.in);
            String input = "UNDEFINED FINAL CHOICE";
            do {
                // prompt for decision
                System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                        ": 'Make your choice.'");
                System.out.println();
                System.out.println(ConsoleColors.BLUE+"OPTION 1"+ConsoleColors.RESET+": Step forward into the void\n" +
                        ConsoleColors.BLUE+"OPTION 2"+ConsoleColors.RESET+": Turn back and face the darkness");
                System.out.println();
                System.out.print(ConsoleColors.YELLOW+"Enter input (1 or 2): "+ConsoleColors.RESET);
                // take in user input
                input = scanner.nextLine().toLowerCase().trim();
            } while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2"));
            ///  the consequences
            // different ending depending on final choice
            if (input.equalsIgnoreCase("1")) {
                ///  STEP INTO THE VOID
                System.out.println();
                System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                        ": 'And so, you choose oblivion. A fitting end for one who sought escape above all else.'");
                Thread.sleep(3000);
                System.out.println();
                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": You take a step forward, and the void envelops you. There is no pain, no fear - only a profound sense of peace. The darkness closes in and, for a moment, you feel as though you are floating.");
                Thread.sleep(3000);
                System.out.println();
                System.out.println();
                System.out.println("Then, nothing.");
                Thread.sleep(1300);
                System.out.println();
                Thread.sleep(1300);
                System.out.println();
                System.out.println("No thoughts.");
                Thread.sleep(1300);
                System.out.println();
                Thread.sleep(1300);
                System.out.println();
                System.out.println("No memories.");
                Thread.sleep(1300);
                System.out.println();
                Thread.sleep(1300);
                System.out.println();
                System.out.println("No self.");
                Thread.sleep(1300);
                System.out.println();
                Thread.sleep(1300);
                System.out.println();
                System.out.println("You are gone.");
                Thread.sleep(3000);
                System.out.println();
            } else if (input.equalsIgnoreCase("2")) {
                ///  RETREAT TO DARKNESS
                System.out.println(ConsoleColors.GREEN+ "DISEMBODIED VOICE" +ConsoleColors.RESET+
                        ": 'You have chosen... Poorly.'");
                Thread.sleep(3000);
                System.out.println();
                System.out.println(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The darkness engulfs you, and the pain begins. It is not a physical pain, but something deeper, more primal. It is the pain of being known, of being consumed, of being torn apart and reassembled\n" +
                        "over and over again for all eternity.\n\n"+
                        "You scream, but no sound escapes.\n\n" +
                        "You struggle, but there is no way out.\n\n" +
                        "The darkness is endless, and so is your torment.");
                Thread.sleep(3000);
                System.out.println();
            }
            System.out.println();
            Thread.sleep(3000);
            System.out.println(ConsoleColors.BLUE+ "IN THE END, THERE IS NO ESCAPE. ONLY THE CHOICES WE MAKE, AND THE CONSEQUENCES WE MUST ENDURE."+ConsoleColors.RESET);
            Thread.sleep(3000);
            System.out.println();
            System.out.println();
            System.out.println(gameOver);
            Thread.sleep(8000);
            player.setPlayerWins(true);
        } catch (InterruptedException ex) {
            System.out.println("Game Over (Success) Method Error");
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

    private static Room SetNewRoom(Logger log, String roomName, boolean rightDoor) throws Exception {
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
        else if (roomName.equalsIgnoreCase("Final Room")) {
            room = roomSetup.MakeRoom_FinalRoom();
        }
        else if (roomName.equalsIgnoreCase("THE END")) {
            room = roomSetup.MakeRoom_TheEnd(rightDoor);
        }
        else if (roomName.equalsIgnoreCase("Consequences")) {
            room = roomSetup.MakeRoom_Consequences();
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

