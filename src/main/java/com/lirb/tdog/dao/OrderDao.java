package com.lirb.tdog.dao;

import com.lirb.tdog.pojo.Order;
import com.lirb.tdog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {
    List<Order> findByUserAndStatusNotOrderByIdDesc(User user,String status);
}
