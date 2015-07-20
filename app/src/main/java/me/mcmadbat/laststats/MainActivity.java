package me.mcmadbat.laststats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import me.mcmadbat.laststats.Helpers.Constants;
import me.mcmadbat.laststats.Helpers.HttpHelper;
import me.mcmadbat.laststats.Helpers.LfmApiHelper;

/*The main activity where all stats are displayed*/
public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor e;

    public void testCall(View v){
        String u = LfmApiHelper.getTopAlbums("mcmadbat3", null, null, null);
        HttpHelper httpHelper = new HttpHelper();

        TextView txt = (TextView) findViewById(R.id.textView);

        try {
            JSONObject json = new JSONObject(httpHelper.HttpGet(u));
            json = json.getJSONObject("topartists");
            JSONArray artists = json.getJSONArray("artist");

            String output = artists.getJSONObject(0).getString("name");
            txt.setText(output);
        }
        catch (Exception e){
            Log.wtf("INFO", e.getMessage());
        }

    }

    //region Constants Methods
    private void updateConstants(){
        Constants.updateFromFile();
    }

    private void saveConstants(){

    }
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateConstants();
        setContentView(R.layout.activity_main);

         /*Reads from SharePreferences to see if some data are saved*/
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e = sharedPref.edit();

        //This sets the action bar
        Toolbar actionbar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
    }

    @Override
    protected void onStop(){
        saveConstants();
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
