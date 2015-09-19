package com.svelte.expensemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class UpdateActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = DatabaseHandler.getInstance(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Expense> dates = new ArrayList<Expense>();
        dates = fetchDates();
        List<String> datestr = new ArrayList<String>();
        for(int i = 0; i<dates.size(); i++)
        {
            datestr.add(dates.get(i)._date);
        }
        mAdapter = new MyAdapter(datestr, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<Expense> fetchDates()
    {
        List<Expense> mydates = new ArrayList<Expense>();

        mydates = db.getDateList();

        /*for(int i = 0; i<30; i++)
        {
            mydates.add(i, "September " + (i+1) + ", 2015");
        }*/

        return mydates;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
