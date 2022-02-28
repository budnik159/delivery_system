package com.delivery_system;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MinimalOrdersStrategy implements Strategy {


    public Courier selectFirst(Order order, List<Courier> couriers) {
        List<Courier> freeCouriers = findFreeCouriers(couriers, order.getOrderWeight());
        if (freeCouriers.size() > 0) {
            Calendar calendar = Calendar.getInstance();
            Courier courierWithMinOrdersCount = freeCouriers.get(0);
            int minOrdersCountForCourierInDate = courierWithMinOrdersCount.getOrdersForDate(calendar).size();

            for (int i = 1; i < couriers.size(); i++) {
                Courier courier = couriers.get(i);
                int ordersCountForCourierInDate = courier.getOrdersForDate(calendar).size();
                if (minOrdersCountForCourierInDate > ordersCountForCourierInDate) {
                    courierWithMinOrdersCount = courier;
                    minOrdersCountForCourierInDate = ordersCountForCourierInDate;
                }
            }

            return courierWithMinOrdersCount;
        }
        return null;

    }

    // TODO:
    //1. как правильно понимать эту запись: min(Courier::compareTo) (я передаю в метод min вместо объекта типа Comparator метод compareTo)?
    //2. в классе Courier я реализовал метод compareTo для стратегии MinimalOrdersStrategy. Для стратегии MinimalDistanceStrategy он не подходит.
    // значит в классе MinimalDistanceStrategy, если потребуется использовать метод min, то ему уже нужно будет передавать объект типа Comparator?
    @Override
    public Courier select(Order order, List<Courier> couriers) {
        List<Courier> freeCouriers = findFreeCouriers(couriers, order.getOrderWeight());
        Optional<Courier> courier= freeCouriers.stream().min(Courier::compareTo);
        return courier.orElse(null);
        // Второй вариант
        /*Calendar calendar = Calendar.getInstance();
        Optional<Integer> ints = freeCouriers.stream().map(t -> t.getOrdersForDate(calendar).size()).min(Integer::compareTo);
        Optional<Courier> courier = freeCouriers.stream().filter(t -> t.getOrdersForDate(calendar).size() == ints.get()).findFirst();
         return courier.orElse(null)
        */
    }


}
