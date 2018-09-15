package eci.rappi.rappihackathon.model;

import java.util.Date;

public class StoreKeeper {
    int storekeeper_id;
    Double lat;
    Double lng;
    Date timestamp;
    Toolkit toolkit;

    public StoreKeeper() {
        this.storekeeper_id = 0;
        this.lat = 0.0;
        this.lng = 0.0;
        this.timestamp = new Date();
        this.toolkit = new Toolkit();
    }

    public StoreKeeper(int storekeeper_id, Double lat, Double lng, Date timestamp, Toolkit toolkit) {
        this.storekeeper_id = storekeeper_id;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
        this.toolkit = toolkit;
    }

    public int getStorekeeper_id() {
        return storekeeper_id;
    }

    public void setStorekeeper_id(int storekeeper_id) {
        this.storekeeper_id = storekeeper_id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Toolkit getToolkit() {
        return toolkit;
    }

    public void setToolkit(Toolkit toolkit) {
        this.toolkit = toolkit;
    }
}
