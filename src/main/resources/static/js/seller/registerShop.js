/*
* 自定义的js代码,用于实现前后端之间的功能交互
*
* */



/* 抽取出来的获取区域分类的方法 */
function getArea(){
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
                areaSel.append($("<option></option>").attr("value",item.areaId).append(item.areaName));
            });
        }
    });
    /*  alert("执行完异步请求js代码");*/
};

/*点击按钮注册店铺的事件*/
$("#submit_RegisterShop").click(function(){
    /* 检查输入框是否符合正则表达式 */


    /* 检查ajax校验用户名是否可用后的标记 */

    /*因为涉及到文件的处理,无法直接用form封装到pojo中,所以先使用js代码来封装数据*/
    var shop={};
    shop.shopName = $("#shopName").val();
    shop.shopDesc= $("#shopDesc").val();
    shop.phone= $("#phone").val();
    shop.shopAddr= $("#shopAddr").val();
    shop.areaId= $("#areaSel option:selected").attr("value");

    var shopImg = $("#inputFile")[0].files[0];
    var formData = new FormData($("#inputFile")[0]);
    formData.append("shopStr",JSON.stringify(shop));
    formData.append("shopImg",shopImg);

    $.ajax({
        url:"/addShop",
        type:"POST",
        data:formData,
        async: false,
        contentType:false,
        processData:false,
        cache:false,
        success:function(result){
            alert(result.msg);
            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(100 == result.code){
                alert("注册成功!");
            }else{
                alert("注册失败了!");
                /* 判断从后台返回的错误字段是哪个,如果有,则显示错误信息 */

            }
        }
    });

});

$(function () {
    getArea();
})

