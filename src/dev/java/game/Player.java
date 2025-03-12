package game;

/**
 * Class for storing information regarding the player while they progress through the game.
 */
public class Player implements Comparable<Player>{
    private String name;
    private int score;
    private Main.Direction facing;
    private Boolean playerWins = false;
    private Boolean hasSeenPainting = false;

    /**
     * Constructor with parameters for the player class.
     * @param name Takes in a string.
     * @param score Takes in a Integer.
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Method to get the player's name.
     * @return Returns a string.
     */
    public String getName() {return name;}

    /**
     * Method to get the player's name in uppercase using an UnaryOperator.
     * @return Returns a string.
     */
    public String getUpperName() {
        UpperNameOperator<String> playerUpperName = String::toUpperCase;
        return playerUpperName.apply(name);
    }

    /**
     * Method to get the player's score.
     * @return Returns the player's score.
     */
    public int getScore() {return score;}

    public Boolean getHasSeenPainting() {return hasSeenPainting;}
    public void setHasSeenPainting(Boolean hasSeenPainting) {
        this.hasSeenPainting = hasSeenPainting;
    }

    /**
     * Method to set the player's name.
     * @param newName Takes in a string.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Method to set the player's score.
     * @param newScore Takes in a int.
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Method to get the Direction the player is facing
     * @return Returns a Main.Direction enum
     */
    public Main.Direction getFacing() {
        return facing;
    }

    /**
     * Method to set the Direction the player is facing
     * @param newFacing Takes in a Main.Direction enum
     */
    public void setFacing(Main.Direction newFacing) {
        this.facing = newFacing;
    }

    /**
     * Method to get a boolean of whether a player wins
     * @return Returns a boolean
     */
    public Boolean getPlayerWins() {return playerWins;}

    /**
     * Method to set the boolean of whether a player wins
     * @param newPlayerWins Takes in a boolean
     */
    public void setPlayerWins(Boolean newPlayerWins) {
        this.playerWins = newPlayerWins;
    }
    //This is the our example of the comparable. The difference bewtween a comparator and comparable is that
    //a comparator is used to compare different aspects bewtween two specfic objects of a certain type. Whereas a
    //a comparable defines how you can sort a large collection of objects.

    /**
     * Method to Compare players
     * @param otherPlayer the object to be compared.
     * @return Returns an int
     */
    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(this.score, otherPlayer.score);
    }

}
