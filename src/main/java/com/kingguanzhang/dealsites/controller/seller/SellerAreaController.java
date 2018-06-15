package com.kingguanzhang.dealsites.controller.seller;


import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Area;
import com.kingguanzhang.dealsites.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/seller")
@Controller
public class SellerAreaController {

    @Autowired
    AreaService areaService;

    /**
     * ajax方式获取所有区域分类
     * @return
     */
    @RequestMapping(value = "/ajax/area/all",method = RequestMethod.GET)
    @ResponseBody
    public Msg getAreas(){
       List<Area> areaList =  areaService.getAreas();
       return Msg.success().add("areas",areaList);
    }
}
