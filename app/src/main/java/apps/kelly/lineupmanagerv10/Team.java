package apps.kelly.lineupmanagerv10;

import java.util.ArrayList;

/**
 * Created by Owner on 30/06/2016.
 */
public class Team {

    private String name;
    private ArrayList<Player> players;

    private String notes;

    private Player p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14;
    private Player r1, r2, r3, r4, r5, r6;

    public Team(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getNotes() {return notes;}

    public void setNotes(String notes) {this.notes = notes;}
}
