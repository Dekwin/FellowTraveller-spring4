package com.fellowtraveler.model.map;

/**
 * Created by igorkasyanenko on 16.04.17.
 */
public class SearchRoute {
    private SearchPoint start;
    private SearchPoint finish;

    public SearchPoint getStart() {
        return start;
    }

    public void setStart(SearchPoint start) {
        this.start = start;
    }

    public SearchPoint getFinish() {
        return finish;
    }

    public void setFinish(SearchPoint finish) {
        this.finish = finish;
    }

    public SearchRoute(SearchPoint start, SearchPoint finish) {
        this.start = start;
        this.finish = finish;
    }

    public SearchRoute() {
    }



}
