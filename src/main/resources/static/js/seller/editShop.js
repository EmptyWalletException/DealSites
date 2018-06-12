/*
* 自定义的js代码,用于编辑店铺
*
* */


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



