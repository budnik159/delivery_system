package com.delivery_system;

import java.util.*;
import java.util.stream.Collectors;

public class Courier implements Comparable<Courier> {

    private Integer courierId;
    private String name;
    private String phoneNumber;
    private int maxParcelWeight;
    private boolean isAvailable;
    private GeoCoordinate currentLocation;
    private ArrayList<Order> orders = new ArrayList<>();
  //  private HashSet<Integer> ordersId;


    public Courier(Integer courierId, String name, String phoneNumber, int maxParcelWeight, boolean isAvailable) {
        this.courierId = courierId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.maxParcelWeight = maxParcelWeight;
        this.isAvailable = isAvailable;

     /*   Random random = new Random();
        // для округления до 6 знаков
        int accuracy = 1000000;
        // широта меняется от -90 до 90
        double latitude = random.nextDouble()*180 - 90;
        latitude = Math.ceil(latitude*accuracy)/accuracy;
        //долгота меняется от -180 до 180
        double longitude = random.nextDouble()*360 - 180;
        longitude = Math.ceil(longitude*accuracy)/accuracy;
        GeoCoordinate currentLocation = new GeoCoordinate(latitude, longitude);
        setCurrentLocation(currentLocation);*/
    }

    public Integer getCourierId() {
        return courierId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMaxParcelWeight() {
        return maxParcelWeight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public GeoCoordinate getCurrentLocation() {
        return currentLocation;
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCurrentLocation(GeoCoordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public List<Order> getOrdersForDate(Calendar calendar){

        List<Order> oList = orders.stream().filter(t -> AppUtil.getDateWithoutTimeUsingCalendar(t.getStartDateTime()).equals(AppUtil.getDateWithoutTimeUsingCalendar(calendar)))
                .collect(Collectors.toList());
        return  oList;
     /*   return orders.stream().filter(t -> AppUtil.getDateWithoutTimeUsingCalendar(t.getStartDateTime()) == AppUtil.getDateWithoutTimeUsingCalendar(calendar))
                .collect(Collectors.toList());*/
    }
    public Integer getOrdersForDate2(Calendar calendar){
        return (int) orders.stream().filter(t -> AppUtil.getDateWithoutTimeUsingCalendar(t.getStartDateTime()) == AppUtil.getDateWithoutTimeUsingCalendar(calendar)).count();
    }
    public static void compare(){

    }


    @Override
    public int compareTo(Courier courier) {
        Calendar calendar = Calendar.getInstance();
        int ordersCount1 = this.getOrdersForDate(calendar).size();
        int ordersCount2 = courier.getOrdersForDate(calendar).size();

        return Integer.compare(ordersCount1, ordersCount2);

    }
}
