package com.driver.Repository;

import com.driver.Model.DeliveryPartner;
import com.driver.Model.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Part;
import java.util.*;


@Repository
@Component
public class DelivaeryPartnerRepository {

    Map<String,Order> orderMap=new HashMap<>();
    Map<String,DeliveryPartner> deliveryPartnerMap=new HashMap<>();
    Map<String, List<String>> map=new HashMap<>();
    List<String> list;

    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
    }

    public void addPartner(String id){
        deliveryPartnerMap.put(id,new DeliveryPartner(id));
    }

    public void addOrderPartnerPair(String oid,String pid){
        if(map.containsKey(pid)){
            map.get(pid).add(oid);
        }
        else{
            list=new ArrayList<>();
            list.add(oid);
            map.put(pid,list);
        }
    }

    public Order getOrderById(String id){
        return orderMap.get(id);
    }

    public DeliveryPartner getPartnerById(String id){
        return deliveryPartnerMap.get(id);
    }

    public int getOrderCountByPartnerId(String id){
        return map.get(id).size();
    }

    public List<String> getOrdersByPartnerId(String id){
        return map.get(id);
    }

    public List<String> getAllOrders(){
        List<String> orderlist=new ArrayList<>();
        for(String id:orderMap.keySet()) orderlist.add(id);
        return orderlist;
    }

    public int getCountOfUnassignedOrders(){
        int ordercount=orderMap.size();
        int partnercount=0;
        for(String id:map.keySet()){
            partnercount+=map.get(id).size();
        }
        return ordercount-partnercount;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String id){
        int t=0;
        list=map.get(id);
        for(String i:list){
            if(orderMap.get(i).getDeliveryTime()>(Integer.valueOf(time.substring(0,2))*60+Integer.valueOf(time.substring(3,5))))
                t++;
        }
        return t;
    }

    public String getLastDeliveryTimeByPartnerId(String id){
        String order=map.get(id).get(map.get(id).size()-1);
        return order;
    }

    public void deletePartnerById(String id){
        map.remove(id);
        deliveryPartnerMap.remove(id);
    }

    public void deleteOrderById(String id){
        orderMap.remove(id);
        for(String i:map.keySet()){
            for(int j=0;j<map.get(i).size();j++){
                if(map.get(i).get(j)==id){
                    map.get(i).remove(j);
                    return;
                }
            }
        }
    }
}
