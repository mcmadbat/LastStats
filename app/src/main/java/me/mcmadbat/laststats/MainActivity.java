package me.mcmadbat.laststats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import me.mcmadbat.laststats.Fragments.TopListFragment;
import me.mcmadbat.laststats.Fragments.ViewPagerFragment;


/*The main activity where all stats are displayed*/
public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private HelpLiveo mHelpLiveo;

    //region Lifecycle Methods

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

    //region Navigation Drawer Methods
    @Override
    public void onInt(Bundle savedInstanceState) {

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String user = sharedPref.getString("user", "mcmadbat3");
        String realname = sharedPref.getString("realname", "David Zhang");

        if (user == ""){
            //// TODO: Prompt the User to enter name
        }

        // User Information
        this.userName.setText(user);
        this.userEmail.setText(realname);
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);

        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.addSubHeader("Timeframe"); //Item subHeader
        mHelpLiveo.add("All Time", R.mipmap.app_icon);
//        mHelpLiveo.addSeparator(); // Item separator

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                .colorItemSelected(R.color.nliveo_red_colorPrimary)
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)

                .footerItem("About", R.mipmap.app_icon)

                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 0 ? 15 : 0);
    }

    //handles the changing of the fragments
    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position){
            case 0:
                mFragment = new ViewPagerFragment();
                break;

            default:
                mFragment = TopListFragment.newInstance(mHelpLiveo.get(position).getName());
                break;
        }

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }

        setElevationToolBar(position != 0 ? 15 : 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeDrawer();
        }
    };

    //endregion
}
