package me.mcmadbat.laststats.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.mcmadbat.laststats.MainActivity;

/**
 * Created by David on 2015-07-18.
 *
 * Wraps the basic HTTP calls
 */

public final class HttpHelper {

    public HttpHelper(){
        //needed because its main thread for this activity
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //used for handling http calls to the odata with the authorization
    public String HttpGet(String url){
        try {
            URL end = new URL(url);
            HttpURLConnection request = (HttpURLConnection) end.openConnection();

            //the parameters for the POST call
            Log.v("INFO", "HTTP call to: " + url);

            request.setChunkedStreamingMode(0);

            //gets the response
            InputStream in = request.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = reader.readLine()) != null){
                response.append(line);
                response.append('\r');
            }

            reader.close();

            Log.v("INFO", "HTTP call succeeded");
            Log.v("INFO", "Raw response: " + response.toString());
            return response.toString();

        }catch (Exception e){
            Log.e("INFO", "Exception doing HTTP GET: " + e.getMessage());
            return "Exception: " + e.getMessage();
        }
    }

    public InputStream HttpGetStream (String url){
        try {
            URL end = new URL(url);
            HttpURLConnection request = (HttpURLConnection) end.openConnection();

            //the parameters for the POST call
            Log.v("INFO", "HTTP call to: " + url);

            request.setChunkedStreamingMode(0);

            //gets the response
            InputStream in = request.getInputStream();

            return in;

        }catch (Exception e){
            Log.e("INFO", "Exception doing HTTP GET: " + e.getMessage());
            return null;
        }
    }

    public File DownloadImage(String url, Context t, MainActivity s){
        DownloadImages d = new DownloadImages(t,s);
        d.execute(url);
        return null;
    }
}

//async downloads the profile and banner images
class DownloadImages extends AsyncTask<String, Void, Boolean> {

    ProgressDialog progressDialog;
    Context _context;
    MainActivity _caller;
    File file;

    public DownloadImages (Context t, MainActivity c) {
        _context = t;
        _caller = c;
    }

    protected Boolean doInBackground(String... url) {
        try {
            File directory = new File(_context.getFilesDir() + "/Images/");

            if (!directory.exists()){
                directory.mkdirs();
            }

            //the profile picture
            URL _url = new URL(url[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) _url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            String filename= "profile.jpeg";

            file = new File(directory,filename);

            if (file.exists()){
                file.delete();
            }

            file.createNewFile();

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();

            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            //downloading here
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }

            fileOutput.close();
            Log.w("INFO", "finished downloading profile picture at " + file.getPath());

            return true;
        }
        catch (Exception e){
            Log.wtf("INFO", "Save images failed");
            Log.wtf("INFO", e.getMessage());
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean b) {
        progressDialog.dismiss();
        if (b == true) {
            _caller.recieveUserPicture(file);
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

