package apps.kelly.lineupmanagerv10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Owner on 23/06/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "playerDB.db";
    public static final String TABLE_PLAYERS = "players";
    public static final String TABLE_TEAMS = "teams";
    public static final String TEAM_NAME = "teamName";
    public static final String TEAM_NOTES = "teamNotes";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_BLOCKER = "blocker";
    public static final String COLUMN_JAMMER = "jammer";
    public static final String COLUMN_PIVOT = "pivot";
    public static final String COLUMN_NOTES = "notes";

    public static final String P1 = "p1";
    public static final String P2 = "p2";
    public static final String P3 = "p3";
    public static final String P4 = "p4";
    public static final String P5 = "p5";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //Create player tables when db is initialised
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "(" + COLUMN_NAME +
                " TEXT," + COLUMN_NUMBER + " TEXT," + COLUMN_BLOCKER + " INTEGER," + COLUMN_JAMMER
                + " INTEGER," + COLUMN_PIVOT + " INTEGER," + COLUMN_NOTES + " TEXT" + ")";


        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + "(" + TEAM_NAME + " TEXT, " +
                TEAM_NOTES + " TEXT," + P1 + " TEXT," + P2 + " TEXT," + P3 + " TEXT," + P4 + " TEXT,"
                + P5 + " TEXT)";

        db.execSQL(CREATE_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYER_TABLE);
    }

    /*        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + "(" + TEAM_NAME + " TEXT, "+
                "p1 TEXT, p2 TEXT, p3 TEXT, p4 TEXT, p5 TEXT, p6 TEXT, p7 TEXT, p8 TEXT, " +
                "p9 TEXT, p10 TEXT, p11 TEXT, p12 TEXT, p13 TEXT, p14 TEXT, r1 TEXT, r2 TEXT, " +
                "r3 TEXT, r4 TEXT, r5 TEXT, r6 TEXT )";*/

    /*The onUpgrade() method is called when the handler is invoked with a greater database version
   number from the one previously used.*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //remove old database and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);

        onCreate(db);
    }

    //insert a new DB record(row) as a Player object
    public void addPlayer(Player player) {
        //cast boolean to int
        int isBlocker = (player.isBlocker()) ? 1 : 0;
        int isJammer = (player.isJammer()) ? 1 : 0;
        int isPivot = (player.isPivot()) ? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.getName());
        values.put(COLUMN_NUMBER, player.getNumber());
        values.put(COLUMN_BLOCKER, isBlocker);
        values.put(COLUMN_JAMMER, isJammer);
        values.put(COLUMN_PIVOT, isPivot);
        values.put(COLUMN_NOTES, player.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PLAYERS, null, values);
       // db.close();
    }

    public void addTeam(Team team) {
        ArrayList<Player> teamPlayers = team.getPlayers();
        ContentValues values = new ContentValues();

        values.put(TEAM_NAME, team.getName());
        values.put(TEAM_NOTES, team.getNotes());
        int size = teamPlayers.size();

        if (size >= 1) {
            if (teamPlayers.get(0) != null) {
                values.put(P1, teamPlayers.get(0).getName());
            } else {
                values.put(P1, "null");
            }
        }
        if (size >= 2) {
            if (teamPlayers.get(1) != null) {
                values.put(P2, teamPlayers.get(1).getName());
            } else {
                values.put(P2, "null");
            }
        }
        if (size >= 3) {
            if (teamPlayers.get(2) != null) {
                values.put(P3, teamPlayers.get(2).getName());
            } else {
                values.put(P3, "null");
            }
        }
        if (size >= 4) {
            if (teamPlayers.get(3) != null) {
                values.put(P4, teamPlayers.get(3).getName());
            } else {
                values.put(P4, "null");
            }
        }
        if (size >= 5) {
            if (teamPlayers.get(4) != null) {
                values.put(P5, teamPlayers.get(4).getName());
            } else {
                values.put(P5, "null");
            }
        }


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TEAMS, null, values);
       // db.close();
    }

    private boolean intToBool(Integer i) {
        boolean b = false;
        if (i == 1) {
            b = true;
        } else if (i == 0) {
            b = false;
        }
        return b;
    }

    public Player findPlayer(String playerName) {
        String query = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_NAME + " =\"" +
                playerName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player = new Player();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            player.setName(cursor.getString(0));
            player.setNumber(cursor.getString(1));
            player.setBlocker(intToBool(cursor.getInt(2)));
            player.setJammer(intToBool(cursor.getInt(3)));
            player.setPivot(intToBool(cursor.getInt(4)));
            player.setNotes(cursor.getString(5));
            cursor.close();
        } else {
            player = null;
        }
      //  db.close();
        return player;
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> playerArr = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYERS;
        Cursor cursor = db.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    Player player = new Player();
                    player.setName(cursor.getString(0));
                    player.setNumber(cursor.getString(1));
                    player.setBlocker(intToBool(cursor.getInt(2)));
                    player.setJammer(intToBool(cursor.getInt(3)));
                    player.setPivot(intToBool(cursor.getInt(4)));
                    player.setNotes(cursor.getString(5));
                    playerArr.add(player);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
        return playerArr;
    }

    public Team findTeam(String teamName) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> allPlayers;
        ArrayList<String> playerNames = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TEAMS + " WHERE " + TEAM_NAME + " =\"" +
                teamName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        allPlayers = this.getAllPlayers();
        Cursor cursor = db.rawQuery(query, null);
        Team team = new Team();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            team.setName(cursor.getString(0));
            team.setNotes(cursor.getString(1));
            playerNames.add(cursor.getString(2));
            playerNames.add(cursor.getString(3));
            playerNames.add(cursor.getString(4));
            playerNames.add(cursor.getString(5));
            playerNames.add(cursor.getString(6));
            for (String s : playerNames) {
                for (Player p : allPlayers) {
                    if (p.getName().equalsIgnoreCase(s)) {
                        players.add(p);
                    }
                }
            }
            team.setPlayers(players);
            cursor.close();
        }
       // db.close();
        return team;
    }

    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teamArr = new ArrayList<>();
        ArrayList<Player> allPlayers;
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<String> playerNames = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        allPlayers = this.getAllPlayers();

        String query = "SELECT * FROM " + TABLE_TEAMS;
        Cursor cursor = db.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    Team team = new Team();
                    team.setName(cursor.getString(0));
                    team.setNotes(cursor.getString(1));
                    playerNames.add(cursor.getString(2));
                    playerNames.add(cursor.getString(3));
                    playerNames.add(cursor.getString(4));
                    playerNames.add(cursor.getString(5));
                    playerNames.add(cursor.getString(6));
                    for (String s : playerNames) {
                        for (Player p : allPlayers) {
                            if (p.getName().equalsIgnoreCase(s)) {
                                players.add(p);
                            }
                        }
                    }
                    team.setPlayers(players);
                    teamArr.add(team);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return teamArr;
    }

    public boolean deletePlayer(String playerName) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_NAME + " =\"" +
                playerName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //Player player = new Player();
        if (cursor.moveToFirst()) {
            // player.setName(cursor.getString(0));
            db.delete(TABLE_PLAYERS, COLUMN_NAME + " = ?", new String[]{playerName});
            cursor.close();
            result = true;
        }

       // db.close();
        return result;
    }

    public boolean updatePlayer(String playerName, Player updatedPlayer) {
        //cast boolean to int
        int isBlocker = (updatedPlayer.isBlocker()) ? 1 : 0;
        int isJammer = (updatedPlayer.isJammer()) ? 1 : 0;
        int isPivot = (updatedPlayer.isPivot()) ? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, updatedPlayer.getName());
        values.put(COLUMN_NUMBER, updatedPlayer.getNumber());
        values.put(COLUMN_BLOCKER, isBlocker);
        values.put(COLUMN_JAMMER, isJammer);
        values.put(COLUMN_PIVOT, isPivot);
        values.put(COLUMN_NOTES, updatedPlayer.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.update(TABLE_PLAYERS, values, COLUMN_NAME + "=" + playerName, null);
        db.update(TABLE_PLAYERS, values, COLUMN_NAME + " = ?", new String[] {playerName});
       // db.close();
        return true;
    }

    public boolean deleteTeam(String teamName) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_TEAMS + " WHERE " + TEAM_NAME + " =\"" +
                teamName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.delete(TABLE_TEAMS, TEAM_NAME + " = ?", new String[]{teamName});
            cursor.close();
            result = true;
        }
        //db.close();
        return result;
    }


}


















