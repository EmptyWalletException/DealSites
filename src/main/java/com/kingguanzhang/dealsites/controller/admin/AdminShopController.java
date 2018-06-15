package com.kingguanzhang.dealsites.controller.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Area;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Shop;
import com.kingguanzhang.dealsites.service.AreaService;
import com.kingguanzhang.dealsites.service.ShopService;
import com.kingguanzhang.dealsites.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RequestMapping("/admin")
@Controller
public class AdminShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AreaService areaService;

    /**
     * 获取所有店铺列表;
     * @param pn
     * @return
     */
    @RequestMapping(value = "/ajax/shop/all",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllShop(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,8);
        List<Shop> shopList =shopService.getAllShop();
        PageInfo pageInfo = new PageInfo(shopList,5);
        return Msg.success().setMsg("获取店铺列表成功").add("pageInfo",pageInfo);
    }

    /**
     * 跳转到店铺管理页;
     * @return
     */
    @RequestMapping("/shop/ManagementPage")
    public String showAdminShopManagement(){
        return "admin/shopManagement";
    }


    /**
     * 跳转到店铺编辑页
     * @return
     */
    @RequestMapping(value = "/shop/EditPage/{shopId}",method = RequestMethod.GET)
    public String adminShowEditShop(@PathVariable("shopId") Integer shopId, Model model){
        Shop shop = shopService.getShop(shopId);
        List<Area> areaList = areaService.getAreas();
        model.addAttribute("shop",shop);
        model.addAttribute("areaList",areaList);
        return "admin/editShop";
    }

    /**
     * ajax更新店铺;与卖家的编辑店铺代码一致,为了方便以后维护所以不抽取;
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/shop/update",method = RequestMethod.POST)
    @ResponseBody
    public Msg adminUpdateShop(HttpServletRequest request) {

        //从前端传来的请求中获取键为shopStr的值;
        String shopStr = RequestUtil.parserString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;

        try {
            //将前端传来的商店信息转换为shop实体类;
            System.out.print("shopStr的值是:" + shopStr);
            shop = objectMapper.readValue(shopStr, Shop.class);

            /*这里需要注意,shopId需要小心处理,建议页面上一步查询时就写入session,防止用户在前端修改id导致处理了错误的数据;*/
            int shopId = (int)request.getSession().getAttribute("shopId");//从session中取出商店id
            shop.setShopId(shopId);

        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().setMsg("店铺信息不能正确解析");
        }

        //从request中解析出上传的文件图片;
        MultipartFile shopImg = ((MultipartRequest) request).getFile("shopImg");

        if (null != shopImg){
            //使用文件.getOriginalFilename可以获取带后缀.jpg的全名;或者文件.getItem.getName也可以获取带后缀的文件名;否则只能取到不带后缀的文件名;
            try {
                shopService.updateShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return Msg.fail().setMsg("更新店铺失败");
            }
        }else {
            shopService.updateShopWithoutImg(shop);
        }


        shop =shopService.getShop(shop.getShopId());
        return Msg.success().setMsg("更新店铺成功").add("shop",shop);

    }




}
