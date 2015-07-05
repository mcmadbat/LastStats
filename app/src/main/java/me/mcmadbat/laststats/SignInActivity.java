package me.mcmadbat.laststats;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import me.mcmadbat.laststats.*;
import me.mcmadbat.laststats.Helpers.Constants;

public class SignInActivity extends AppCompatActivity {
    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //start
        WebView wv = (WebView) findViewById(R.id.webView);
        CookieManager ckManager = CookieManager.getInstance();
        ckManager.setAcceptCookie(true); //needed for login

        wv.clearFormData();
        wv.clearHistory();
        wv.clearCache(true);

        //different methods of clearing cookies depending on sdk
        if (Build.VERSION.SDK_INT >=21){
            ckManager.removeAllCookies(null);
            ckManager.flush();
        } else {
            ckManager.removeAllCookie();
        }

        wv.setWebViewClient(new WebViewClient() {
                                     public void onPageFinished(WebView view, String url) {
                                         handlePageFinished(view, url);
                                     }
                                 }
        );
        wv.setVisibility(View.VISIBLE);

        WebSettings wSettings = wv.getSettings();
        wSettings.setJavaScriptEnabled(true);

        //loads the authentication endpoint
        wv.loadUrl("http://www.last.fm/api/auth/?api_key=" + Constants.KEY);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("INFO", "Sign In Activity stopped");
    }

    @Override
    protected void onResume() {
        Log.i("INFO", "Sign In Activity resumed.");
        super.onResume();

        Toast t = Toast.makeText(getApplicationContext(),"Please Sign In", Toast.LENGTH_SHORT);
        t.show();
    }
    //endregion

    private void handlePageFinished(View v , String url){
        if (url.contains(("hooey"))){
            int index = url.indexOf("token=") + 6;
            Toast t = Toast.makeText(getApplicationContext(),url.substring(index),Toast.LENGTH_SHORT);
        }

        v.setVisibility(View.VISIBLE);
    }
}
