package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Owner on 17/06/2016.
 */
public class AddPlayerActivity extends AppCompatActivity {

    Player player = new Player();

    EditText txtName;
    EditText txtNumber;
    CheckBox cbBlocker;
    CheckBox cbJammer;
    CheckBox cbPivot;
    EditText txtNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        txtName = (EditText) findViewById(R.id.txtEnterName);
        txtNumber = (EditText) findViewById(R.id.txtEnterNumber);
        cbBlocker = (CheckBox) findViewById(R.id.cbBlocker);
        cbJammer = (CheckBox) findViewById(R.id.cbJammer);
        cbPivot = (CheckBox) findViewById(R.id.cbPivot);
        txtNotes = (EditText) findViewById(R.id.txtNotes);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.cbBlocker:
                if(checked) {
                    player.setBlocker(true);
                }
                else{
                    player.setBlocker(false);
                }
                break;
            case R.id.cbJammer:
                if(checked) {
                    player.setJammer(true);
                }
                else{
                    player.setJammer(false);
                }
                break;
            case R.id.cbPivot:
                if(checked) {
                    player.setPivot(true);
                }
                else{
                    player.setPivot(false);
                }
                break;
        }
    }

    public void newPlayer(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        String name = txtName.getText().toString();

        if(name.trim().equalsIgnoreCase("")){
            Snackbar.make(view, "'Name' is a required field", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
            player.setName(txtName.getText().toString());
            player.setNumber(txtNumber.getText().toString());
            player.setNotes(txtNotes.getText().toString());
            dbHandler.addPlayer(player);

            Snackbar.make(view, "New Player added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

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
