

/* 定义一个全局变量用于记录末页的页码,服务于页面自动跳转到最后一页 */
var maxPage;
var currentPage;
$(function(){
    to_page("/buyer/ajax/getCartList",1);
    changeBatchDeleteButton();
})


/* 抽取出来的跳转到指定页码页面的方法 */
function to_page(url,pn){
    $.ajax({
        url:url,
        data:"pn="+pn,
        type:"post",
        success:function(result){
            $("#shopListRow").empty();
            if (200 == result.code){
                $("#shopListRow").append(
                    "<div class='container text-center'><h3 >购物车为空</h3></div>"
                );
                return;
            }
            maxPage = result.extend.pageInfo.pages;
            currentPage = result.extend.pageInfo.pageNum;
            build_product_table(result);
            build_page_info(url,result);
            build_page_nav(url,result);
            /*重新检查页面上需要发生状态改变的按钮,以免逻辑性bug */
            changeAllCheckedButton();
            changeBatchDeleteButton();

        }
    });
}


/*获取商品信息并回显,需要传入一个url字符串以调用控制层不同的查询方法*/
function build_product_table(result){

                var cartList = result.extend.pageInfo.list;

                var flag=0;//用于生成收藏按钮时的判断,注意作用域,并且在每个商品遍历完后要重置成0;
                if(null != result.extend.favoriteProductList){
                    var favoriteProductList = result.extend.favoriteProductList;
                }

                 $.each(cartList,function (index,cart) {
                    var product = cart.product;
                     if(null != favoriteProductList){
                         $.each(favoriteProductList,function (index,favoriteProduct) {
                             if (product.productId == favoriteProduct.productId){
                                 flag = 1;
                             }
                         })
                     }
                    var createTime = new Date(product.createTime);
                    var editTime = new Date(product.editTime);

                    if (0 == flag){
                        $("#shopListRow").append(
                            "<div class=\"col-lg-3 col-md-6 col-sm-6\">" +
                            "<div class=\"card mb-4 box-shadow\">" +
                            "<img class=\"card-img-top\" src=\"../" +product.imgAddr +"\" alt=\"商品图片\">" +
                            "<div class=\"card-body\">" +

                            "<h1>" +product.productName + "</h1>" +
                            "<div>" +
                            "<span>$ " +product.normalPrice + "</span>"+
                            "</div>" +
                            "<div class='status'>" +
                            "<a class=\"text-muted\" href='/common/shopDetails/"+ product.shop.shopId+"'>店铺 : "+ product.shop.shopName +"</a>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">商品创建时间 : " +createTime.toLocaleDateString() +"</small>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">最后编辑时间 : " +editTime.toLocaleDateString()+ "</small>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">商品分类 : "+product.productCategory.productCategoryName+ "</small>" +
                            "</div>" +
                            "<p class=\"card-text\">" +product.productDesc + "</p>" +

                            "<div class=\"\">" +
                            "<div class=\"btn-group\">" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_collectProduct'  productId='"+product.productId+"'><span class='glyphicon glyphicon-heart-empty'></span> 收藏</button>" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_removeProduct'  productId='"+product.productId+"'>删除</button>" +
                            "</div>" +
                            "<br/>"+
                            "<div class=\"btn-group\">" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_minus'  productId='"+product.productId+"'> - </button>" +
                            "<button id='productCount' class='btn btn-sm btn-outline-secondary' disabled>" +cart.productCount+ "</button>"+
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_add ' productId='"+product.productId+"'> + </button>" +
                            "</div>" +
                            "<span class='float-right '>"+
                            "<input type='checkbox' class='check_one' productId='"+product.productId+"' />选中"+
                            "</span>"+
                            "</div>" +
                            "</div>"+
                            "</div>"
                        );
                    }else {
                        $("#shopListRow").append(
                            "<div class=\"col-lg-3 col-md-6 col-sm-6\">" +
                            "<div class=\"card mb-4 box-shadow\">" +
                            "<img class=\"card-img-top\" src=\"../" +product.imgAddr +"\" alt=\"商品图片\">" +
                            "<div class=\"card-body\">" +

                            "<h1>" +product.productName + "</h1>" +
                            "<div>" +
                            "<span>$ " +product.normalPrice + "</span>"+
                            "</div>" +
                            "<div class='status'>" +
                            "<a class=\"text-muted\" href='/common/shopDetails/"+ product.shop.shopId+"'>店铺 : "+ product.shop.shopName +"</a>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">商品创建时间 : " +createTime.toLocaleDateString() +"</small>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">最后编辑时间 : " +editTime.toLocaleDateString()+ "</small>" +
                            "</div>" +
                            "<div>" +
                            "<small class=\"text-muted\">商品分类 : "+product.productCategory.productCategoryName+ "</small>" +
                            "</div>" +
                            "<p class=\"card-text\">" +product.productDesc + "</p>" +

                            "<div class=\"\">" +
                            "<div class=\"btn-group\">" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_cancelCollectProduct'  productId='"+product.productId+"'><span class='glyphicon glyphicon-heart'></span> 取消收藏</button>" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_removeProduct'  productId='"+product.productId+"'>删除</button>" +
                            "</div>" +
                            "<br/>"+
                            "<div class=\"btn-group\">" +
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_minus'  productId='"+product.productId+"'> - </button>" +
                            "<button id='productCount' class='btn btn-sm btn-outline-secondary' disabled>" +cart.productCount+ "</button>"+
                            "<button type='button' class='btn btn-sm btn-outline-secondary btn_add ' productId='"+product.productId+"'> + </button>" +
                            "</div>" +
                            "<span class='float-right '>"+
                            "<input type='checkbox' class='check_one' productId='"+product.productId+"' />选中"+
                            "</span>"+
                            "</div>" +
                            "</div>"+
                            "</div>"
                        );
                    }



                     flag =0;

                })
                changeAllCheckedButton();

}


//删除商品
$("#shopListRow").on('click','.btn_removeProduct',function () {

    var productName = $(this).parent().parent().prevAll("h1").text();
    var confirmRemove = confirm("确认要删除商品:"+productName+"?");
    var productId = $(this).attr("productId");
    if (true == confirmRemove){
        $.ajax({
            url:"/buyer/ajax/removeProductFromCart",
            type:'POST',
            data:{'productId':productId},
            success:function (result) {
                alert(result.msg);
                to_page("/buyer/ajax/getCartList",currentPage);
            }
        });
    } else{
        return;
    }
})

//商品数量加一
$("#shopListRow").on('click','.btn_add',function () {
    var btnCount = $(this).prev();
     var productId = $(this).attr("productId");
        $.ajax({
            url:"/buyer/ajax/updateAddProduct",
            type:'POST',
            data:{'productId':productId},
            success:function (result) {
                if(100 ==result.code){

                   var count = parseInt(btnCount.text())+1;
                   btnCount.empty();
                   btnCount.append(count);
                }
            }
        });

})

//商品数量减一
$("#shopListRow").on('click','.btn_minus',function () {
    var btnCount = $(this).next();

    if (1 == btnCount.text()) {
        return false;
    }
    var productId = $(this).attr("productId");
        $.ajax({
            url:"/buyer/ajax/updateMinusProduct",
            type:'POST',
            data:{'productId':productId},
            success:function (result) {
                if(100 ==result.code){

                    var count = parseInt(btnCount.text()) -1;
                    btnCount.empty();
                    btnCount.append(count);
                }
            }
        });
})

//收藏商品
$("#shopListRow").on('click','.btn_collectProduct',function () {
    var userCenter =$("#userCenter").text();
    if("" == userCenter){
        window.location.href="/login";
    }
    var thisBtn = $(this);
    var productId = thisBtn.attr("productId");
    $.ajax({
        url:"/buyer/ajax/addFavoriteProduct",
        type:'POST',
        data:{'productId':productId},
        success:function (result) {
            //判断当收藏成功时切换取消收藏按钮
            if (100 == result.code) {
                //这里必须在ajax的回掉函数外面就用变量thisBtn代替$(this),否则实现不了功能;
                thisBtn.removeClass("btn_collectProduct").addClass("btn_cancelCollectProduct");
                thisBtn.empty();
                thisBtn.append("<span class='glyphicon glyphicon-heart'></span>");
                thisBtn.append(" 取消收藏")
            }
            alert(result.msg);
        }
    });
})

//取消收藏商品
$("#shopListRow").on('click','.btn_cancelCollectProduct',function () {
    var userCenter =$("#userCenter").text();
    if("" == userCenter){
        window.location.href="/login";
    }
    var thisBtn = $(this);
    var productId = thisBtn.attr("productId");
    $.ajax({
        url:"/buyer/ajax/removeFavoriteProduct",
        type:'POST',
        data:{'productId':productId},
        success:function (result) {
            //判断当收藏成功时切换取消收藏按钮
            if (100 == result.code) {
                //这里必须在ajax的回掉函数外面就用变量thisBtn代替$(this),否则实现不了功能;
                thisBtn.removeClass("btn_cancelCollectProduct").addClass("btn_collectProduct");
                thisBtn.empty();
                thisBtn.append("<span class='glyphicon glyphicon-heart-empty'></span>");
                thisBtn.append(" 收藏")
            }
            alert(result.msg);
        }
    });
})



/*分页功能部分*/
/*  当前第${pageInfo.pageNum }/${pageInfo.pages }页,当前页记录数:${pageInfo.size }条;总记录数:${pageInfo.total }条; */
function build_page_info(url,result){
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


/*批量操作的模块*/

/* 用于控制页面上的批量操作按钮组是否可用 ,此方法最好在页面发生任何事件时都调用一次,否则会出现逻辑bug,
		目前只在点击选择框时和页面加载后重新检查一次*/
function changeBatchDeleteButton(){
    var checked_length = $(".check_one:checked").length;
    if(0 == checked_length){
        $("#button_delete_batch").prop("disabled","disabled");
        $("#btn_settlement_batch").prop("disabled","disabled");
    }else{
        $("#button_delete_batch").prop("disabled","");
        $("#btn_settlement_batch").prop("disabled","");
    }
}

/* 用于控制全选按钮是否被勾选 ,此方法最好在页面发生任何事件时都调用一次,否则会出现逻辑bug,
目前只在点击选择框时重新检查一次,页面重载时不要检查,
因为此时check_one元素还没生成,解决方法时将此方法放在build_product_table方法内最下面*/
function changeAllCheckedButton(){
    var checked_length = $(".check_one:checked").length;
    var checkBox_length = $(".check_one").length;
    if(checkBox_length == checked_length){
        $(".check_all").prop("checked","checked");
    }else{
        $(".check_all").prop("checked","");
    }
}

/* 全选和全不选的功能 */
$(".check_all").click(function(){
    /* .attr()方法只能适用用自定义的值,prop方法适用于原生的属性 */
    $(".check_one").prop("checked",$(this).prop("checked"));
    changeBatchDeleteButton();
});

/* 当检测到手动点满所有单个选择按钮时,则自动勾选上全选按钮 */
$(document).on("click",".check_one",function(){
    /* .check_one:checked 是一类筛选器,筛选所有属性值有checked的check_one元素   .length代表筛选出来的个数*/
    /* 检查选中的个数是否等于页面上所有选择框的总数 */
    changeBatchDeleteButton();
    changeAllCheckedButton();
});

/*批量删除商品 */
$(document).on("click","#button_delete_batch",function(){
    /* 注意这里的变量初始化时一定要设置为空字符,否则就会是默认保存了一个undefined */
    var products = "";
    var productIds ="";
    $.each($(".check_one:checked"),function(){
        products += $(this).parent().parent().prevAll("h1").text() +",";
        productIds += $(this).attr("productId")+",";
    });
    products = products.substring(0,products.length-1);
    productIds = productIds.substring(0,products.length-1);
    if(confirm("确定要删除选中的 : "+ products+" 商品吗?")){
        $.ajax({
            url:"/buyer/ajax/removeProductFromCartBatch",
            type:"POST",
            data:{"productIds":productIds},
            success:function(result){
                alert(result.msg);
                to_page("/buyer/ajax/getCartList",currentPage);
            }
        });
    }
});

/*点击按钮后批量结算商品 */
$(document).on("click","#btn_settlement_batch",function(){
    /* 注意这里的变量初始化时一定要设置为空字符,否则就会是默认保存了一个undefined */
    var products = "";
    var productIds ="";
    $.each($(".check_one:checked"),function(){
        products += $(this).parent().parent().prevAll("h1").text() +",";
        productIds += $(this).attr("productId")+",";
    });
    products = products.substring(0,products.length-1);
    productIds = productIds.substring(0,products.length-1);
    if(confirm("确定要结算选中的 : "+ products+" 商品吗?")){
        alert("支付功能还在开发中!");
    }
});







