package com.lelivrescolaire.testtechnique.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.interfaces.WebViewTaskListener;
import com.lelivrescolaire.testtechnique.tasks.WebViewTask;

import java.io.UnsupportedEncodingException;

/**
 * Created by root on 3/31/16.
 * Activity displaying the content of the page inside a webwiew
 */
public class PageActivity extends AppCompatActivity implements WebViewTaskListener {

    public final static String ARG_PAGE_ID = "ARG_PAGE_ID";
    private final static String TAG = PageActivity.class.getCanonicalName();
    private final static String JSC = "displayLineNumbers()";
    private final static String URL_OVERRIDE = "native://";

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview = (WebView) findViewById(R.id.page_wv_content);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long idPage = extras.getLong(ARG_PAGE_ID);
            WebViewTask webViewTask = new WebViewTask(this);
            webViewTask.execute(idPage);
        }
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(URL_OVERRIDE)) {
                    String value = url.replace(URL_OVERRIDE, "");
                    try {
                        value = java.net.URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:" + JSC);
            }
        });
    }

    @Override
    public void onLoadFinished(String result) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", result, "text/html", "UTF-8", "");
    }
}
