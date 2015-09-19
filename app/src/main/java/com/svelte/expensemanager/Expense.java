package com.svelte.expensemanager;

/**
 * Created by S on 9/19/2015.
 */
public class Expense {

    int _id;
    String _date;
    String _place;
    int _cost;

    public Expense()
    {

    }

    public Expense(String _place, int _cost) {
        this._place = _place;
        this._cost = _cost;
    }

    public Expense(String _date, String _place, int _cost) {
        this._date = _date;
        this._place = _place;
        this._cost = _cost;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Expense(int _id, String _date, String _place, int _cost) {
        this._id = _id;

        this._date = _date;
        this._place = _place;
        this._cost = _cost;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_place() {
        return _place;
    }

    public void set_place(String _place) {
        this._place = _place;
    }

    public int get_cost() {
        return _cost;
    }

    public void set_cost(int _cost) {
        this._cost = _cost;
    }
}
