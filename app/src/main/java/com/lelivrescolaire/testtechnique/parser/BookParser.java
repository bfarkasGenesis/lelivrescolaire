package com.lelivrescolaire.testtechnique.parser;

import com.lelivrescolaire.testtechnique.bean.Book;
import com.lelivrescolaire.testtechnique.bean.Page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 3/31/16.
 * Parser for a Book
 */
public class BookParser {

    private static final String TITLE = "title";
    private static final String PATH = "cover";
    private static final String PAGES = "pages";

    public static void parse(Book book, JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            book.setTitle(jsonObject.getString(TITLE));
            book.setPathImage(jsonObject.getString(PATH));
            JSONArray jsonArrayPages = jsonObject.getJSONArray(PAGES);
            int count = jsonArrayPages.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObjectPage = jsonArrayPages.getJSONObject(i);
                Page page = new Page(jsonObjectPage);
                book.addPage(page);
            }

        }
    }
}