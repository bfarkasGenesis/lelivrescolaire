package com.lelivrescolaire.testtechnique.bean;

import com.lelivrescolaire.testtechnique.parser.PageParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 3/31/16.
 * Bean for a page item
 */
public class Page {

    private long id;
    private long idBook;
    private String title;
    private String content;
    private String pathImage;

    public Page() {
    }

    public Page(JSONObject jObj) throws JSONException {
        PageParser.parse(this, jObj);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdBook() {
        return idBook;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

}