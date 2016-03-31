package com.lelivrescolaire.testtechnique.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lelivrescolaire.testtechnique.bean.Book;

import java.util.ArrayList;

/**
 * Created by root on 3/31/16.
 * DAO for the books in db
 */
public class BookDao {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String PHOTO_PATH = "photo_path";

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_PHOTO_PATH = 2;

    public static final String TABLE = "book";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TITLE + " TEXT," +
            PHOTO_PATH + " TEXT);";

    String[] allColumns = new String[]{ID, TITLE, PHOTO_PATH};

    private SQLiteDatabase db;
    private CustomSQLHelper dbHelper;
    private Context mContext;

    public BookDao(Context context) {
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
     * Insert a book into the db
     * or update if id > 0
     *
     * @param book - a book
     */
    public void insertOrUpdate(Book book) {
        open();
        if (book.getId() > 0) {
            db.update(TABLE, getContentValues(book), ID + " = ?", new String[]{String.valueOf(book.getId())});
        } else {
            long insertID = db.insert(TABLE, null, getContentValues(book));
            book.setId(insertID);
        }
        close();
    }

    /**
     * Delete a book from db
     *
     * @param id unique identifier for a book
     */
    public void delete(long id) {
        open();
        db.delete(TABLE, ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    /**
     * Delete all books from db
     */
    public void deleteAll() {
        open();
        db.delete(TABLE, null, null);
        close();
    }

    /**
     * Retrieve the whole list of books stored in db
     *
     * @return ArrayList ArrayList<Book>
     */
    public ArrayList<Book> getAll() {
        ArrayList<Book> list = new ArrayList<>();
        open();
        Cursor cursor = db.query(TABLE, allColumns, null, null, null, null, ID);
        while (cursor != null && cursor.moveToNext()) {
            list.add(cursorToBook(cursor));
        }
        if (cursor != null)
            cursor.close();

        close();
        return list;
    }

    /**
     * Set ContentValues for a book
     *
     * @param book item to parse
     * @return a set of values containing infos of the book
     * @see ContentValues
     */
    private ContentValues getContentValues(Book book) {
        ContentValues contentValues = new ContentValues();
        long id = book.getId();
        if (id > 0)
            contentValues.put(ID, id);
        contentValues.put(TITLE, book.getTitle());
        contentValues.put(PHOTO_PATH, book.getPathImage());

        return contentValues;
    }

    /**
     * Convert a cursor into an object Book
     *
     * @param cursor object to parse
     * @return Book
     */

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(COL_ID));
        book.setTitle(cursor.getString(COL_TITLE));
        book.setPathImage(cursor.getString(COL_PHOTO_PATH));

        return book;
    }


}