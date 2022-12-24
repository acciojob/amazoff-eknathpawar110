package com.driver.Service;

import com.driver.Model.DeliveryPartner;
import com.driver.Model.Order;
import com.driver.Repository.DelivaeryPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class AmzonService {
    @Autowired
    private DelivaeryPartnerRepository delivaeryPartnerRepository;

    public void addOrder(Order order){
        delivaeryPartnerRepository.addOrder(order);
    }

    public void addDeliveryPartner(String deliveryPartner){
       delivaeryPartnerRepository.addDeliveryPartner(deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        delivaeryPartnerRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId){
        return delivaeryPartnerRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return delivaeryPartnerRepository.getPartnerById(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return delivaeryPartnerRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<Order> orderList = delivaeryPartnerRepository.getOrdersByPartnerId(partnerId);
        List<String> stringList = new ArrayList<>();
        for(Order order:orderList) stringList.add(order.getId());
        return stringList;
    }

    public List<String> getAllOrders(){
        List<Order> orderList = delivaeryPartnerRepository.getAllOrders();
        List<String> stringList = new ArrayList<>();
        for(Order order:orderList) stringList.add(order.getId());
        return stringList;
    }

    public int getCountOfUnassignedOrders(){
        return delivaeryPartnerRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime, String partnerId){
        return delivaeryPartnerRepository.getOrdersLeftAfterGivenTimeByPartnerId(deliveryTime,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return delivaeryPartnerRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId){
       delivaeryPartnerRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){
        delivaeryPartnerRepository.deleteOrderById(orderId);
    }
}
