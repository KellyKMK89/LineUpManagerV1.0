package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerDetailActivity extends AppCompatActivity {


    EditText txtNumber;
    EditText txtNotes;
    TextView txtPosition;
    TextView lblTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        Bundle playerData = getIntent().getExtras();
        if (playerData == null) {
            return;
        }

        String playerName = playerData.getString("playerName");
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblTitle.setText(playerName);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Player player = dbHandler.findPlayer(playerName);

        txtNumber = (EditText) findViewById(R.id.txtEnterNumber);
        txtPosition = (TextView) findViewById(R.id.txtPosition);
        txtNotes = (EditText) findViewById(R.id.txtNotes);

        txtNumber.setText(player.getNumber());
        txtPosition.setText(positionString(player));
        txtNotes.setText(player.getNotes());
    }

    private String positionString(Player player) {
        String s = "";
        if (player.isBlocker()) {
            s += "Blocker ";
        }
        if (player.isJammer()) {
            s += "Jammer ";
        }
        if (player.isPivot()) {
            s += "Pivot ";
        }

        return s;
    }

    public void editPlayer(View view){
        Intent i = new Intent(view.getContext(), EditPlayerActivity.class);
        String name = lblTitle.getText().toString();
        i.putExtra("playerName", name);
        startActivity(i);
    }

    public void removePlayer(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        boolean result = dbHandler.deletePlayer(lblTitle.getText().toString());
        if(result){
            Intent i = new Intent(this, PlayerListActivity.class);
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
