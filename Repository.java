package com.delivery_system;

import java.util.*;
import java.util.stream.Collectors;

//TODO: Solid принципы
public class Repository{

    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Courier> couriers = new ArrayList<>();
    private static int maxOrderNumber;
    private static final String dateFormat = "dd.MM.yyyy|HH:mm";
    private final Strategy strategy;



    public static String getDateFormat() {
        return dateFormat;
    }

    public Repository(Strategy strategy) {
        this.strategy = strategy;
    }

    public void addOrder(float orderWeight, GeoCoordinate source, GeoCoordinate destination) {
        Order newOrder = new Order(++maxOrderNumber, orderWeight, source, destination);
        orders.add(newOrder);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addCourier(Courier courier) {
        couriers.add(courier);
    }

    public boolean assignCourier(Order order) {
        if (order.getCourier() != null)
            return false;
        //TODO: Вынести в интерфейс findFreeCouriers()

        Courier courier = strategy.select(order, couriers);
        if (courier != null) {
            Calendar calendar = Calendar.getInstance();
            order.setStartDateTime(calendar);
            order.setCourier(courier);
            courier.addOrder(order);
            courier.setAvailable(false);
            return true;
        }
        return false;
    }

    public void completeOrder(Order order) {
        Calendar calendar = Calendar.getInstance();
        order.setEndDateTime(calendar);
        Courier courier = order.getCourier();
        courier.setAvailable(true);
    }

    public Courier getCourierById(int courierId) {
        Optional<Courier> optionalCourier = couriers.stream().filter(t -> t.getCourierId() == courierId).findFirst();
        return optionalCourier.orElse(null);
    }

    public Order getOrderById(Integer orderId) {
        Optional<Order> optionalOrder = orders.stream().filter(t -> t.getOrderId() == orderId).findFirst();
        return optionalOrder.orElse(null);
    }

    //TODO: сделать readonly
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public List<Order> getOrdersInWork() {
        return orders.stream().filter(t -> t.getStartDateTime() != null && t.getEndDateTime() == null).
                collect(Collectors.toList());
    }

    // Ф-ия возвращает список выполненных заказов
    public List<Order> getPerformedOrders() {
        return orders.stream().filter(t -> t.getStartDateTime() != null && t.getEndDateTime() != null).
                collect(Collectors.toList());
    }

    public List<Courier> getCouriers() {
        return Collections.unmodifiableList(couriers);
    }

    public List<Courier> getFreeOrBusyCouriers(boolean isAvailable) {

        return couriers.stream().
                filter(t -> t.isAvailable() == isAvailable).
                collect(Collectors.toList());
    }

}
