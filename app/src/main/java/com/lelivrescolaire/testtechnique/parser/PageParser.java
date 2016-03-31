package com.lelivrescolaire.testtechnique.parser;

import com.lelivrescolaire.testtechnique.bean.Page;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 3/31/16.
 * Parser of the item Page
 */
public class PageParser {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String PATH = "cover";

    public static void parse(Page page, JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            page.setTitle(jsonObject.getString(TITLE));
            page.setContent(jsonObject.getString(CONTENT));
            page.setPathImage(jsonObject.getString(PATH));
        }
    }
}