package com.fellowtraveler.model.map;


import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
//@NodeEntity
public class RoutePoint {
    //@GraphId

    private Double latitude;
    private Double longitude;
    private CollectionData collectionData;



    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public CollectionData getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(CollectionData collectionData) {
        this.collectionData = collectionData;
    }

    public RoutePoint( Double latitude, Double longitude, CollectionData collectionData) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.collectionData = collectionData;
    }

    public RoutePoint() {
    }



//    @Override
//    public String toString() {
//        return String.format("{ id=%s, latitude='%s', longitude='%s'}", id,
//                latitude, longitude);
//    }

    @Override
    public String toString() {
        return String.format("{latitude:%s, longitude:%s, collectionData:%s}",
                latitude, longitude, collectionData);
    }
}