package com.lirb.tdog.dao;

import com.lirb.tdog.pojo.Product;
import com.lirb.tdog.pojo.Property;
import com.lirb.tdog.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
    PropertyValue getByPropertyAndProduct(Property property, Product product);
}
