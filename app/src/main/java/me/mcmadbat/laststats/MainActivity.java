package me.mcmadbat.laststats;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import me.mcmadbat.laststats.Fragments.LoginFragment;
import me.mcmadbat.laststats.Fragments.ViewPagerFragment;
import me.mcmadbat.laststats.Helpers.CardInfo;
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
        if (id == R.id.Change_User) {
            user.delete();
            Toast t = Toast.makeText(getApplicationContext(),"Please enter user again", Toast.LENGTH_SHORT);
            t.show();

            Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,  mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    endregion

    //region Navigation Drawer Methods
    @Override
    public void onInt(Bundle savedInstanceState) {

        File t = new File(getApplicationContext().getFilesDir(),"UserInfo.txt");
        String version = "null";
        try {
            version = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (Exception e){

        }

        user = new UserHelper(t);

        updateDrawerFromUser(user);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        //mHelpLiveo.add("Home");
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("Top Artists");
        mHelpLiveo.add("Top Albums");
        mHelpLiveo.add("Top tracks");
//        mHelpLiveo.addSeparator(); // Item separator

        with(this).startingPosition(1) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                .colorItemSelected(R.color.nliveo_red_colorPrimary)
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)

                .footerItem("Version " + version, R.mipmap.app_icon)

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
            updateUserPicture();

            this.userBackground.setImageResource(R.drawable.ic_user_background_first);
        }else {
            this.userName.setText("Null");
            this.userEmail.setText("Null");
        }

        Log.w("INFO" , "Updated drawer for user " + user.username());
    }

    //handles the changing of the fragments
    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        Log.w("INFO", "Position= " + position);

        int elev = 0; //the elevation of the toolbar

        if (!user.isUserSet()){
            mFragment = LoginFragment.newInstance("",this);
            elev = 15;

        } else {
            switch (position){
//                case 0:
//                    mFragment = new HomeFragment();
//                    break;
                case 1: // Top Artists
                    mFragment =  ViewPagerFragment.newInstance("artist",user.username());
                    setTitle("Top Artists");
                    break;
                case 2: // Top Albums
                    mFragment = ViewPagerFragment.newInstance("album",user.username());
                    setTitle("Top Albums");
                    break;
                case 3: // Top Tracks
                    mFragment = ViewPagerFragment.newInstance("track",user.username());
                    setTitle("Top Tracks");
                    break;
                default:
                    //should never go here
                    mFragment = null;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(getApplicationContext(), "yes thats you!", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //closeDrawer();
            String txt = "Graph graphic by reepik from Flaticon is licensed under CC BY 3.0 Made with " +
                    "Logo Maker";
            Toast t = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
            t.show();
        }
    };

    //endregion

    //receives the user information from fragment
    public void recieveUserInfo(String username, String realname, File profilePic){
        user.updateInfo(username,realname, profilePic);

        if (user.isUserSet()){
            this.userName.setText(username);
            this.userEmail.setText(realname);

            try {
                if (profilePic.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(profilePic.getAbsolutePath());

                    this.userPhoto.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                return;
            }
        }

    }

    public void recieveUserPicture(File profilePic){
        try {
            if (profilePic.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(profilePic.getAbsolutePath());

                this.userPhoto.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            return;
        }
    }

    public void updateUserPicture(){
        try {
            File directory = new File(getApplicationContext().getFilesDir() + "/Images/");
            File file = new File(directory,"profile.jpeg");

            if (file.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                this.userPhoto.setImageBitmap(myBitmap);
            } else {
                throw null;
            }
        }
        catch (Exception e){
            Log.wtf("INFO", "Updating user picture failed");
        }
    }

    //the interface to communicate with the toplistfragments
    public interface DownloadListListener{
        public void onResult(List<CardInfo> result);
    }
}
