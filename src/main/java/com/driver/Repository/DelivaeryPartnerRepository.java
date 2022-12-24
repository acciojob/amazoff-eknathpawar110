package com.driver.Repository;

import com.driver.Model.DeliveryPartner;
import com.driver.Model.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@org.springframework.stereotype.Repository
public class DelivaeryPartnerRepository {

    HashMap<String,Order> orderMap = new HashMap<>(); // (orderId,orderObject)
    HashMap<String,DeliveryPartner> partnerMap = new HashMap<>(); // (deliverPartnerId,deliverPartnerObject)

    HashMap<DeliveryPartner, List<Order>> pairMap = new HashMap<>(); // partner,List(orders)

    HashMap<Order,DeliveryPartner> revPairMap = new HashMap<>(); // reverse of pariMap for faster referal of oder to parnter

    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
    }

    public void addDeliveryPartner(String deliveryPartner){
        partnerMap.put(deliveryPartner,new DeliveryPartner(deliveryPartner));
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        Order order = orderMap.get(orderId);
        if(!pairMap.containsKey(deliveryPartner)){
            pairMap.put(deliveryPartner,new ArrayList<>());
        }
        List<Order> list = pairMap.get(deliveryPartner);
        list.add(order);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        revPairMap.put(order,deliveryPartner);
    }

    public Order getOrderById(String orderId){
        return orderMap.getOrDefault(orderId,null);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return partnerMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return partnerMap.get(partnerId).getNumberOfOrders();
    }

    public List<Order> getOrdersByPartnerId(String partnerId){
        return pairMap.getOrDefault(partnerMap.get(partnerId),null);
    }

    public List<Order> getAllOrders(){
        List<Order> orderList = new ArrayList<>();
        for(Order order:orderMap.values()) orderList.add(order);
        return orderList;
    }

    public int getCountOfUnassignedOrders(){
        return orderMap.size() - revPairMap.size();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime, String partnerId){
        String[] list = deliveryTime.split(":");
        int time = (Integer.parseInt(list[0])*60) + Integer.parseInt(list[1]);
        int count = 0;
        for(Order order:pairMap.get(partnerMap.get(partnerId))) if(order.getDeliveryTime() > time) count++;
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<Order> orderList = pairMap.get(partnerMap.get(partnerId));
        int largestTime = 0;
        for(Order order:orderList) largestTime = Math.max(order.getDeliveryTime(),largestTime);
        int hour = largestTime / 60;
        int min = largestTime % 60;
        String time = "";
        if(hour<10) time = time+"0";
        time = time+String.valueOf(hour)+":";
        if(min<10) time = time+"0";
        time = time+String.valueOf(min);
        return time;
    }

    public void deletePartnerById(String partnerId){
        DeliveryPartner partner = partnerMap.get(partnerId);
        partnerMap.remove(partnerId);
        List<Order> orderList = pairMap.get(partner);
        pairMap.remove(partner);
        for(Order order:orderList) revPairMap.remove(order);
    }

    public void deleteOrderById(String orderId){
        Order order = orderMap.get(orderId);
        orderMap.remove(orderId);
        DeliveryPartner partner = revPairMap.getOrDefault(order,null);
        if(partner != null) {
            revPairMap.remove(order);
            List<Order> orderList = pairMap.get(partner);
            partner.setNumberOfOrders(partner.getNumberOfOrders()-1);
            orderList.remove(order);
        }
    }
}
