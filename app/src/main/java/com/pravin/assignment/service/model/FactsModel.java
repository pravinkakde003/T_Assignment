package com.pravin.assignment.service.model;

import java.util.ArrayList;

/*
Model class for Facts entities
 */
public class FactsModel {

    public String title;
    private ArrayList<FeedModel> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FeedModel> getRows() {
        return rows;
    }

    public void setRows(ArrayList<FeedModel> rows) {
        this.rows = rows;
    }
}
