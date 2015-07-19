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
    }
    //endregion

}
