/*
* 自定义的js代码,用于编辑店铺
*
* */


/* 抽取出来的获取区域分类的方法 */
function getArea(shop){
    /* alert("进入js代码");*/
    $.ajax({
        url:"/ajax/Area",
        type:"get",
        success:function(result){
            /*    alert("进入ajax代码");*/
            var areaSel =$("#areaSel");
            var areaArray = result.extend.areas;
            areaSel.empty();
            $.each(areaArray,function (index,item) {
                var op4Area =$("<option></option>").attr("value",item.areaId).append(item.areaName);
                if(shop.shopCategoryId == item.areaId){
                    op4Area.attr("selected","selected");
                }
                 areaSel.append(op4Area);
            });
        }
    });
    /*  alert("执行完异步请求js代码");*/
};

/*获取店铺信息并回显*/
function getShop(){
    /*这里需要实现取到shop的id值的代码*/
    //TO-DO
    $.ajax({
        url:"/getShop",
        type:"GET",
        success:function(result){

            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(100 == result.code){

                var shop = result.extend.shop;
                $("#shopImg").attr("src","../../"+shop.shopImg);
                $("#shopName").val(shop.shopName);
                $("#shopDesc").val(shop.shopDesc);
                $("#phone").val(shop.phone);
                $("#shopAddr").val(shop.shopAddr);

                getArea(shop);
            }else{
                alert(result.msg);
            }
        }
    });
}

$("#submit_EditShop").click(function(){
    /* 检查输入框是否符合正则表达式 */

    /* 检查ajax校验名字是否可用后的标记 */

    /*因为涉及到文件的处理,无法直接用form封装到pojo中,所以先使用js代码来封装数据*/
    var shop={};
    // shop.shopId= null; 需要从页面获取店铺id;
    shop.shopName = $("#shopName").val();
    shop.shopDesc= $("#shopDesc").val();
    shop.phone= $("#phone").val();
    shop.shopAddr= $("#shopAddr").val();
    shop.areaId= $("#areaSel option:selected").attr("value");

    var shopImg = $("#inputFile")[0].files[0]; //需要在控制层处理一下用户没有上传文件的情况
    var formData = new FormData($("#inputFile")[0]);
    formData.append("shopStr",JSON.stringify(shop));
    formData.append("shopImg",shopImg);
    formData.append("test","test");

    $.ajax({
        url:"/updateShop",
        type:"POST",
        data:formData,
        async: false,
        contentType:false,
        processData:false,
        cache:false,
        success:function(result){
            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(100 == result.code){
                alert(result.msg);
            }else{
                alert(result.msg);
            }
        }
    });

});

$(function () {
    getShop();
})

