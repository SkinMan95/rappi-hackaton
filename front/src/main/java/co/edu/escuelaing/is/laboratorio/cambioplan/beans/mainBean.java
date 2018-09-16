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

import co.edu.escuelaing.is.laboratorio.cambioplan.logic.Order;
import co.edu.escuelaing.is.laboratorio.cambioplan.logic.StoreKeeper;
import co.edu.escuelaing.is.laboratorio.cambioplan.logic.Utiles;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.map.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        orders = new ArrayList<>();
        storeKeepers = new ArrayList<>();
        circleModel = new DefaultMapModel();
    }

    @PostConstruct
    public void init() {
        //circleModel = new DefaultMapModel();
        //storeKeepers = Utiles.getStoresKeepers();
        for (String type : Utiles.getTypes()){
            types.put(type,type);
        }
//        orders = Utiles.getOrders();
        //fillModel();
        load=true;
    }

    public void onStateChange(StateChangeEvent event) {

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
        circleModel.getCircles().clear();
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

    public BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        Axis yAxis = model.getAxis(AxisType.Y);


        int oneO =0;
        int twoO =0;
        for (Order o:orders
             ) {
            if(o.getToolkit().getVehicle()==1){
                oneO++;
            }
            else if(o.getToolkit().getVehicle()==2){
                twoO++;
            }
            else{
                oneO++;
                twoO++;
            }


        }
        int oneS=0;
        int twoS=0;
        for(StoreKeeper s : storeKeepers){
            if(s.getToolkit().getVehicle()==1)
                oneS++;
            else
                twoS++;
        }

        ChartSeries cantidadOrdenes = new ChartSeries();
        cantidadOrdenes.setLabel("Cantidad Ordenes");
        cantidadOrdenes.set("1", oneO);
        cantidadOrdenes.set("2", twoO);



        ChartSeries cantidadVehiculos = new ChartSeries();
        cantidadVehiculos.setLabel("Cantidad Vehiculos");
        cantidadVehiculos.set("1", oneS);
        cantidadVehiculos.set("2", twoS);

        yAxis.setMin(0);
        yAxis.setMax(Math.max(Math.max(Math.max(oneO,oneS),twoO),twoS));

        model.addSeries(cantidadOrdenes);
        model.addSeries(cantidadVehiculos);

        model.setTitle("Saturacion vehiculos");
        model.setAnimate(true);
        model.setLegendPosition("ne");

        return model;
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
