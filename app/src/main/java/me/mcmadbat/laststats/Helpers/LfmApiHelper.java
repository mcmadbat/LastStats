package me.mcmadbat.laststats.Helpers;

import android.support.annotation.Nullable;

/**
 * Created by David on 2015-07-19.
 *
 * This class is used to get the URL for the Last FM Api Methods
 */

public final class LfmApiHelper {

    private static String KEY = "9d60dd5787a7a7bc7db25552db7e31ac"; //the api key
    private static String URL_ROOT = "http://ws.audioscrobbler.com/2.0/"; //the url root for all the calls

    //region API Methods

    //this method returns the url for the api method get top artists
    public static String getTopArtists (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
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

        return url;
    }

    //this method returns the url for the api method get top tracks
    public static String getTopTracks (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
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

        return url;
    }

    //this method returns the url for the api method get top tracks
    public static String getTopAlbums (String user, @Nullable String period, @Nullable String limit, @Nullable String page){
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

        return url;
    }
    //endregion
}
