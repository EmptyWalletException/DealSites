/*
* 自定义的js代码,用于获取店铺内所有的商品
*
* */




/*获取商品分类信息*/
function getProductCategoryList(){
    $.ajax({
        url:"/ajax/productCategory",
        type:"get",
        success:function(result){

            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(100 == result.code){
                var productCategoryList = result.extend.productCategoryList;
                $("#tbody4productCategoryList").empty();
                $.each(productCategoryList,function (index,productCategory) {
                    var createTime = new Date(productCategory.createTime);

                    $("#tbody4productCategoryList").append(
                        "<tr>"+
                        /*商品名字*/
                        "<td class='nameTd4Edit'>" +
                        "<div class=\"col-lg-6\">\n" +
                        "    <div class=\"input-group\">\n" +
                        "      <input type=\"text\" class=\"form-control\" value=\""+productCategory.productCategoryName+"\" disabled>" +
                        "      <span class=\"input-group-btn\">\n" +
                        "      </span>\n" +
                        "    </div>" +
                        " </div>" +
                        " </td>" +
                        
                        /*商品层级*/
                        "<td class='levelTd4Edit'>" +
                        "<div class=\"col-lg-6\">\n" +
                        "    <div class=\"input-group\">\n" +
                        "      <input type=\"text\" class=\"form-control\" value=\""+productCategory.priority+"\" disabled>" +
                        "      <span class=\"input-group-btn\">\n" +
                        "      </span>\n" +
                        "    </div>" +
                        " </div>" +
                        " </td>" +

                        "<td>"+createTime.toLocaleDateString()+"</td>"+
                        "<td class='editAndDeleteButton'>"+
                        "<button  class=\"btn btn-primary editPC\" productCategoryId='"+productCategory.productCategoryId+"'>编辑</button>"+
                        "<button  class=\"btn btn-danger  deletePC\" productCategoryId='"+productCategory.productCategoryId+"'>删除</button>"+
                        "</td>"+
                        "</tr>"
                    )
                })
            }else{
                alert(result.msg);
            }
        }
    });
}

$("#tbody4productCategoryList").on('click','.editPC',function () {
    var inputNameGroup = $(this).parents().prevAll('.nameTd4Edit').children('div').children('div');
    inputNameGroup.children('input').removeAttr('disabled');
    

    var inputLevelGroup = $(this).parents().prevAll('.levelTd4Edit').children('div').children('div');
    inputLevelGroup.children('input').removeAttr('disabled');

    //点击编辑按钮后将它后面的"删除"按钮改造成提交按钮;
    $(this).parents().children(".deletePC").removeClass("deletePC btn-danger").addClass("confirmEdit btn-primary").text("提交");

    $(this).attr("disabled",true);
});

/*点击增加商品按钮后的行为*/
$("#addProductCategroy").click(function () {
    var name = $("#tempProductCateGoryName").val();
    var priority = $("#tempPriority").val();
    $.ajax({
        url:"/ajax/addProductCategory",
        type:"POST",
        data:{'productCategoryName':name,'priority':priority},
        success:function(result){
            alert(result.msg);
            getProductCategoryList();
        }
    });
});


/*点击提交按钮后的行为*/
$("#tbody4productCategoryList").on('click','.confirmEdit',function () {
    var productCategoryId = $(this).attr("productCategoryId");
    var productCategoryName =$(this).parents().prevAll(".nameTd4Edit").children('div').children('div').children('input').val();
    var productCategoryLevel =$(this).parents().prevAll(".levelTd4Edit").children('div').children('div').children('input').val();
    //点击提交按钮后将它改造成"删除"按钮;然后恢复"编辑"按钮;
    $(this).parents().children(".confirmEdit").removeClass("confirmEdit btn-primary").addClass("deletePC btn-danger").text("删除");
   // $(this).parents().children(".editPC").attr("disabled",false);
    $.ajax({
        url:"/ajax/editProductCategory",
        type:"POST",
        data:{'productCategoryId':productCategoryId,'productCategoryName':productCategoryName,'productCategoryLevel':productCategoryLevel},
        success:function(result){
            alert(result.msg);
            getProductCategoryList();
        }
    });
})

$("#tbody4productCategoryList").on('click','.deletePC',function () {
    var productCategoryId = $(this).attr('productCategoryId');
    $.ajax({
        url:"/ajax/deleteProductCategory",
        type:"POST",
        data:{productCategoryId:productCategoryId},
        success:function(result){
            alert(result.msg);
            getProductCategoryList();
        }
    });
});

$(function () {
    getProductCategoryList();

})

