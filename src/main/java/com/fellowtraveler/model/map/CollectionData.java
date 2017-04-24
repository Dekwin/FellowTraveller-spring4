package com.fellowtraveler.model.map;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Set;

/**
 * Created by igorkasyanenko on 17.04.17.
 */
@QueryResult
public class CollectionData {
    private Long id;
    private Set<Integer> subscribers;
    private Long datetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Integer> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Integer> subscribers) {
        this.subscribers = subscribers;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }


    public CollectionData(Long id, Set<Integer> subscribers, Long datetime) {
        this.id = id;
        this.subscribers = subscribers;
        this.datetime = datetime;
    }

    public CollectionData() {
    }


    @Override
    public String toString() {
        return String.format("{id:%s, datetime:%s, subscribers:%s}}",id, datetime, subscribers);
    }
}
