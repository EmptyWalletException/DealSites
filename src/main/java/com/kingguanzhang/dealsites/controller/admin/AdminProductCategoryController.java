package com.kingguanzhang.dealsites.controller.admin;


import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.ProductCategory;
import com.kingguanzhang.dealsites.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 跳转到商品分类管理页;
     * @return
     */
    @RequestMapping(value = "/productCategory/productCategoryManagementPage",method = RequestMethod.GET)
    public String ShowProductCategoryManagementPage(){
        return "admin/productCategoryManagement";
    }


    /**
     * ajax获取商品分类列表;
     * @return
     */
    @RequestMapping(value = "/ajax/productCategory/all",method = RequestMethod.GET)
    @ResponseBody
    public Msg getCategory(){
       List<ProductCategory> productCategoryList = productCategoryService.getCategory();
       return  Msg.success().setMsg("获取分类成功").add("productCategoryList",productCategoryList);
    }


    /**
     * 新增一个商品分类
     * @param name
     * @param priority
     * @return
     */
    @RequestMapping(value = "/ajax/productCategory/add",method = RequestMethod.POST)
    @ResponseBody
    public Msg addProductCategroy(@RequestParam("productCategoryName") String name, @RequestParam("priority")Integer priority){
        if (null != name || null != priority){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryName(name);
            productCategory.setPriority(priority);
            productCategory.setCreateTime(new Date(System.currentTimeMillis()));
            //shopId为了安全只能从session中获取
            int integer = 0;
            try{
            //分类id由数据库自增;
             integer = productCategoryService.addProductCategroy(productCategory);
            }catch (Exception e){
                e.printStackTrace();
                return Msg.fail().setMsg("新增分类失败");
            }
            if (integer >0){
                return  Msg.success().setMsg("创建分类成功");
            }
        }
        return Msg.fail().setMsg("创建分类失败");
    }

    /**
     * 更新商品分类
     * @param Id
     * @param name
     * @param priority
     * @return
     */
    @RequestMapping(value = "/ajax/productCategory/update",method = RequestMethod.POST)
    @ResponseBody//'productCategoryLevel':productCategoryLevel
    public Msg editProductCategory(@RequestParam("productCategoryId") Integer Id, @RequestParam("productCategoryName") String name, @RequestParam("productCategoryLevel") Integer priority){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName(name);
        productCategory.setProductCategoryId(Id);
        productCategory.setPriority(priority);
        int integer = 0;
        try{
          integer = productCategoryService.updateProductCategory(productCategory);
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().setMsg("修改失败");
        }
        if (integer >0){
            return  Msg.success().setMsg("修改分类成功");
        }
        return Msg.fail().setMsg("修改分类失败");
    }

    /**
     * 删除一个商品分类
     * @param Id
     * @return
     */
    @RequestMapping(value = "/ajax/productCategory/delete",method = RequestMethod.POST)
    @ResponseBody
    public Msg editProductCategory(@RequestParam("productCategoryId") Integer Id){
        int integer = 0;
        try{
         integer = productCategoryService.deleteProductCategroy(Id);
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().setMsg("删除失败");
        }
        if (integer >0){
            return  Msg.success().setMsg("删除分类成功");
        }
        return Msg.fail().setMsg("删除分类失败");
    }

}
