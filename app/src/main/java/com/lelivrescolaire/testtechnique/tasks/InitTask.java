package com.lelivrescolaire.testtechnique.tasks;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.lelivrescolaire.testtechnique.bean.Book;
import com.lelivrescolaire.testtechnique.dao.BookDao;
import com.lelivrescolaire.testtechnique.dao.PageDao;
import com.lelivrescolaire.testtechnique.interfaces.InitTaskListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 3/31/16.
 * Async task for retrieving and parsing data from JSON in asset folder
 * And store the result into database
 */
public class InitTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = InitTask.class.getCanonicalName();

    private Context mContext;
    private InitTaskListener mListener;

    public InitTask(Context context) {
        if (context instanceof InitTaskListener) {
            mContext = context;
            mListener = (InitTaskListener) context;
        } else {
            throw new IllegalStateException("must implement InitTaskListener");
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
    protected Void doInBackground(Void... params) {
        try {
            BookDao bookDao = new BookDao(mContext);
            PageDao pageDao = new PageDao(mContext);
            bookDao.deleteAll();
            pageDao.deleteAll();

            String jsonBooks = AssetFile("books.json", mContext);
            JSONArray jsonArrayBooks = new JSONArray(jsonBooks);
            int count = jsonArrayBooks.length();
            //For each item in the json file
            for (int i = 0; i < count; i++) {
                JSONObject jsonObjectBook = jsonArrayBooks.getJSONObject(i);
                Book book = new Book(jsonObjectBook);
                bookDao.insertOrUpdate(book);
                pageDao.insertOrUpdateFromBook(book);
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onInitFinished();
        mContext = null;
        mListener = null;
    }
}
