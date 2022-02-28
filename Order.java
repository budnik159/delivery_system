package com.delivery_system;

import java.util.Calendar;

public class Order {
    private final int orderId;
    private Courier courier;
    private final float orderWeight;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private final GeoCoordinate source;
    private final GeoCoordinate destination;

    public Order(int orderId, float orderWeight, GeoCoordinate source, GeoCoordinate destination) {
        this.orderId = orderId;
        this.orderWeight = orderWeight;
        this.source = source;
        this.destination = destination;

    }

    public int getOrderId() {
        return orderId;
    }

    public float getOrderWeight() {
        return orderWeight;
    }

    public Calendar getStartDateTime() {
        return startDateTime;
    }

    public Calendar getEndDateTime() {
        return endDateTime;
    }

    public GeoCoordinate getSource() {
        return source;
    }

    public void setStartDateTime(Calendar startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Calendar endDateTime) {
        this.endDateTime = endDateTime;
    }

    public GeoCoordinate getDestination() {
        return destination;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
