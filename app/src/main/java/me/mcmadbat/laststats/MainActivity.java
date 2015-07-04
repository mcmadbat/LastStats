package me.mcmadbat.laststats;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/*The main activity where all stats are displayed*/
public class MainActivity extends AppCompatActivity {

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This sets the action bar
        Toolbar actionbar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("INFO", "Main Activity stopped");
    }

    @Override
    protected void onResume() {
        Log.i("INFO", "Main Activity resumed.");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//    endregion

    //    region User Action Methods
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    endregion
}
