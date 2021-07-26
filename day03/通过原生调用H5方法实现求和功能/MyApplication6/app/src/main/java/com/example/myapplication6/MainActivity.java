package com.example.myapplication6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/求和.html");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.editTextNumber4);
                EditText editText2 = findViewById(R.id.editTextNumber5);
                float a  = Float.parseFloat(editText1.getText().toString());
                float b = Float.parseFloat(editText2.getText().toString());

                webView.evaluateJavascript("add(" + a + "," + b + ")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        TextView textView = findViewById(R.id.textView10);
                        textView.setText(value);
                    }
                });
            }
        });

    }

}