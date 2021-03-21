package com.lirb.tdog.es;

import com.lirb.tdog.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductESDAO extends ElasticsearchRepository<Product,Integer>{
}
