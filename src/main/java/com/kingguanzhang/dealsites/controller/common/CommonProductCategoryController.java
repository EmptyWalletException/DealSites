package com.kingguanzhang.dealsites.controller.common;

import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.ProductCategory;
import com.kingguanzhang.dealsites.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/common")
@Controller
public class CommonProductCategoryController {


    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 在首页ajax获取商品分类列表;
     * @return
     */
    @RequestMapping(value = "/ajax/productCategory/all",method = RequestMethod.GET)
    @ResponseBody
    public Msg getCategory(){
        List<ProductCategory> productCategoryList = productCategoryService.getCategory();
        return  Msg.success().setMsg("获取分类成功").add("productCategoryList",productCategoryList);
    }
}
