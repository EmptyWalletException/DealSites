package com.kingguanzhang.dealsites.controller.admin;


import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.ProductCategory;
import com.kingguanzhang.dealsites.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/ajax/productCategory")
    @ResponseBody
    public Msg getCategory(){
       List<ProductCategory> productCategoryList = productCategoryService.getCategory();
       return  Msg.success().setMsg("获取分类成功").add("productCategoryList",productCategoryList);
    }


    @RequestMapping("/ajax/addProductCategory")
    @ResponseBody
    public Msg addProductCategroy(@RequestParam("productCategoryName") String name, @RequestParam("priority")Integer priority){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName(name);
        productCategory.setPriority(priority);
        productCategory.setCreateTime(new Date(System.currentTimeMillis()));
        //shopId为了安全只能从session中获取
        //分类id由数据库自增;
        Integer integer = productCategoryService.addProductCategroy(productCategory);
        if (integer >0){
        return  Msg.success().setMsg("创建分类成功");
        }
        return Msg.fail().setMsg("创建分类失败");
    }

    @RequestMapping("/ajax/editProductCategory")
    @ResponseBody//'productCategoryLevel':productCategoryLevel
    public Msg editProductCategory(@RequestParam("productCategoryId") Integer Id, @RequestParam("productCategoryName") String name, @RequestParam("productCategoryLevel") Integer priority){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName(name);
        productCategory.setProductCategoryId(Id);
        productCategory.setPriority(priority);
        Integer integer = productCategoryService.editProductCategroy(productCategory);
        if (integer >0){
            return  Msg.success().setMsg("修改分类成功");
        }
        return Msg.fail().setMsg("修改分类失败");
    }

    @RequestMapping("/ajax/deleteProductCategory")
    @ResponseBody
    public Msg editProductCategory(@RequestParam("productCategoryId") Integer Id){
        Integer integer = productCategoryService.deleteProductCategroy(Id);
        if (integer >0){
            return  Msg.success().setMsg("删除分类成功");
        }
        return Msg.fail().setMsg("删除分类失败");
    }

}
