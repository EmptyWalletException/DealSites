/*
* 自定义的js代码,用于编辑商品
*
* */

/* 抽取出来的获取商品分类的方法 */
function getProductCategory(product){
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
                var  op4SC =$("<option></option>").attr("value",item.productCategoryId).append(item.productCategoryName)
               if(product.productCategoryId == item.productCategoryId){
                   op4SC.attr("selected","selected");
               }
                productCategorySel.append(op4SC);
            });
        }
    });
};

/*获取商品信息并回显*/
function getProduct(){
    /*这里需要实现取到商品的id值的代码*/
    //TO-DO
    $.ajax({
        url:"/getProduct",
        type:"get",
        success:function(result){

            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(100 == result.code){
                var product = result.extend.product;
                $("#productImg").attr("src",product.imgAddr);
                $("#productName").val(product.productName);
                $("#productDesc").val(product.productDesc);
                $("#normalPrice").val(product.normalPrice);
                getProductCategory(product);
            }else{
                alert(result.msg);
            }
        }
    });
}

$("#submit_EditProduct").click(function(){
    /* 检查输入框是否符合正则表达式 */

    /* 检查ajax校验名字是否可用后的标记 */

    /*因为涉及到文件的处理,无法直接用form封装到pojo中,所以先使用js代码来封装数据*/
    var product={};
    // product.productId= null; 需要从页面获取商品id;
    product.productName = $("#productName").val();
    product.productDesc= $("#productDesc").val();
    product.normalPrice= $("#normalPrice").val();
    product.productCategoryId= $("#productCategorySel option:selected").attr("value");

    var productImg = $("#inputFile")[0].files[0]; //需要在控制层处理一下用户没有上传文件的情况
    var formData = new FormData($("#inputFile")[0]);
    formData.append("productStr",JSON.stringify(product));
    formData.append("productImg",productImg);

    $.ajax({
        url:"/updateProduct",
        type:"POST",
        data:formData,
        async: false,
        contentType:false,
        processData:false,
        cache:false,
        success:function(result){
                alert(result.msg);
        }
    });

});

$(function () {
    getProduct();
})

