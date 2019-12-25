package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Toolbar toolbar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toast.makeText(this,getIntent().getStringExtra("url"),Toast.LENGTH_SHORT).show();
        progressBar =findViewById(R.id.progressBar);
       // toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.detailView);

        setSupportActionBar(toolbar);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         Toast.makeText(DetailActivity.this,"Page Started",Toast.LENGTH_SHORT).show();

                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         progressBar.setVisibility(View.GONE);
                                         webView.setVisibility(View.VISIBLE);
                                         Toast.makeText(DetailActivity.this,"Page Loaded",Toast.LENGTH_SHORT).show();


                                     }
                                 }


        );
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
