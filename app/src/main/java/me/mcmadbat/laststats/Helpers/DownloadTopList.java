package me.mcmadbat.laststats.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.mcmadbat.laststats.MainActivity;

/**
 * Created by David on 2015-09-26.
 */
//async downloads all the top lists
//params[0] = artist || album || track
//params[1] = span
public class DownloadTopList extends AsyncTask<String, Void, List<CardInfo>> {
    ProgressDialog progressDialog;
    Context _context;

    private List<MainActivity.DownloadListListener> myListeners = new ArrayList<MainActivity.DownloadListListener>();

    String _type, _span;

    public void addListener(MainActivity.DownloadListListener tl){
        myListeners.add(tl);
    }

    public DownloadTopList (Context t,  String type, String span) {
        _context = t;
        _type = type;
        _span = span;
    }

    protected List<CardInfo> doInBackground(String... userName) {
        List<CardInfo> toReturn = new ArrayList<CardInfo>();
        try {

            List<String> data;

            Log.wtf("INFO", _type + " " + _span);

            if (_type == "artist") {
                data = LfmApiHelper.getTopArtists(userName[0],_span,"20", null);
            } else if (_type == "album") {
                data = LfmApiHelper.getTopAlbums(userName[0],_span,"20", null);
            } else if (_type == "track") {
                data = LfmApiHelper.getTopTracks(userName[0], _span, "20", null);
            } else {
                throw null; // hopefully never goes here
            }

            for (String x: data){
                String[] info = x.split("~~"); // split the string by the wildcard

                CardInfo temp = new CardInfo();
                temp.title = info[0];
                temp.count = info[1];
                temp.albumCoverUrl = info[3];

                // sets img directly from url
                HttpHelper helper = new HttpHelper();
                InputStream iStream = helper.HttpGetStream(temp.albumCoverUrl);
                Bitmap bMap = BitmapFactory.decodeStream(iStream);
                temp.bMap = bMap;

               // int rank = Integer.getInteger(info[2]); //have to order it lol
                toReturn.add(temp);
            }

            Log.w("INFO", "finished downloading info for " + _type + " " + _span);


            return toReturn;
        }
        catch (Exception e){
            Log.wtf("INFO", "Downloading of Top lists failed!: " + e.getMessage());
            Log.wtf("INFO", e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<CardInfo> result) {
        progressDialog.dismiss();

        for(MainActivity.DownloadListListener tl : myListeners){
            tl.onResult(result);
        }

        return;
    }

    @Override
    protected void onPreExecute() {
        //shows the loading
        progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Contacting Last.FM ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();

        return;
    }


}