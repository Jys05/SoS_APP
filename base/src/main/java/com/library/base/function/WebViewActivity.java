package com.library.base.function;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.library.base.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author zhangdeming
 * @date 创建时间 2016/12/26
 * @description 打开web页面
 */
public class WebViewActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_URL = "key_url";
    private static final String KEY_CONTENT = "key_url";
    private static final String KEY_TYPE = "key_type";

    private static final byte TYPE_URL = 0x11;
    private static final byte TYPE_CONTENT = 0x12;

    private WebView mWebview;
    private Toolbar mToolbar;
    private WebSettings mSettings;

    private String mUrl, mContent, mTitle;
    private byte mType = 0x00;

    public static void browseByUrl(Context context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, url);
        bundle.putByte(KEY_TYPE, TYPE_URL);
        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    public static void browseByContent(Context context, String title, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_CONTENT, content);
        bundle.putByte(KEY_TYPE, TYPE_URL);
        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebview = (WebView) findViewById(R.id.web_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        init();
    }


    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mType = bundle.getByte(KEY_TYPE);
            mTitle = bundle.getString(KEY_TITLE);
            if (mType == TYPE_URL) {
                mUrl = bundle.getString(KEY_URL);
            } else if (mType == TYPE_CONTENT) {
                mContent = bundle.getString(KEY_CONTENT);
            }
            mToolbar.setTitle(mTitle);
            initWebView();
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void initWebView() {
        // 网页加载
        mSettings = mWebview.getSettings();
        mSettings.setDefaultTextEncodingName("utf-8");

        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mSettings.setAllowFileAccess(true);
        mSettings.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);

        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);

        mSettings.setSupportMultipleWindows(false);
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setGeolocationEnabled(true);
        mSettings.setAppCacheMaxSize(Long.MAX_VALUE);

        mSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        mSettings.setDatabasePath(this.getDir("databases", 0).getPath());
        mSettings.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());

        mSettings.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);


        mWebview.setWebViewClient(client);
        // 弹出对话框
        mWebview.setWebChromeClient(chromeClient);

        if (mType == TYPE_URL) {
            if (mUrl.startsWith("http://") || mUrl.startsWith("https://")) {
                mWebview.loadUrl(mUrl);
            } else {
                mWebview.loadUrl("http://" + mUrl);
            }
        } else if (mType == TYPE_CONTENT) {
            mWebview.loadData(mContent, "text/html", "utf-8");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private WebChromeClient chromeClient = new WebChromeClient() {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };

    private WebViewClient client = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    };

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            finish();
        }
    }

}
