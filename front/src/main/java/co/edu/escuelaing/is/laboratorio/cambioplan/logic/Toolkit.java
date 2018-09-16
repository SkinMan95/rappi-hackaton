package co.edu.escuelaing.is.laboratorio.cambioplan.logic;

public class Toolkit {

    private Double delivery_kit;
    private Double kit_size;
    private Double terminal;
    private Double know_how;
    private Boolean trusted;
    private Double order_level;
    private Double storekeeper_level;
    private Double vehicle;
    private Boolean cashless;
    private Boolean exclusive;

    public Toolkit() {
        delivery_kit = 0.0;
        kit_size = 0.0;
        terminal = 0.0;
        know_how = 0.0;
        trusted = false;
        order_level = 0.0;
        storekeeper_level = 0.0;
        vehicle = 0.0;
        cashless = false;
        exclusive = false;

    }

    public Toolkit(Double delivery_kit, Double kit_size, Double terminal, Double know_how, Boolean trusted, Double order_level, Double storekeeper_level, Double vehicle, Boolean cashless, Boolean exclusive) {
        this.delivery_kit = delivery_kit;
        this.kit_size = kit_size;
        this.terminal = terminal;
        this.know_how = know_how;
        this.trusted = trusted;
        this.order_level = order_level;
        this.storekeeper_level = storekeeper_level;
        this.vehicle = vehicle;
        this.cashless = cashless;
        this.exclusive = exclusive;
    }

    public Double getDelivery_kit() {
        return delivery_kit;
    }

    public void setDelivery_kit(Double delivery_kit) {
        this.delivery_kit = delivery_kit;
    }

    public Double getKit_size() {
        return kit_size;
    }

    public void setKit_size(Double kit_size) {
        this.kit_size = kit_size;
    }

    public Double getTerminal() {
        return terminal;
    }

    public void setTerminal(Double terminal) {
        this.terminal = terminal;
    }

    public Double getKnow_how() {
        return know_how;
    }

    public void setKnow_how(Double know_how) {
        this.know_how = know_how;
    }

    public Boolean getTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public Double getOrder_level() {
        return order_level;
    }

    public void setOrder_level(Double order_level) {
        this.order_level = order_level;
    }

    public Double getStorekeeper_level() {
        return storekeeper_level;
    }

    public void setStorekeeper_level(Double storekeeper_level) {
        this.storekeeper_level = storekeeper_level;
    }

    public Double getVehicle() {
        return vehicle;
    }

    public void setVehicle(Double vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getCashless() {
        return cashless;
    }

    public void setCashless(Boolean cashless) {
        this.cashless = cashless;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }
}

