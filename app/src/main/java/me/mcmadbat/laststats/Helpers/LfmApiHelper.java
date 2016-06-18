package me.mcmadbat.laststats.Helpers;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2015-07-19.
 *
 * This class is used to get the URL for the Last FM Api Methods
 */

public final class LfmApiHelper {

    private static String KEY = "9d60dd5787a7a7bc7db25552db7e31ac"; //the api key
    private static String URL_ROOT = "http://ws.audioscrobbler.com/2.0/"; //the url root for all the calls

    //region API Methods
    //this method returns the results for the api method get top artists
    public static List<String> getTopArtists (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
        String url = URL_ROOT + "?method=user.gettopartists&format=json&api_key=" + KEY + "&user=" + user;

        if (period != null && period != "") {
            url += "&period=" + period;
        }

        if (limit != null && limit != ""){
            url+= "&limit=" + limit;
        }

        if (page != null && page != ""){
            url+= "&page=" + page;
        }

        HttpHelper httpHelper = new HttpHelper();

        List<String> r = new ArrayList<String>();
        try {
            JSONObject response = new JSONObject(httpHelper.HttpGet(url));
            response = response.getJSONObject("topartists");

            JSONArray arr = response.getJSONArray("artist");

            for (int i =0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                String line ="";
                line += temp.getString("name") + "~~";
                //line += " ~~";
                line += temp.getString("playcount") + " Plays~~";

                String rank = temp.getJSONObject("@attr").getString("rank");
                line += rank + "~~";
                line += temp.getJSONArray("image").getJSONObject(2).getString("#text") + "~~"; // get the small img file

                r.add(line);
            }
        }
        catch (Exception e){
            Log.wtf("INFO", e.getMessage());
        }

        return r;
    }

    //this method returns the url for the api method get top tracks
    public static List<String> getTopTracks (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
        String url = URL_ROOT + "?method=user.gettoptracks&format=json&api_key=" + KEY + "&user=" + user;

        if (period != null && period != "") {
            url += "&period=" + period;
        }

        if (limit != null && limit != ""){
            url+= "&limit=" + limit;
        }

        if (page != null && page != ""){
            url+= "&page=" + page;
        }

        HttpHelper httpHelper = new HttpHelper();

        List<String> r = new ArrayList<String>();
        try {
            JSONObject response = new JSONObject(httpHelper.HttpGet(url));
            response = response.getJSONObject("toptracks");

            JSONArray arr = response.getJSONArray("track");

            for (int i =0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                String line ="";
                line += temp.getString("name") + "~~";
                //line += temp.getString("playcount") + "~~";
                line += "by " + temp.getJSONObject("artist").getString("name") + "~~";
                String rank = temp.getJSONObject("@attr").getString("rank");
                line += rank + "~~";
                line += temp.getJSONArray("image").getJSONObject(2).getString("#text") + "~~"; // get the small img file
                r.add(line);
            }
        }
        catch (Exception e){
            Log.wtf("INFO", e.getMessage());
        }

        return r;
    }

    //this method returns the url for the api method get top tracks
    public static List<String> getTopAlbums (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
        String url = URL_ROOT + "?method=user.gettopalbums&format=json&api_key=" + KEY + "&user=" + user;

        if (period != null && period != "") {
            url += "&period=" + period;
        }

        if (limit != null && limit != ""){
            url+= "&limit=" + limit;
        }

        if (page != null && page != ""){
            url+= "&page=" + page;
        }

        HttpHelper httpHelper = new HttpHelper();

        List<String> r = new ArrayList<String>();
        try {
            JSONObject response = new JSONObject(httpHelper.HttpGet(url));
            response = response.getJSONObject("topalbums");

            JSONArray arr = response.getJSONArray("album");

            for (int i =0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                String line ="";
                line += temp.getString("name") + "~~";

                //line += temp.getString("playcount") + "~~";
                line += "by " + temp.getJSONObject("artist").getString("name") + "~~";

                String rank = temp.getJSONObject("@attr").getString("rank");
                line += rank + "~~";
                line += temp.getJSONArray("image").getJSONObject(2).getString("#text") + "~~"; // get the small img file
                r.add(line);
            }
        }
        catch (Exception e){
            Log.wtf("INFO", e.getMessage());
        }

        return r;
    }

    //gets the user info of the user
    public static List<String> getUserInfo (String user) {
        String url = URL_ROOT + "?method=user.getinfo&format=json&api_key=" + KEY + "&user=" + user;
        HttpHelper httpHelper = new HttpHelper();

        List<String> r = new ArrayList<String>();
        try {
            JSONObject response = new JSONObject(httpHelper.HttpGet(url));
            response = response.getJSONObject("user");

            if (response != null){
                r.add(response.getString("name")); //the user name
                r.add(response.getString("realname")); //the real name

                JSONArray images = response.getJSONArray("image");
                JSONObject image = images.getJSONObject(1);
                r.add(image.getString("#text"));
            }
        }
        catch (Exception e){
            Log.wtf("INFO", e.getMessage());
        }

        return r;
    }

    //endregion

}
