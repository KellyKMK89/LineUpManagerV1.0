package apps.kelly.lineupmanagerv10;

public class Player {

    private String name;

    private String number;

    private boolean blocker;
    private boolean jammer;
    private boolean pivot;

    private String notes;

    public Player (){

    }

    public Player(String name) {
        this.name = name;
    }


    //GETTERS/SETTERS
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public boolean isBlocker() {return blocker;}
    public void setBlocker(boolean blocker) {this.blocker = blocker;}

    public boolean isJammer() {return jammer;}
    public void setJammer(boolean jammer) {this.jammer = jammer;}

    public boolean isPivot() {return pivot;}
    public void setPivot(boolean pivot) {this.pivot = pivot;}

    public String getNotes() { return notes;}
    public void setNotes(String notes) {this.notes = notes;}
}
