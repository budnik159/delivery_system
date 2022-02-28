package com.delivery_system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RepositoryTest {

    Repository repository = new Repository(new MinimalDistanceStrategy());
    @Before
    public void beforeTest(){
        Courier firstCourier = new Courier(1, "Константин", "233-43-54", 10, true);
        Courier secondCourier = new Courier(2, "Артем", "212-11-07", 20, true);
        Courier thirdCourier = new Courier(3, "Антон", "210-10-20", 15, false);
        Courier fourthCourier = new Courier(4, "Павел", "269-13-13", 12, false);

        firstCourier.setCurrentLocation(new GeoCoordinate(20, 20));
        secondCourier.setCurrentLocation(new GeoCoordinate(30, 30));
        thirdCourier.setCurrentLocation(new GeoCoordinate(40, 40));
        fourthCourier.setCurrentLocation(new GeoCoordinate(50, 50));

        Order firstOrder = new Order(1, 9, new GeoCoordinate(10, 10), new GeoCoordinate(100, 100));
        Order secondOrder = new Order(2, 10, new GeoCoordinate(20, 20), new GeoCoordinate(100, 100));
        Order thirdOrder = new Order(3, 7, new GeoCoordinate(30, 30), new GeoCoordinate(100, 100));
        Order fourthOrder = new Order(4, 3, new GeoCoordinate(40, 40), new GeoCoordinate(100, 100));
        Order fifthOrder = new Order(5, 5, new GeoCoordinate(50, 50), new GeoCoordinate(100, 100));

        repository.addOrder(firstOrder);
        repository.addOrder(secondOrder);
        repository.addOrder(thirdOrder);
        repository.addOrder(fourthOrder);
        repository.addOrder(fifthOrder);

        repository.addCourier(firstCourier);
        repository.addCourier(secondCourier);
        repository.addCourier(thirdCourier);
        repository.addCourier(fourthCourier);


        //TODO: убрать
        /*Random random = new Random();
        // для округления до 6 знаков
        int accuracy = 1000000;
        for (int i = 0; i < 5 ; i++) {
            // широта меняется от -90 до 90
            double latitudeSource = random.nextDouble()*180 - 90;
            double latitudeDestination = random.nextDouble()*180 - 90;
            latitudeSource = Math.ceil(latitudeSource*accuracy)/accuracy;
            latitudeDestination = Math.ceil(latitudeDestination*accuracy)/accuracy;

            //долгота меняется от -180 до 180
            double longitudeSource = random.nextDouble()*360 - 180;
            double longitudeDestination = random.nextDouble()*360 - 180;
            longitudeSource = Math.ceil(longitudeSource*accuracy)/accuracy;
            longitudeDestination = Math.ceil(longitudeDestination*accuracy)/accuracy;

            source.add(new GeoCoordinate(latitudeSource, longitudeSource));
            destination.add(new GeoCoordinate(latitudeDestination, longitudeDestination));

        }*/

    }

    // проверка того, что курьеры получат одинаковое кол-во заказов (при стратегии MinimalOrderStrategy)
    @Test
    public void assignFreeCourierToFreeOrder(){

        repository.assignCourier(repository.getOrderById(1));
        repository.assignCourier(repository.getOrderById(2));
        repository.completeOrder(repository.getOrderById(1));
        repository.assignCourier(repository.getOrderById(3));
        repository.completeOrder(repository.getOrderById(2));
        repository.completeOrder(repository.getOrderById(3));
        repository.assignCourier(repository.getOrderById(4));
        assertEquals(repository.getCourierById(1).getOrders().size(),repository.getCourierById(2).getOrders().size() );

    }

    // попытка назначить курьера на заказ для случая, когда все курьеры заняты
    @Test
    public void assignOrderAllCouriersBusy(){

        for (int i = 1; i < 5; i++) {
            repository.getCourierById(i).setAvailable(false);
        }
        assertFalse(repository.assignCourier(repository.getOrderById(1)));
    }

    //назначение курьера на заказ для случая, когда свободен только один курьер
    @Test
    public void assignOrderOnlyOneCouriersFree(){

        // курьеры с Id = 1, 2, 3 будут заняты. Курьер с id = 4 будет свободен
        repository.getCourierById(4).setAvailable(true);
        for (int i = 1; i < 4; i++) {
            repository.getCourierById(i).setAvailable(false);
        }

        Order order = repository.getOrderById(1);
        assertTrue(repository.assignCourier(order));
        int freeCourierId = order.getCourier().getCourierId();
        assertEquals(4, freeCourierId);
    }

    // назначение курьера на заказ, который уже выполняется другим курьером
    @Test
    public void assignCourierForBusyOrder(){
        repository.assignCourier(repository.getOrderById(1));
        assertFalse(repository.assignCourier(repository.getOrderById(1)));
    }

    // проверка назначения заказов (все курьеры свободны)
    @Test
    public void assignMinimalOrdersStrategy(){
        for (int i = 1; i < 5; i++) {
            repository.getCourierById(i).setAvailable(true);
        }
        for (int i = 1; i < 5; i++) {
            Order assignedOrder = repository.getOrderById(i);
            repository.assignCourier(assignedOrder);
            int assignedCourierId = assignedOrder.getCourier().getCourierId();
            assertEquals((Integer)i, (Integer)assignedCourierId);
        }

        for (int i = 0; i < repository.getCouriers().size(); i++) {
            assertFalse(repository.getCouriers().get(i).isAvailable());
        }
    }

    // проверка очередности назначения заказов на 2-ух свободных курьеров
    @Test
    public void assignMinimalOrdersStrategy2(){
        //курьеры с id = 1, 2 - свободны, с id = 3, 4 - заняты
        repository.getCourierById(1).setAvailable(true);
        repository.getCourierById(2).setAvailable(true);
        repository.getCourierById(3).setAvailable(false);
        repository.getCourierById(4).setAvailable(false);
        int j = 0;
        // цикл по всем заказам
        for (int i = 1; i < repository.getOrders().size(); i++) {
            Order assignedOrder = repository.getOrderById(i);
            repository.assignCourier(assignedOrder);
            int assignedCourierId = assignedOrder.getCourier().getCourierId();

            if(i % 2 == 1){
                j = 1;
            } else{
                j = 2;
            }

            assertEquals(j, assignedCourierId);
            repository.completeOrder(assignedOrder);
        }

    }

    // проверка назначение заказа на ближайшего курьера
    @Test
    public void assignMinimalDistanceStrategy(){
        for (int i = 1; i < 5; i++) {
            repository.getCourierById(i).setAvailable(true);
        }

        repository.assignCourier(repository.getOrderById(1));
        assertFalse(repository.getCourierById(1).isAvailable());

        repository.assignCourier(repository.getOrderById(4));
        assertFalse(repository.getCourierById(3).isAvailable());



    }


}