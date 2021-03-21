package com.lirb.tdog.service;

import com.lirb.tdog.dao.PropertyValueDao;
import com.lirb.tdog.pojo.Product;
import com.lirb.tdog.pojo.PropertyValue;
import com.lirb.tdog.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="propertyValues")
public class PropertyValueService  {
    @Autowired
    PropertyValueDao propertyValueDAO;
    @Autowired
    PropertyService propertyService;

    @CacheEvict(allEntries=true)
    public void update(PropertyValue bean) {
        propertyValueDAO.save(bean);
    }

    public void init(Product product) {
        List<Property> properties= propertyService.listByCategory(product.getCategory());
        for (Property property: properties) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if(null==propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    @Cacheable(key="'propertyValues-one-pid-'+#p0.id+ '-ptid-' + #p1.id")
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueDAO.getByPropertyAndProduct(property,product);
    }

    @Cacheable(key="'propertyValues-pid-'+#p0.id")
    public List<PropertyValue> list(Product product) {
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }

}
