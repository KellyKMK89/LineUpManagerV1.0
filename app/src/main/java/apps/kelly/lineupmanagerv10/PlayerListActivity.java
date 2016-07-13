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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class PlayerListActivity extends AppCompatActivity {

    ArrayList<String> playerNames = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    android.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        /*The ListView needs an array of items to display, an adapter to manage the items in that
        array and a layout definition to dictate how items are to be presented to the user.*/
        listView = (ListView) findViewById(R.id.listView);
        searchView = (android.widget.SearchView) findViewById(R.id.searchview);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        final ArrayList<Player> playerArr = dbHandler.getAllPlayers();
        for(Player p : playerArr){
            String name = p.getName();
            playerNames.add(name);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,playerNames);

        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.toString().compareToIgnoreCase(p2.toString());
            }
        });

        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), PlayerDetailActivity.class);
                String item = ((TextView)view).getText().toString();
                i.putExtra("playerName", item);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddPlayerActivity.class);
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
