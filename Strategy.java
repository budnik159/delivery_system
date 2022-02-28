package com.delivery_system;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Strategy {

    Courier select(Order order, List<Courier> couriers);

    default  List<Courier> findFreeCouriers(List<Courier> couriers, float orderWeight) {
        return couriers.stream().filter(t -> t.isAvailable() && t.getMaxParcelWeight() >= orderWeight).
                collect(Collectors.toList());
    }


}
