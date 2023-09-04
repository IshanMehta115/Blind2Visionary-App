package com.blind2visionary.andapp.article;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blind2visionary.andapp.R;

import java.net.URLEncoder;

public class ViewPdf extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        webView = findViewById(R.id.webViewPdf);
        webView.getSettings().setJavaScriptEnabled(true);
        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(filename);
        progressDialog.setMessage("Loading...");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        String url = "";
        try {
            url = URLEncoder.encode(fileurl,"UTF-8");
        }
        catch (Exception ex) {
        }

        webView.loadUrl("https://docs.google.com/viewer?url="+url);
    }
}