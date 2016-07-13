package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Owner on 5/07/2016.
 */
public class TeamDetailActivity extends AppCompatActivity{

    ArrayList<String> playerNames = new ArrayList<>();
    ArrayList<Player> players;
    ArrayAdapter<String> adapter;
    ListView listView;

    EditText txtNotes;
    TextView lblTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        Bundle teamData = getIntent().getExtras();
        if(teamData == null){
            return;
        }

        String teamName = teamData.getString("teamName");
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        listView = (ListView) findViewById(R.id.listView);
        txtNotes = (EditText) findViewById(R.id.txtNotes);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Team team = dbHandler.findTeam(teamName);

        players = team.getPlayers();

        for (Player p : players) {
            String name = p.getName();
            playerNames.add(name);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerNames);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.toString().compareToIgnoreCase(p2.toString());
            }
        });

        listView.setAdapter(adapter);

        lblTitle.setText(teamName);
        txtNotes.setText(team.getNotes());
    }

    public void removeTeam(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        boolean result = dbHandler.deleteTeam(lblTitle.getText().toString());
        if(result){
            Intent i = new Intent(this, TeamListActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_players:
                Intent i = new Intent(this, PlayerListActivity.class);
                startActivity(i);
                break;
            case R.id.menu_teams:
                Intent a = new Intent(this, TeamListActivity.class);
                startActivity(a);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
