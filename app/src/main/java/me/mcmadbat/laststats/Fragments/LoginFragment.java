package me.mcmadbat.laststats.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import me.mcmadbat.laststats.Helpers.LfmApiHelper;
import me.mcmadbat.laststats.MainActivity;
import me.mcmadbat.laststats.R;

/**
 * Created by David on 2015-09-20.
 * This is where the user puts in the user name
 */
public class LoginFragment extends Fragment {

    private static final String TEXT_FRAGMENT = "";

    private Button btnEnter;
    private EditText userIn;

    //constructor
    public static LoginFragment newInstance(String text){
        LoginFragment mFragment = new LoginFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        btnEnter = (Button) rootView.findViewById(R.id.btnEnter);
        userIn = (EditText) rootView.findViewById(R.id.userNameIn);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = userIn.getText().toString();

                List<String> info = LfmApiHelper.getUserInfo(u);

                if (info.size() != 0){
                    //user was found
                    ((MainActivity)getActivity()).recieveUserInfo(info.get(0),info.get(1));

                    Fragment mFragment;
                    FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();

                    mFragment = TopListFragment.newInstance("Artist");

                    mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();

                } else {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(),"User not found. Please try again.", Toast.LENGTH_LONG);
                    t.show();

                    userIn.setText("");
                }
            }
        });

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return true;
    }

}
