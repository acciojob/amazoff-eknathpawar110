package com.driver.Service;

import com.driver.Model.DeliveryPartner;
import com.driver.Model.Order;
import com.driver.Repository.DelivaeryPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class AmzonService {
    @Autowired
    DelivaeryPartnerRepository ORepository;

    public void addOrder(Order order){
        ORepository.addOrder(order);
    }

    public void addPartner(String id){
        ORepository.addPartner(id);
    }

    public void addOrderPartnerPair(String oid,String pid){
        ORepository.addOrderPartnerPair(oid,pid);
    }

    public Order getOrderById(String id){
        return ORepository.getOrderById(id);
    }

    public DeliveryPartner getPartnerById(String id){
        return ORepository.getPartnerById(id);
    }

    public int getOrderCountByPartnerId(String id){
        return ORepository.getOrderCountByPartnerId(id);
    }

    public List<String> getOrdersByPartnerId(String id){
        return ORepository.getOrdersByPartnerId(id);
    }

    public List<String> getAllOrders(){
        return ORepository.getAllOrders();
    }

    public int getCountOfUnassignedOrders(){
        return ORepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String id){
        return ORepository.getOrdersLeftAfterGivenTimeByPartnerId(time,id);
    }

    public String getLastDeliveryTimeByPartnerId(String id){
        return ORepository.getLastDeliveryTimeByPartnerId(id);
    }

    public void deletePartnerById(String id){
        ORepository.deletePartnerById(id);
    }

    public void deleteOrderById(String id){
        ORepository.deleteOrderById(id);
    }
}
