package apps.kelly.lineupmanagerv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
    }

    public void onBtnPlayersClick(View view){
        Intent i = new Intent(this, PlayerListActivity.class);
        startActivity(i);
    }

    public void onBtnTeamsClick(View view){
        Intent i = new Intent(this, TeamListActivity.class);
        startActivity(i);
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
