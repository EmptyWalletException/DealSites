/*
* 自定义的js代码,用于获取店铺内所有的商品
*
* */




/*获取商品分类信息*/
function getProductCategoryList(){
    $.ajax({
        url:"/admin/ajax/productCategory/all",
        type:"get",
        success:function(result){

            /* 这里要检查一下后端是否返回了错误报告信息 */
            if(200 == result.code){
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

//给编辑分类按钮绑定点击事件
$("#tbody4productCategoryList").on('click','.editPC',function () {
    var inputNameGroup = $(this).parents().prevAll('.nameTd4Edit').children('div').children('div');
    inputNameGroup.children('input').removeAttr('disabled');
    

    var inputLevelGroup = $(this).parents().prevAll('.levelTd4Edit').children('div').children('div');
    inputLevelGroup.children('input').removeAttr('disabled');

    //点击编辑按钮后将它后面的"删除"按钮改造成提交按钮;
    $(this).parents().children(".deletePC").removeClass("deletePC btn-danger").addClass("confirmEdit btn-primary").text("提交");

    $(this).attr("disabled",true);
});


//抽取出来的数字层级输入框校验;
function checkInputInt(inputInt) {
    var regInt = /(^[0-9-]{1,5}$)/;
    if(!regInt.test(inputInt)){
        alert('请输入1到5位的数字!');
        return false;
    }
}

//抽取出来的文字名字输入框校验;
function checkInputStr(inputStr) {
    var regStr = /(^[a-z0-9_-]{2,10}$)|(^[\u2E80-\u9FFF]{1,10})/;//昵称正则
    if(!regStr.test(inputStr)){
        alert('请输入1到10位的分类名字!');
        return false;
    }
}

/*点击增加商品按钮后的行为*/
$("#addProductCategroy").click(function () {
    var name = $("#tempProductCateGoryName").val();
    var priority = $("#tempPriority").val();
    //校验两个输入框;
    checkInputStr(name);
    checkInputInt(priority);
    $.ajax({
        url:"/admin/ajax/productCategory/add",
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
    //校验两个输入框;
    checkInputStr(productCategoryName);
    checkInputInt(productCategoryLevel);
    //点击提交按钮后将它改造成"删除"按钮;然后恢复"编辑"按钮;
    $(this).parents().children(".confirmEdit").removeClass("confirmEdit btn-primary").addClass("deletePC btn-danger").text("删除");
   // $(this).parents().children(".editPC").attr("disabled",false);
    $.ajax({
        url:"/admin/ajax/productCategory/update",
        type:"POST",
        data:{'productCategoryId':productCategoryId,'productCategoryName':productCategoryName,'productCategoryLevel':productCategoryLevel},
        success:function(result){
            alert(result.msg);
            getProductCategoryList();
        }
    });
})

//删除分类;
$("#tbody4productCategoryList").on('click','.deletePC',function () {
    var productCategoryId = $(this).attr('productCategoryId');
    $.ajax({
        url:"/admin/ajax/productCategory/delete",
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

