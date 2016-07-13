package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Owner on 30/06/2016.
 */
public class TeamListActivity extends AppCompatActivity{

    ArrayAdapter<String> adapter;
    ListView listView;
    Button saveTeam;
    ArrayList<String> teamNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        listView = (ListView) findViewById(R.id.listView);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        final ArrayList<Team> teamArr = dbHandler.getAllTeams();
        for(Team t : teamArr){
            teamNames.add(t.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamNames);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.toString().compareToIgnoreCase(p2.toString());
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), TeamDetailActivity.class);
                String item = ((TextView)view).getText().toString();
                i.putExtra("teamName", item);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddTeamActivity.class);
                startActivity(i);
            }
        });

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
