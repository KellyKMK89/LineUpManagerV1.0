package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Owner on 7/07/2016.
 */
public class EditPlayerActivity extends AppCompatActivity {

    Player player;
    Player updatedPlayer = new Player();

    String playerName;

    EditText txtName;
    EditText txtNumber;
    CheckBox cbBlocker;
    CheckBox cbJammer;
    CheckBox cbPivot;
    EditText txtNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        Bundle playerData = getIntent().getExtras();
        if (playerData == null) {
            return;
        }

        playerName = playerData.getString("playerName");

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        player = dbHandler.findPlayer(playerName);

        txtName = (EditText) findViewById(R.id.txtEnterName);
        txtNumber = (EditText) findViewById(R.id.txtEnterNumber);
        cbBlocker = (CheckBox) findViewById(R.id.cbBlocker);
        cbJammer = (CheckBox) findViewById(R.id.cbJammer);
        cbPivot = (CheckBox) findViewById(R.id.cbPivot);
        txtNotes = (EditText) findViewById(R.id.txtNotes);

        //Populate fields on load
        txtName.setText(player.getName());
        txtNumber.setText(player.getNumber());
        txtNotes.setText(player.getNotes());
        if(player.isJammer()){
            cbJammer.setChecked(true);
        }
        if(player.isBlocker()){
            cbBlocker.setChecked(true);
        }
        if(player.isPivot()){
            cbPivot.setChecked(true);
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbBlocker:
                if (checked) {
                    updatedPlayer.setBlocker(true);
                } else {
                    updatedPlayer.setBlocker(false);
                }
                break;
            case R.id.cbJammer:
                if (checked) {
                    updatedPlayer.setJammer(true);
                } else {
                    updatedPlayer.setJammer(false);
                }
                break;
            case R.id.cbPivot:
                if (checked) {
                    updatedPlayer.setPivot(true);
                } else {
                    updatedPlayer.setPivot(false);
                }
                break;
        }
    }

    public void savePlayer(View view){
        editPlayer(view);
        Intent i = new Intent(this, PlayerListActivity.class);
        startActivity(i);
    }

    public void editPlayer(View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        updatedPlayer.setName(txtName.getText().toString());
        updatedPlayer.setNumber(txtNumber.getText().toString());
        updatedPlayer.setNotes(txtNotes.getText().toString());
        if(cbPivot.isChecked()){
            updatedPlayer.setPivot(true);
        }
        else {
            updatedPlayer.setPivot(false);
        }
        if(cbJammer.isChecked()){
            updatedPlayer.setJammer(true);
        }
        else{
            updatedPlayer.setJammer(false);
        }
        if(cbBlocker.isChecked()){
            updatedPlayer.setBlocker(true);
        }
        else{
            updatedPlayer.setBlocker(false);
        }
        dbHandler.updatePlayer(playerName, updatedPlayer);
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
