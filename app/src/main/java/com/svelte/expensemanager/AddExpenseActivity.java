package com.svelte.expensemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddExpenseActivity extends ActionBarActivity {

    private Spinner place;
    private EditText cost;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        place = (Spinner) findViewById(R.id.expenseplace);
        cost = (EditText) findViewById(R.id.expensecost);
        addButton = (Button) findViewById(R.id.expenseadd);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cost.getText().toString().matches("")) {

                    Toast.makeText(AddExpenseActivity.this,"Add a cost!",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    DatabaseHandler db;
                    db = DatabaseHandler.getInstance(AddExpenseActivity.this);
                    Expense expense = new Expense(String.valueOf(place.getSelectedItem()), Integer.parseInt(String.valueOf(cost.getText())));
                    db.addExpense(expense);
                    finish();
                }

                /*Toast.makeText(AddExpenseActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + String.valueOf(place.getSelectedItem()) +
                                "\nEdittext : " + String.valueOf(cost.getText()),
                        Toast.LENGTH_SHORT).show();*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);
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
