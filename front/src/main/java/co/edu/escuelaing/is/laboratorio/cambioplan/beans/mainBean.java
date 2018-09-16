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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.*;

@ManagedBean(name = "Main")
@SessionScoped
public class mainBean implements Serializable {
    private MapModel circleModel;
    private int interval,zoom;
    private String actualCoords;
    private List<StoreKeeper> storeKeepers;
    private List<Order> orders;
    private boolean load;
    private Order order;
    private Map<String,String> types;
    public mainBean() {
        interval=60;
        actualCoords = "4.6435568,-74.0625309";
        zoom=13;
        order = new Order();
        types = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        circleModel = new DefaultMapModel();
        storeKeepers = Utiles.getStoresKeepers();
        for (String type : Utiles.getTypes()){
            types.put(type,type);
        }
//        orders = Utiles.getOrders();
        fillModel();
        load=true;
    }

    public void onStateChange(StateChangeEvent event) {
        circleModel.getCircles().clear();
        LatLngBounds bounds = event.getBounds();
        zoom = event.getZoomLevel();
        actualCoords = event.getCenter().getLat() + "," + event.getCenter().getLng();
        orders = Utiles.getOrders(bounds.getSouthWest().getLat()
                ,bounds.getSouthWest().getLng(),bounds.getNorthEast().getLat(),bounds.getNorthEast().getLng());
        storeKeepers = Utiles.getStoresKeepers(bounds.getSouthWest().getLat()
        ,bounds.getSouthWest().getLng(),bounds.getNorthEast().getLat(),bounds.getNorthEast().getLng());
        fillModel();
    }

    private void fillModel(){
        for(StoreKeeper storeKeeper: storeKeepers)if(order.getToolkit().getVehicle()==0 || storeKeeper.getToolkit().getVehicle()==order.getToolkit().getVehicle()){
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

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Map<String, String> getTypes() {
        return types;
    }

    public void setTypes(Map<String, String> types) {
        this.types = types;
    }
}
