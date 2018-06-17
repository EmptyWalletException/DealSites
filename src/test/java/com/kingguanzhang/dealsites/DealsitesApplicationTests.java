package com.kingguanzhang.dealsites;

import com.kingguanzhang.dealsites.pojo.Product;
import io.searchbox.client.JestClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DealsitesApplicationTests {

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("水杯");
        product.setNormalPrice(5);
        product.setEnableStatus(1);
        product.setImgAddr("1");
        product.setPriority(1);


        //构建一个索引
        Index index = new Index.Builder(product).index("ds").type("d").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
