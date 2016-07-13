package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Owner on 30/06/2016.
 */
public class AddTeamActivity extends AppCompatActivity {

    ArrayList<String> playerNames = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    Button saveTeam;
    ArrayList<Player> playerArr;

    EditText txtName;
    EditText txtNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        txtName = (EditText) findViewById(R.id.txtEnterName);
        listView = (ListView) findViewById(R.id.listView);
        saveTeam = (Button) findViewById(R.id.btnAddTeamSave);
        txtNotes = (EditText) findViewById(R.id.txtNotes);

        final DBHandler dbHandler = new DBHandler(this, null, null, 1);

        playerArr = dbHandler.getAllPlayers();

        for (Player p : playerArr) {
            String name = p.getName();
            playerNames.add(name);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, playerNames);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.toString().compareToIgnoreCase(p2.toString());
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }

    public void newTeam(View v) {

        final DBHandler db = new DBHandler(this, null, null, 1);
        final ArrayList<Player> teamOfPlayers = new ArrayList<>();

        String teamName = txtName.getText().toString();
        if (teamName.trim().equalsIgnoreCase("")) {
            Snackbar.make(v, "'Name' is a required field", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<>();
            for (int i = 0; i < checked.size(); i++) {
                int position = checked.keyAt(i);
                if (checked.valueAt(i))
                    selectedItems.add(adapter.getItem(position));
            }
            for (Player p : playerArr) {
                for (String s : selectedItems) {
                    if (p.getName().equalsIgnoreCase(s)) {
                        teamOfPlayers.add(p);
                    }
                }
            }
            Team t1 = new Team();
            t1.setName(teamName);
            t1.setNotes(txtNotes.getText().toString());
            t1.setPlayers(teamOfPlayers);
            db.addTeam(t1);

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

        switch (item.getItemId()) {
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
