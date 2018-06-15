/*
* 自定义的js代码,用于获取店铺内所有的商品
*
* */

/* 定义一个全局变量用于记录末页的页码,服务于页面自动跳转到最后一页 */
var maxPage;
var currentPage;
$(function(){
    to_page("/admin/ajax/shop/all",1);
})


/* 抽取出来的跳转到指定页码页面的方法 */
function to_page(url,pn){
    $.ajax({
        url:url,
        data:"pn="+pn,
        type:"post",
        success:function(result){
            maxPage = result.extend.pageInfo.pages;
            currentPage = result.extend.pageInfo.pageNum;
            build_product_table(result);
            build_page_info(result);
            build_page_nav(url,result);
        }
    });
}


/*获取商品信息并回显,需要传入一个url字符串以调用控制层不同的查询方法*/
function build_product_table(result){

                var shopList = result.extend.pageInfo.list;


                $("#shopListRow").empty();
                $.each(shopList,function (index,shop) {

                    var createTime = new Date(shop.createTime);
                    var editTime = new Date(shop.editTime);
                    $("#shopListRow").append(
                        "<div class=\"col-lg-3 col-md-6 col-sm-6\">" +
                            "<div class=\"card mb-4 box-shadow\">" +
                                "<img class=\"card-img-top\" src=\"../../../" +shop.shopImg +"\" alt=\"店铺名称\">" +
                            "<div class=\"card-body\">" +

                                "<h1>" +shop.shopName + "</h1>" +
                                "<div>" +
                                    "<span>店主 :  " +shop.personInfo.name+ "</span>"+
                                "</div>" +
                                "<div class='status'>" +
                                    "<small class=\"text-muted\" >所属区域 : " +shop.area.areaName +"</small>" +
                                "</div>" +
                                "<div>" +
                                "<small class=\"text-muted\">店铺等级 : "+shop.priority+ "</small>" +
                                "</div>" +
                                "<div>" +
                                    "<small class=\"text-muted\">店铺创建时间 : " +createTime.toLocaleDateString() +"</small>" +
                                "</div>" +
                                "<div>" +
                                    "<small class=\"text-muted\">最后编辑时间 : " +editTime.toLocaleDateString()+ "</small>" +
                                "</div>" +

                                "<p class=\"card-text\">" +shop.shopDesc + "</p>" +
                                "<div class=\"\">" +
                                    "<div class=\"btn-group\">" +
                                        "<a type=\"button\" class=\"btn btn-sm btn-outline-secondary btn_shopDetails\"  href='/common/shop/shopDetailsPage/"+shop.shopId+"'>详情</a>" +
                                        "<a type=\"button\" class=\"btn btn-sm btn-outline-secondary edit btn_edit\"  href=\"/admin/shop/EditPage/"+shop.shopId+"\""+"\">编辑</a>" +
                                    "</div>" +
                                "</div>" +
                            "</div>"+
                        "</div>"
                    );
                })

}



/*分页功能部分*/
/*  当前第${pageInfo.pageNum }/${pageInfo.pages }页,当前页记录数:${pageInfo.size }条;总记录数:${pageInfo.total }条; */
function build_page_info(result){
    /* 生成新的元素前一定要先清空掉以前的数据,否则会累加到页面上 */
    $("#page_info").empty();
    $("#page_info").append("当前第"+result.extend.pageInfo.pageNum+"/"+result.extend.pageInfo.pages+"页,当前页记录数:"+result.extend.pageInfo.size+"条;总记录数:"+result.extend.pageInfo.total+"条");
}

/* 显示分页条 */
function build_page_nav(url,result){
    /* 生成新的元素前一定要先清空掉以前的数据,否则会累加到页面上 */
    $("#page_nav").empty();
    var ul = $("<ul></ul>").addClass("pagination");

    /* 生成首页按钮 */
    var li_frist = $("<li class='page-item'></li>").append($("<a class='page-link'></a>").attr("href","#").append("首页"));

    ul.append(li_frist);

    /* 生成上一页按钮 */
    var li_pre = $("<li class='page-item'></li>").append($("<a class='page-link'></a>").attr("href","#").append("&laquo;"));

    ul.append(li_pre);

    /* 当没有上一页时则按钮不可点击,否则可以点击 */
    if(result.extend.pageInfo.hasPreviousPage == false){
        li_frist.addClass("disabled");

        li_pre.addClass("disabled");
    }else{
        /* 为首页按钮添加一个点击跳转到首页的绑定事件 */
        li_frist.click(function(){
            to_page(url,1);
            return false;
        });
        /* 为上一页按钮添加一个点击跳转到上一页的绑定事件 */
        li_pre.click(function(){
            to_page(url,result.extend.pageInfo.prePage);
            return false;
        });
    }

    /* 遍历分页信息中是否有12345等页码数 */
    $.each(result.extend.pageInfo.navigatepageNums,function(index,nums){
        var a_nums = $("<a class='page-link'></a>").attr("href","#").append(nums)
        var li_nums = $("<li class='page-item'></li>").append(a_nums);

        if(result.extend.pageInfo.pageNum == nums){
            /* 当前页码高亮显示 ,如果不是当前页码,则会在else里添加点击功能*/
            li_nums.addClass("active");
        }else{
            /* 为每一个遍历后生成出来的li_nums添加一个点击跳转的绑定事件 */
            li_nums.click(function(){
                to_page(url,nums);
                return false;
            });
        }
        ul.append(li_nums);
    });

    /* 生成下一页按钮 */
    var li_next = $("<li class='page-item'></li>").append($("<a class='page-link'></a>").attr("href","#").append("&raquo;"));

    ul.append(li_next);

    /* 生成末页按钮 */
    var li_last = $("<li class='page-item'></li>").append($("<a class='page-link'></a>").attr("href","#").append("末页"));

    ul.append(li_last);

    /* 当没有下一页时按钮不可点击,否则可以点击 */
    if(result.extend.pageInfo.hasNextPage == false){
        li_next.addClass("disabled");
        li_last.addClass("disabled");
    }else{
        /* 为下一页按钮添加一个点击跳转到下一页的绑定事件 */
        li_next.click(function(){
            to_page(url,result.extend.pageInfo.nextPage);
            return false;
        });
        /* 为末页按钮添加一个点击跳转到末页的绑定事件 */
        li_last.click(function(){
            to_page(url,result.extend.pageInfo.pages);
            return false;
        });
    }

    var nav = $("<nav></nav>").attr("aria-label","Page navigation").append(ul);
    $("#page_nav").append(nav);
}









