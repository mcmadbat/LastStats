package me.mcmadbat.laststats.Helpers;

import android.content.SharedPreferences;

/*this class is used for Constants and stuff*/
public final class Constants {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor e;

    /*Keys and stuff*/
    public static String KEY = "9d60dd5787a7a7bc7db25552db7e31ac";
    public static String SECRET = "3619fdf6507a96471c380920a508f797";

    /*Urls and stuff*/
    public static String URL_ROOT = "http://ws.audioscrobbler.com/2.0/";

    /*Current User Information*/
    public static String user_name = "mcmadbat3";
    public static Boolean user_exist = true;
}
