package com.delivery_system_main;

import com.delivery_system.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void showMainMenu(){
        System.out.println("Выберите действие: ");
        System.out.println("0. Выход");
        System.out.println("1. Просмотр всех заказов");
        System.out.println("2. Просмотр свободных заказов");
        System.out.println("3. Просмотр заказов, находящихся в работе");
        System.out.println("4. Просмотр выполненных заказов");
        System.out.println("5. Добавить заказ");
        System.out.println("6. Просмотр всех курьеров");
        System.out.println("7. Просмотр свободных курьеров");
        System.out.println("8. Просмотр занятых курьеров");
        System.out.println("9. Назначить курьера для заказа");
        System.out.println("10. Отметить заказ, как \"выполненный\"");
    }

    public static void showOrders(ArrayList<Order> orders){
        StringBuilder sb = new StringBuilder();
        sb.append("orderId orderWeight sourceLatitude|sourceLongitude destinationLatitude|destinationLongitude startDateTime endDateTime");
        System.out.println(sb);

        for (Order order : orders) {
            int orderId = order.getOrderId();
            float orderWeight = order.getOrderWeight();
            GeoCoordinate sourceCoord = order.getSource();
            double sourceLatitude = sourceCoord.getLatitude();
            double sourceLongitude = sourceCoord.getLongitude();
            GeoCoordinate destinationCoord = order.getSource();
            double destinationLatitude = destinationCoord.getLatitude();
            double destinationLongitude = destinationCoord.getLongitude();

            Calendar startDate = order.getStartDateTime();
            Calendar endDate =order.getEndDateTime();
            SimpleDateFormat df = new SimpleDateFormat(Repository.getDateFormat());
            String strStartDate = "null";
            String strEndDate = "null";

            if (startDate != null){
                strStartDate = df.format(startDate.getTime());
            }

            if (endDate != null){
                strEndDate = df.format(endDate.getTime());
            }
            sb = new StringBuilder();
           // sb.append("\n");
            sb.append(orderId).append(" ").append(orderWeight).append(" ").append(sourceLatitude).append("|").append(sourceLongitude).append(" ").
                    append(destinationLatitude).append("|").append(destinationLongitude).append(" ").append(strStartDate).append(" ").append(strEndDate);
            System.out.println(sb);

        }
    }

    public static void showCouriers(ArrayList<Courier> couriers){
        StringBuilder sb = new StringBuilder();
        sb.append("courierId name phoneNumber maxParcelWeight isAvailable currentLatitude|currentLongitude");
        System.out.println(sb);

        for (Courier courier : couriers) {
            int courierId = courier.getCourierId();
            String name = courier.getName();
            String phoneNumber = courier.getPhoneNumber();
            float maxParcelWeight = courier.getMaxParcelWeight();
            boolean isAvailable = courier.isAvailable();
            GeoCoordinate currentLocation = courier.getCurrentLocation();
            double currentLatitude = currentLocation.getLatitude();
            double currentLongitude = currentLocation.getLongitude();

            sb = new StringBuilder();
            sb.append(courierId).append(" ").append(name).append(" ").append(phoneNumber).append(" ").append(maxParcelWeight).append(" ").
                    append(isAvailable).append(" ").append(currentLatitude).append("|").append(currentLongitude);
            System.out.println(sb);

        }
    }


    public static void main(String[] args) {


    }

}