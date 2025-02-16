package game;

public class Player {
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

}
