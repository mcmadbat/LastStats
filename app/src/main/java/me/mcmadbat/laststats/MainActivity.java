package me.mcmadbat.laststats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import me.mcmadbat.laststats.Fragments.HomeFragment;
import me.mcmadbat.laststats.Fragments.LoginFragment;
import me.mcmadbat.laststats.Fragments.TopListFragment;
import me.mcmadbat.laststats.Fragments.ViewPagerFragment;
import me.mcmadbat.laststats.Helpers.UserHelper;


/*The main activity where all stats are displayed*/
public class MainActivity extends NavigationLiveo implements OnItemClickListener {
    UserHelper user = null;

    private HelpLiveo mHelpLiveo;

    //region Lifecycle Methods

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("INFO", "Main Activity stopped");
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        user.saveToMemory();
    }

    @Override
    protected void onResume() {
        if (user != null){
            updateDrawerFromUser(user);
        }

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

        File t = new File(getApplicationContext().getFilesDir(),"UserInfo.txt");
        user = new UserHelper(t);

        updateDrawerFromUser(user);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add("Home");
        //mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("Top Artists");
        mHelpLiveo.add("Top Albums");
        mHelpLiveo.add("Top tracks");
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

    //updates the drawer from the user adt
    public void updateDrawerFromUser(UserHelper u){
        if (u.isUserSet()){
            // User Information
            this.userName.setText(u.username());
            this.userEmail.setText(u.realname());
            this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);

            this.userBackground.setImageResource(R.drawable.ic_user_background_first);
        }else {
            this.userName.setText("Null");
            this.userEmail.setText("Null");
        }

        Log.w("INFO" , "user " + (user.isUserSet() == true? "true": "false"));
    }

    //handles the changing of the fragments
    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        Log.w("INFO", "Position= " + position);

        int elev = 0; //the elevation of the toolbar

        if (!user.isUserSet()){
            mFragment = new LoginFragment();
            elev = 15;

        } else {
            switch (position){
                case 0:
                    mFragment = new HomeFragment();
                    break;
                case 1: // Top Artists
                    mFragment =  ViewPagerFragment.newInstance("artist");
                    break;
                case 2: // Top Albums
                    mFragment = ViewPagerFragment.newInstance("album");
                    break;
                case 3: // Top Tracks
                    mFragment = ViewPagerFragment.newInstance("track");
                    break;
                default:
                    //should never go here
                    mFragment = ViewPagerFragment.newInstance(mHelpLiveo.get(position).getName());
                    break;
            }
        }

        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }

        setElevationToolBar(elev);

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

    //receives the user information from fragment
    public void recieveUserInfo(String username, String realname){
        user.updateInfo(username,realname);

        if (user.isUserSet()){
            this.userName.setText(username);
            this.userEmail.setText(realname);
        }

    }
}
