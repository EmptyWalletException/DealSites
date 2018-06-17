package com.kingguanzhang.dealsites.respository;

import com.kingguanzhang.dealsites.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRespository extends ElasticsearchRepository<Product,Integer> {
}
