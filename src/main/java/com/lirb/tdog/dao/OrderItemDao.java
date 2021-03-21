package com.lirb.tdog.dao;

import com.lirb.tdog.pojo.Order;
import com.lirb.tdog.pojo.OrderItem;
import com.lirb.tdog.pojo.Product;
import com.lirb.tdog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
