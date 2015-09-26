package me.mcmadbat.laststats.Helpers;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by David on 2015-09-20.
 * This class helps with the management of the user information
 */
public final class UserHelper {

    public static boolean _isUserSet = false;

    private static String _username;
    private static String _realname;

    File _f;

    //the constructor
    public UserHelper(File f) {
        _f = f;
        _isUserSet = getFromMemory(); //does an initial get from memory
    }

    //gets whether there is a user
    public boolean isUserSet() {
        return _isUserSet;
    }

    public String username(){
       return _isUserSet == false? "" : _username;
    }

    public String realname(){
        return _isUserSet == false? "" : _realname;
    }

    //saves the information to memory
    public Boolean saveToMemory(){

        if (!_isUserSet && _username == ""){
            return false;
        }

        try {
            FileOutputStream stream = new FileOutputStream(_f);
            String output = "";

            output += _username + "~~"; // ~~ is the wildcard
            output += _realname;

            stream.write(output.getBytes());
            stream.close();
            return true;
        } catch (Exception e){

        }

        return false;
    }

    //gets the information from memory
    //returns true if its found
    public boolean getFromMemory(){
        if (_f.exists()){
            String data;
            try {
                int length = (int) _f.length();

                byte[] bytes = new byte[length];

                FileInputStream in = new FileInputStream(_f);
                try {
                    in.read(bytes);
                } finally {
                    in.close();
                }

                data = new String(bytes);
                String[] info = data.split("~~");

                if (info[0].equalsIgnoreCase("null") || info[0] == ""){
                    return false;
                }

                _username = info[0];
                Log.w("INFO", "Read from memory username = " + _username);
                _realname = info[1];

                return true;

            } catch (Exception e){
                return false;
            }
        }

        return false;
    }

    public Boolean updateInfo(String username, String realname){
        if (username == ""){
            return false;
        }
        _isUserSet = true;

        _username = username == "" ? _username: username;
        _realname = realname == "" ? _realname: realname;

        return this.saveToMemory();
    }
}
