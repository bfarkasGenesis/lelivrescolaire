package com.lelivrescolaire.testtechnique.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lelivrescolaire.testtechnique.bean.Book;
import com.lelivrescolaire.testtechnique.bean.Page;

import java.util.ArrayList;

/**
 * Created by root on 3/31/16.
 * DAO for the pages in database
 */
public class PageDao {

    public static final String ID = "id";
    public static final String ID_BOOK = "idBook";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String PHOTO_PATH = "photo_path";

    public static final int COL_ID = 0;
    public static final int COL_ID_BOOK = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_CONTENT = 3;
    public static final int COL_PHOTO_PATH = 4;

    public static final String TABLE = "page";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ID_BOOK + " INTEGER," +
            TITLE + " TEXT," +
            CONTENT + " TEXT," +
            PHOTO_PATH + " TEXT);";

    String[] allColumns = new String[]{ID, ID_BOOK, TITLE, CONTENT, PHOTO_PATH};

    private SQLiteDatabase db;
    private CustomSQLHelper dbHelper;
    private Context mContext;

    public PageDao(Context context) {
        this.mContext = context;
        dbHelper = CustomSQLHelper.getHelper(this.mContext);
        DatabaseManager.initializeInstance(dbHelper);
    }

    /**
     * Open the database
     */
    public void open() {
        if (dbHelper == null) {
            dbHelper = CustomSQLHelper.getHelper(mContext);
            DatabaseManager.initializeInstance(dbHelper);
        }
        db = DatabaseManager.getInstance().openDatabase();
    }

    /**
     * Close the database
     */
    private void close() {
        DatabaseManager.getInstance().closeDatabase();
    }

    /**
     * Insert pages from a book into the db
     * or update if id > 0
     *
     * @param book - a book
     */
    public void insertOrUpdateFromBook(Book book) {
        open();
        ArrayList<Page> list = book.getPages();
        for (Page page : list) {
            page.setIdBook(book.getId());
            if (page.getId() > 0) {
                db.update(TABLE, getContentValues(page), ID + " = ?", new String[]{String.valueOf(page.getId())});
            } else {
                long insertID = db.insert(TABLE, null, getContentValues(page));
                page.setId(insertID);
            }
        }
        close();
    }

    /**
     * Delete a page from db
     *
     * @param id unique identifier for a page
     */
    public void delete(long id) {
        open();
        db.delete(TABLE, ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    /**
     * Delete all pages from db
     */
    public void deleteAll() {
        open();
        db.delete(TABLE, null, null);
        close();
    }

    /**
     * Retrieve the whole list of pages stored in db for a book
     *
     * @return ArrayList ArrayList<Page>
     */
    public ArrayList<Page> getAllByBook(long idBook) {
        ArrayList<Page> list = new ArrayList<>();
        open();
        Cursor cursor = db.query(TABLE, allColumns, ID_BOOK + " = ?", new String[]{String.valueOf(idBook)}, null, null, ID);
        while (cursor != null && cursor.moveToNext()) {
            list.add(cursorToPage(cursor));
        }
        if (cursor != null)
            cursor.close();

        close();
        return list;
    }

    /**
     * Retrieve a page by it's id
     *
     * @param idPage identifier of the page
     * @return corresponding Page
     */
    public Page getById(long idPage) {
        Page page = null;
        open();
        Cursor cursor = db.query(TABLE, allColumns, ID + " = ? ", new String[]{String.valueOf(idPage)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            page = cursorToPage(cursor);
            cursor.close();
        }
        close();
        return page;
    }

    /**
     * Set ContentValues for a page
     *
     * @param page Page to parse
     * @return a set of values containing infos of the page
     * @see ContentValues
     */
    private ContentValues getContentValues(Page page) {
        ContentValues contentValues = new ContentValues();
        long id = page.getId();
        if (id > 0)
            contentValues.put(ID, id);
        contentValues.put(ID_BOOK, page.getIdBook());
        contentValues.put(TITLE, page.getTitle());
        contentValues.put(CONTENT, page.getContent());
        contentValues.put(PHOTO_PATH, page.getPathImage());

        return contentValues;
    }

    /**
     * Convert a cursor into an object Page
     *
     * @param cursor object to parse
     * @return Page
     */

    private Page cursorToPage(Cursor cursor) {
        Page page = new Page();
        page.setId(cursor.getLong(COL_ID));
        page.setIdBook(cursor.getLong(COL_ID_BOOK));
        page.setTitle(cursor.getString(COL_TITLE));
        page.setContent(cursor.getString(COL_CONTENT));
        page.setPathImage(cursor.getString(COL_PHOTO_PATH));

        return page;
    }
}