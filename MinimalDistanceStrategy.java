package com.delivery_system;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class MyComparator implements Comparator<Courier>{
    Order order;

    public MyComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(Courier firstCourier, Courier secondCourier) {
        double firstCourierDist = firstCourier.getCurrentLocation().findDistance(order.getSource());
        double secondCourierDist = secondCourier.getCurrentLocation().findDistance(order.getSource());

        return Double.compare(firstCourierDist, secondCourierDist);
    }
}

public class MinimalDistanceStrategy implements Strategy {

    public Courier selectFirst(Order order, List<Courier> couriers) {
        List<Courier> freeCouriers = findFreeCouriers(couriers, order.getOrderWeight());

        if (freeCouriers.size() > 0) {
            Courier courier = freeCouriers.get(0);
            GeoCoordinate courierCoordinate = new GeoCoordinate(courier.getCurrentLocation().getLatitude(), courier.getCurrentLocation().getLongitude());
            GeoCoordinate orderCoordinate = new GeoCoordinate(order.getSource().getLatitude(), order.getSource().getLongitude());

            double minDistance = courierCoordinate.findDistance(orderCoordinate);
            for (int i = 1; i < freeCouriers.size(); i++) {
                courierCoordinate = new GeoCoordinate(freeCouriers.get(i).getCurrentLocation().getLatitude(), freeCouriers.get(i).getCurrentLocation().getLongitude());
                double distance = courierCoordinate.findDistance(orderCoordinate);
                if (distance < minDistance) {
                    courier = freeCouriers.get(i);
                    minDistance = distance;
                }
            }

            return courier;
        }
        return null;
    }

    @Override
    public Courier select(Order order, List<Courier> couriers) {
        List<Courier> freeCouriers = findFreeCouriers(couriers, order.getOrderWeight());
        Comparator<Courier> comp = new MyComparator(order);
        //TODO: передаю объект типа Comparator, т.к. метод compareTo, в классе Courier, уже реализован для стратегии MinimalOrdersStrategy
        //Optional<Courier> courier = freeCouriers.stream().min(comp);
        Optional<Courier> courier = freeCouriers.stream().min((o1, o2) -> {
            double firstCourierDist = o1.getCurrentLocation().findDistance(order.getSource());
            double secondCourierDist = o2.getCurrentLocation().findDistance(order.getSource());
            return Double.compare(firstCourierDist, secondCourierDist);
        });
        return courier.orElse(null);
    }



}
