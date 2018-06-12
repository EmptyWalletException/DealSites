/*
* 自定义的js代码,用于编辑商品
*
* */

/* 抽取出来的获取商品分类的方法 */
function getProductCategory(){
    /* alert("进入js代码");*/
    $.ajax({
        url:"/ajax/productCategory",
        type:"get",
        success:function(result){
            /*    alert("进入ajax代码");*/
            var productCategorySel =$("#productCategorySel");
            var categoryArray = result.extend.productCategoryList;
            productCategorySel.empty();
            $.each(categoryArray,function (index,item) {
                var  op4SC =$("<option></option>").attr("value",item.productCategoryId).append(item.productCategoryName);
                productCategorySel.append(op4SC);
            });
        }
    });
};



$("#submit_addProduct").click(function(){
    /* 检查输入框是否符合正则表达式 */

    /* 检查ajax校验名字是否可用后的标记 */

    /*因为涉及到文件的处理,无法直接用form封装到pojo中,所以先使用js代码来封装数据*/
    var product={};
    product.productName = $("#productName").val();
    product.productDesc= $("#productDesc").val();
    product.normalPrice= $("#normalPrice").val();
    product.productCategoryId= $("#productCategorySel option:selected").attr("value");

    var productImg = $("#inputFile")[0].files[0]; //需要在控制层处理一下用户没有上传文件的情况
    var formData = new FormData($("#inputFile")[0]);
    formData.append("productStr",JSON.stringify(product));
    formData.append("productImg",productImg);

    $.ajax({
        url:"/addProduct",
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
    getProductCategory();
})

