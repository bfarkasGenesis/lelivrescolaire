package com.lelivrescolaire.testtechnique.tasks;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.lelivrescolaire.testtechnique.bean.Page;
import com.lelivrescolaire.testtechnique.dao.PageDao;
import com.lelivrescolaire.testtechnique.interfaces.WebViewTaskListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 3/31/16.
 * Async task for retrieving and parsing data from JSON in asset folder
 * And store the result into database
 */
public class WebViewTask extends AsyncTask<Long, Void, String> {

    private static final String TAG = WebViewTask.class.getCanonicalName();
    private final static String REPLACE = "TEXT_CONTENT";

    private Context mContext;
    private WebViewTaskListener mListener;

    public WebViewTask(Context context) {
        if (context instanceof WebViewTaskListener) {
            mContext = context;
            mListener = (WebViewTaskListener) context;
        } else {
            throw new IllegalStateException("must implement WebViewTaskListener");
        }
    }

    public static String AssetFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    @Override
    protected String doInBackground(Long... params) {

        String html = "";
        try {
            html = InitTask.AssetFile("page.html", mContext);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        long idPage = params[0];
        PageDao pageDao = new PageDao(mContext);
        Page page = pageDao.getById(idPage);
        if (page != null && html.length() > 0) {
            html = html.replace(REPLACE, page.getContent());
        }

        return html;
    }

    @Override
    protected void onPostExecute(String result) {
        mListener.onLoadFinished(result);
        mContext = null;
        mListener = null;
    }
}
