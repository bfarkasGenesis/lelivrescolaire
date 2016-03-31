package com.lelivrescolaire.testtechnique.bean;

import com.lelivrescolaire.testtechnique.parser.BookParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 3/31/16.
 * Bean for a book item
 */
public class Book {

    private long id;
    private String title;
    private String pathImage;

    private ArrayList<Page> listPages = new ArrayList<>();

    public Book() {
    }

    public Book(JSONObject jObj) throws JSONException {
        BookParser.parse(this, jObj);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public ArrayList<Page> getPages() {
        return listPages;
    }

    public void addPage(Page page) {
        listPages.add(page);
    }

}