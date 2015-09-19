package com.svelte.expensemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ExpensesDetails extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHandler db;
    String datestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_details);

        db = DatabaseHandler.getInstance(this);

        datestr = getIntent().getStringExtra("date");

        TextView heading = (TextView) findViewById(R.id.dateheading);
        heading.setText(datestr);

        mRecyclerView = (RecyclerView) findViewById(R.id.expenselist);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Expense> explist = fetchExpenses();
        List<String> expstr = new ArrayList<String>();

        for(int i = 0; i<explist.size(); i++)
        {
            expstr.add(explist.get(i)._place + ": " + explist.get(i)._cost);
        }

        mAdapter = new ExpenseAdapter(expstr, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<Expense> fetchExpenses()
    {
        List<Expense> myexpenses = new ArrayList<Expense>();

        myexpenses = db.getDateExpenses(datestr);

        /*for(int i = 0; i<30; i++)
        {
            myexpenses.add(i, "Classics" + ": " + "Rs " + "50");
        }*/

        return myexpenses;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses_details, menu);
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
