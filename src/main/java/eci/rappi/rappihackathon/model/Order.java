package eci.rappi.rappihackathon.model;

import java.util.Date;

public class Order {

    private Double id; // XXX
    private Double lat;
    private Double lng;
    private Date timestamp;
    private Date created_at;
    private String type;
    private Toolkit toolkit;


    public Order() {
        this.id = new Double(0);
        this.lat = new Double(0);
        this.lng = new Double(0);
        this.timestamp = new Date();
        this.created_at = new Date();
        this.type = new String();
        this.toolkit = new Toolkit();
    }

    public Order(Double id, Double lat, Double lng, Date timestamp, Date created_at, String type, Toolkit toolkit) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
        this.created_at = created_at;
        this.type = type;
        this.toolkit = toolkit;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Toolkit getToolkit() {
        return toolkit;
    }

    public void setToolkit(Toolkit toolkit) {
        this.toolkit = toolkit;
    }
}
