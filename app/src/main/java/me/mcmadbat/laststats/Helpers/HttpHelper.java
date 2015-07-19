package me.mcmadbat.laststats.Helpers;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
