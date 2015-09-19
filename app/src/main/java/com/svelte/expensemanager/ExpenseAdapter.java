package com.svelte.expensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by S on 9/19/2015.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private List<String> mDataset;
    private Context cont;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mText;
        public IMyViewHolderClicks mListener;

        public ViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            mText = (TextView) v.findViewById(R.id.expense_lv);
            mText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof TextView){
                mListener.onTextClick((TextView) v);
            }
        }

        public static interface IMyViewHolderClicks {
            public void onTextClick(TextView callerButton);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseAdapter(List<String> myDataset, Context c) {
        mDataset = myDataset;
        cont = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_listview, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, new ExpenseAdapter.ViewHolder.IMyViewHolderClicks() {
            public void onTextClick(TextView callerText)
            {
                Log.d("----------------", callerText.getText().toString());
                final int colpos = callerText.getText().toString().indexOf(":");
                final String txt = callerText.getText().toString();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        cont);
                // set title
                alertDialogBuilder.setTitle("Warning");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete " + callerText.getText().toString() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                DatabaseHandler db = DatabaseHandler.getInstance(cont);
                                Expense tempexp = new Expense();
                                tempexp.set_place(txt.substring(0, colpos));
                                Log.d("Here's the place:", tempexp.get_place());
                                tempexp.set_cost(Integer.parseInt(txt.substring(colpos + 2)));
                                Log.d("Here's the cost:", String.valueOf(tempexp.get_cost()));
                                db.deleteExpense(tempexp);
                                ((Activity)cont).finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mText.setText(mDataset.get(position));

        /*holder.mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(cont, ExpensesDetails.class);
                i.putExtra("date", v.findViewById(R.id))
                cont.startActivity(i);
            }
        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
