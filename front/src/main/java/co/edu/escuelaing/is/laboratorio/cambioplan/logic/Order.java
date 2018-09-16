package co.edu.escuelaing.is.laboratorio.cambioplan.logic;

import org.bson.types.ObjectId;

public class Order {

    private ObjectId _id;
    private Double id;
    private Double lat;
    private Double lng;
    private String timestamp;
    private String created_at;
    private String type;
    private Toolkit toolkit;


    public Order() {
        this._id = new ObjectId();
        this.id = 0.0;
        this.lat = 0.0;
        this.lng = 0.0;
        this.timestamp = "";
        this.created_at = "";
        this.type = "";
        this.toolkit = new Toolkit();
    }

    public Order(ObjectId _id, Double id, Double lat, Double lng, String timestamp, String created_at, String type, Toolkit toolkit) {
        this._id = _id;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
        this.created_at = created_at;
        this.type = type;
        this.toolkit = toolkit;
    }

    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", timestamp=" + timestamp +
                ", created_at=" + created_at +
                ", type='" + type + '\'' +
                ", toolkit=" + toolkit +
                '}';
    }
}
