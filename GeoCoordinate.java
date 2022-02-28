package com.delivery_system;

public class GeoCoordinate {

    private final double latitude;
    private final double longitude;

    public GeoCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double findDistance(GeoCoordinate source) {
        double deltaX = source.getLatitude() - latitude;
        double deltaY = source.getLongitude() - longitude;

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}
