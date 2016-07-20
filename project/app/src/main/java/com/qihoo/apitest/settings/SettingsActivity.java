package com.qihoo.apitest.settings;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qihoo.apitest.R;
import com.qihoo.apitest.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends Activity implements View.OnClickListener {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActivityUtils.installAllButtonListener(this, this);

        mWebView = (WebView)findViewById(R.id.targetView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.air_mode_on:
                turnOnAirMode();
                break;
            case R.id.air_mode_off:
                turnOffAirMode();
                break;
            case R.id.do_work:
                doWork();
                break;
        }
    }

    private static final String BASE_URL = "http://zhounian.lantingroup.cn/index.php/Home/Share/home/id/380.html";
    private static final String VOTE_URL = "http://zhounian.lantingroup.cn/index.php/Home/Share/addSentiment.html?idm=380";
    public static final String HEADER_WAP_PROFILE = "x-wap-profile";
    private boolean mVoted = false;
    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mVoted) return;
                    mVoted = true;
                    Map<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", BASE_URL);
                    extraHeaders.put(HEADER_WAP_PROFILE, "");
                    mWebView.loadUrl(VOTE_URL, extraHeaders);
                }
            }, 10000);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    };

    WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
    };
    private void doWork() {
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl(BASE_URL);
    }

    private void turnOffAirMode() {
        ContentResolver cr = getContentResolver();
        if (Settings.System.getString(cr, Settings.Global.AIRPLANE_MODE_ON).equals("1")) {
            Settings.System.putString(cr, Settings.Global.AIRPLANE_MODE_ON, "0");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            sendBroadcast(intent);
        }
    }

    private void turnOnAirMode() {
        ContentResolver cr = getContentResolver();
        if (Settings.System.getString(cr, Settings.Global.AIRPLANE_MODE_ON).equals("0")) {
            Settings.System.putString(cr, Settings.Global.AIRPLANE_MODE_ON, "1");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            sendBroadcast(intent);
        }
    }
}
