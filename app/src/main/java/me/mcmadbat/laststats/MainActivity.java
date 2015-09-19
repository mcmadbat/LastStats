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
import org.w3c.dom.Text;

import java.util.List;

import me.mcmadbat.laststats.Helpers.Constants;
import me.mcmadbat.laststats.Helpers.HttpHelper;
import me.mcmadbat.laststats.Helpers.LfmApiHelper;

/*The main activity where all stats are displayed*/
public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor e;

    public void testCall(View v){
        List<String> o = LfmApiHelper.getTopArtists("mcmadbat3",null,null,null);

        TextView txt = (TextView) findViewById(R.id.textView);
        String cur = "";
        for (int x = 0; x < o.size(); x++) {
            cur += o.get(x) + "\n";
        }
        txt.setText(cur);
    }

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
