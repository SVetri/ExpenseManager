package com.svelte.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by S on 9/19/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;

    Context cont;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "expenseManager";

    // Contacts table name
    private static final String TABLE_EXPENSES = "expenses";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_PLACE = "place";
    private static final String KEY_COST = "cost";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cont = context;
    }

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " INTEGER NOT NULL DEFAULT (strftime('%s','now')), " + KEY_PLACE + " TEXT, "
                + KEY_COST + " INTEGER" + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE, expense.get_place());
        values.put(KEY_COST, expense.get_cost()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }

    //Get all Dates
    public List<Expense> getDateList() {
        List<Expense> expenseList = new ArrayList<Expense>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + KEY_DATE  + " from " + TABLE_EXPENSES;

        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                    Expense expense = new Expense();
                    DateFormat formatter = android.text.format.DateFormat
                            .getDateFormat(cont);
                    Date dateObj = new Date(Long.parseLong(cursor.getString(0)) * 1000);
                    expense.set_date(formatter.format(dateObj));
                    expenseList.add(expense);

                    /*Expense expense = new Expense();
                    Date d  = new Date(Long.parseLong(cursor.getString(0)));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    expense.set_date(dateFormat.format(d));
                    // Adding contact to list
                    expenseList.add(expense);*/

            } while (cursor.moveToNext());
        }

        List<Expense> actualexpenseList = new ArrayList<Expense>();

        int flag = 0;
        for(int i = 0; i<expenseList.size(); i++)
        {
            for(int j = 0; j<actualexpenseList.size(); j++)
            {
                if(actualexpenseList.get(j)._date.equals(expenseList.get(i)._date))
                {
                    flag = 1;
                    break;
                }
            }

            if(flag ==0)
            {
                actualexpenseList.add(expenseList.get(i));
            }
            flag = 0;
            /*if(actualexpenseList.contains(expenseList.get(i)._date) == false)
            {
                actualexpenseList.add(expenseList.get(i));
            }*/
        }

        // return contact list
        return actualexpenseList;
    }

    // Getting All expenses on a given Date
    public List<Expense> getDateExpenses(String searchdate) {
        List<Expense> expenseList = new ArrayList<Expense>();

        //String sdate = new SimpleDateFormat("yyyyMMdd").format(searchdate);

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + KEY_DATE + ", " + KEY_PLACE + ", " + KEY_COST + " from " + TABLE_EXPENSES;

        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DateFormat formatter = android.text.format.DateFormat
                        .getDateFormat(cont);
                Date dateObj = new Date(Long.parseLong(cursor.getString(0)) * 1000);
                if(formatter.format(dateObj).equals(searchdate))
                {
                    Expense expense = new Expense();
                    expense.set_place(cursor.getString(1));
                    expense.set_cost(Integer.parseInt(cursor.getString(2)));
                    // Adding contact to list
                    expenseList.add(expense);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;
    }

    // Deleting single contact
    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_PLACE + " = ? AND " + KEY_COST + " = ?",
                new String[] { String.valueOf(expense.get_place()), String.valueOf(expense.get_cost()) });
        db.close();
    }

}
