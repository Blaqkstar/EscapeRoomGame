package game;

public class Player implements Comparable<Player>{
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public String getName() {return name;}
    public int getScore() {return score;}

    public void setName(String newName) {
        this.name = newName;
    }
    public void setScore(int newScore) {
        this.score = newScore;
    }

    //This is the our example of the comparable. The difference bewtween a comparator and comparable is that
    //a comparator is used to compare different aspects bewtween two specfic objects of a certain type. Whereas a
    //a comparable defines how you can sort a large collection of objects.
    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(this.score, otherPlayer.score);
    }

    //The
}
