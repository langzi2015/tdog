package com.lirb.tdog.dao;

import com.lirb.tdog.pojo.Product;
import com.lirb.tdog.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review,Integer> {
    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
}
