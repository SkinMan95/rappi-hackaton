/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.is.laboratorio.cambioplan.beans;

/**
 *
 * @author blackphantom
 */
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import co.edu.escuelaing.is.laboratorio.cambioplan.logic.Order;
import co.edu.escuelaing.is.laboratorio.cambioplan.logic.StoreKeeper;
import co.edu.escuelaing.is.laboratorio.cambioplan.logic.Utiles;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.map.*;

@ManagedBean(name = "Main")
@SessionScoped
public class mainBean implements Serializable {
    private MapModel circleModel;
    private int vehicle,interval,zoom;
    private String type,actualCoords;
    private List<StoreKeeper> storeKeepers;
    private List<Order> orders;

    public mainBean() {
        vehicle =0;
        interval=60;
        actualCoords = "4.6435568,-74.0625309";
        zoom=13;
    }

    @PostConstruct
    public void init() {
        circleModel = new DefaultMapModel();
        storeKeepers = Utiles.getStoresKeepers();
//        orders = Utiles.getOrders();
        fillModel();
    }

    private void fillModel(){
        for(StoreKeeper storeKeeper: storeKeepers)if(vehicle==0 || storeKeeper.getToolkit().getVehicle()==vehicle){
            LatLng latlng = new LatLng(storeKeeper.getLat(),storeKeeper.getLng());
            Circle circle = new Circle(latlng,500 * storeKeeper.getToolkit().getVehicle()); //500 <-> 6 cuadras
            circle.setFillColor("#ff7175");
            circle.setFillOpacity(0.05);
            circle.setStrokeOpacity(0);
            circleModel.addOverlay(circle);
        }
    }

    public void filterByVehicle(){
        circleModel = new DefaultMapModel();
        fillModel();
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            actualCoords = center.getLat() + "," + center.getLng();
        }
    }

    public void onReverseGeocode(ReverseGeocodeEvent event) {
        List<String> addresses = event.getAddresses();
        LatLng coord = event.getLatlng();

        if (addresses != null && !addresses.isEmpty()) {
            actualCoords = coord.getLat() + "," + coord.getLng();
            circleModel.addOverlay(new Marker(coord, addresses.get(0)));
            System.out.println(addresses);
        }
    }

    public MapModel getCircleModel() throws SQLException {
        if (circleModel==null)
            init();
        return circleModel;
    }

    public void onCircleSelect(OverlaySelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Circle Selected", null));
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getActualCoords() {
        return actualCoords;
    }

    public void setActualCoords(String actualCoords) {
        this.actualCoords = actualCoords;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
