/*
* 自定义的js代码,用于实现前后端之间的功能交互
*
* */



/*点击按钮注册店铺的事件*/
$("#submit_RegisterUser").click(function(){
    /* 检查输入框是否符合正则表达式 */
    if (!validateInput("#username","#password","#passwordCheck","#petname","#submit_RegisterUser","#email","#phone")) {
        return false;
    }

    /* 检查ajax校验用户名是否可用后的标记 */
    if($(this).attr("ajaxCheckUsername") == "error" || $(this).attr("ajaxCheckPetname") == "error"){
        return false;
    }

    /*因为涉及到文件的处理,无法直接用form封装到pojo中,所以先使用js代码来封装数据
    * 这里采用本地账号和用户信息同时注册的方式,需要用到两个pojo封装*/
    var localAuth={};
        localAuth.username = $("#username").val();
        localAuth.password= $("#password").val();

    var personInfo ={};
        personInfo.name=$("#petname").val();
        personInfo.email=$("#email").val();
        personInfo.gender=$("input:checked").val();


    var formData = new FormData();
    formData.append("localAuth",JSON.stringify(localAuth));
    formData.append("personInfo",JSON.stringify(personInfo));

    $.ajax({
        url:"/common/ajax/registerUser",
        type:"POST",
        data:formData,
        async: false,
        contentType:false,
        processData:false,
        cache:false,
        success:function(result){
            alert(result.msg);
            /* 这里要检查一下后端是否返回了错误报告信息 */

                /* 判断从后台返回的错误字段是哪个,如果有,则显示错误信息 */


        }
    });

});


/* 抽取出来的输入框的前端校验 */
function validateInput(usernameEle,passwordEle,passwordCheckEle,petnameEle,ajaxEle,emailEle,phoneEle){

    //用户账号校验;
    checkUsernameInput(usernameEle,ajaxEle);

    //验证第一个密码输入框;
    checkPasswordInput(passwordEle);

    //验证第二个密码输入框;
    checkPasswordCheckInput(passwordCheckEle,passwordEle);

    //验证用户昵称输入框;
    checkPetnameInput(petnameEle,ajaxEle);

    //验证邮箱
    checkEmailInput(emailEle);

    //验证手机
    checkPhoneInput(phoneEle);

    //以上是前端输入框用户体验的校验,下面是真正的功能性校验
    //上面的校验不能中途return,否则会影响用户体验;
    //同时,以上校验不能干预提交按钮上的ajax校验标记,防止出现错误;
    var inputUsername = $(usernameEle).val();//用户账号输入框
    var regUserName = /(^[a-z0-9_-]{5,15}$)/;//账号正则表达式

    var inputPassword = $(passwordEle).val();//密码输入框
    var regPassword = /(^[a-z0-9_-]{5,15}$)/;//密码正则

    var inputValue = $(passwordCheckEle).val();//第二个密码输入框

    var inputPetName = $(petnameEle).val();//昵称输入框
    var regPetName = /(^[a-z0-9_-]{2,10}$)|(^[\u2E80-\u9FFF]{2,10})/;//昵称正则

    var inputEmail = $(emailEle).val();//邮箱输入框
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;//邮箱正则

    var inputPhone = $(phoneEle).val();//手机输入框
    var regPhone = /(^[0-9-]{5,20}$)/;//手机正则

    if(!regUserName.test(inputUsername) || !regPassword.test(inputPassword) ||
        inputValue!=inputPassword || !regPetName.test(inputPetName) ||
        !regEmail.test(inputEmail) || !regPhone.test(inputPhone)){
        return false;
    }

    return true;
}


/* 当用户更改第二个密码输入框时进行密码校验框的一致性校验,这是为了用户体验*/
$("#passwordCheck").change(function(){
    checkPasswordCheckInput("#passwordCheck","#password");
});

/* 当用户更改邮箱输入框时进行密码校验框的一致性校验,这是为了用户体验*/
$("#email").change(function(){
    checkEmailInput("#email");
});

/* 当用户更改昵称输入框时进行校验,这是为了用户体验*/
$("#petname").change(function(){
   //先检查正则,正则通过后再检查ajax,减轻服务器压力;
    checkPetnameInput("#petname","#submit_RegisterUser");
    var cls = $("#petname").next("span").attr("class");
    if ("valid-feedback" == cls){
        ajaxCheckPetnameInput("#petname","#submit_RegisterUser");
    }

});

/* 当用户更改密码输入框时进行校验,这是为了用户体验*/
$("#password").change(function(){
    checkPasswordInput("#password");
});

/* 当用户更改账号输入框时进行校验,这是为了用户体验*/
$("#username").change(function(){
    checkUsernameInput("#username","#submit_RegisterUser");
    var cls = $("#username").next("span").attr("class");
    if ("valid-feedback" == cls) {
        ajaxCheckUsernameInput("#username", "#submit_RegisterUser");
    }
});

/* 当用户更改手机输入框时进行校验,这是为了用户体验*/
$("#phone").change(function(){
    checkPhoneInput("#phone");
});



//抽取出来的用户帐号校验;
function checkUsernameInput(usernameEle,ajaxEle) {
    // 验证用户账号  /^[a-z0-9_-]{5,15}$/
    var inputUsername = $(usernameEle).val();
    var regUserName = /(^[a-z0-9_-]{5,15}$)/;
    //alert(!regName.test(inputName));

    if(!regUserName.test(inputUsername)){
        //alert("用户名格式不正确,请输入2~10位字符,只能出现数字或英文或汉字的组合!");
        showValidateInfo(usernameEle,"error","账号格式不正确,请输入5~15位字符,只能出现数字或英文或下划线的组合!");
    }else{
        showValidateInfo(usernameEle,"ok","");
    }

}

//抽取出来的ajax校验用户账号;
function ajaxCheckUsernameInput(usernameEle,ajaxEle){
    var inputValue = $(usernameEle).val();
    $.ajax({
        url:"/common/ajax/localAuth/checkUsername",
        data:"inputValue="+inputValue,
        type:"POST",
        success:function(result){
            if(200 == result.code){
                showValidateInfo("#username","ok",result.msg);
                //在按钮上添加一个信息,用于判断是否通过校验,从而决定能否提交表单;
                $(ajaxEle).attr("ajaxCheckUsername","success");
            }else{
                showValidateInfo("#username","error",result.msg);
                $(ajaxEle).attr("ajaxCheckUsername","error");
            }
        }
    });
}



//抽取出来的密码输入框校验;
function checkPasswordInput(passwordEle) {
    // 验证用户密码  /^[a-z0-9_-]{5,15}$/
    var inputPassword = $(passwordEle).val();
    var regPassword = /(^[a-z0-9_-]{5,15}$)/;
    if(!regPassword.test(inputPassword)){
        showValidateInfo(passwordEle,"error","请输入5~15位字符,只能出现数字英文或下划线的组合!");
    }else{
        showValidateInfo(passwordEle,"ok","");
    }
}

//抽取出来的用户昵称输入框的校验;
function checkPetnameInput(petnameEle,ajaxEle) {
    // 验证用户昵称  /^[a-z0-9_-]{2,10}$)|(^[\u2E80-\u9FFF]{2,10}/
    var inputPetName = $(petnameEle).val();
    var regPetName = /(^[\u2E80-\u9FFFa-z0-9_-]{2,10}$)/;

    if(!regPetName.test(inputPetName)){
        //alert("用户名格式不正确,请输入2~10位字符,只能出现数字或英文或汉字的组合!");
        showValidateInfo(petnameEle,"error","昵称格式不正确,请输入2~10位字符,只能出现数字或英文或汉字的组合!");
    }else{
        showValidateInfo(petnameEle,"ok","");
    }

}

//抽取出来的ajax校验用户昵称;
function ajaxCheckPetnameInput(petnameEle,ajaxEle){
    var inputValue = $(petnameEle).val();
    $.ajax({
        url:"/common/ajax/personInfo/checkName",
        data:"inputValue="+inputValue,
        type:"POST",
        success:function(result){
            if(200 == result.code){
                showValidateInfo(petnameEle,"ok",result.msg);
                $(ajaxEle).attr("ajaxCheckPetname","success");
            }else{
                showValidateInfo(petnameEle,"error",result.msg);
                $(ajaxEle).attr("ajaxCheckPetname","error");
            }
        }
    });
}


//抽取出来的两个密码输入框一致性的校验方法;
function checkPasswordCheckInput(passwordCheckEle,passwordEle) {
    var inputValue = $(passwordCheckEle).val();
    var password = $(passwordEle).val();
    if(inputValue!=password){
        //alert("用户名格式不正确,请输入2~10位字符,只能出现数字或英文或汉字的组合!");
        showValidateInfo(passwordCheck,"error","两次输入的密码不一致!");
    }else{
        showValidateInfo(passwordCheck,"ok","");
    }
}

//抽取出来的验证邮箱输入框的方法
function checkEmailInput(emailEle) {
    var inputEmail = $(emailEle).val();
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if(!regEmail.test(inputEmail)){
        showValidateInfo("#email","error","邮箱格式不正确");
    }else{
        showValidateInfo("#email","ok","");
    }
}

//抽取出来的手机校验;
function checkPhoneInput(phoneEle) {
    var inputPhone = $(phoneEle).val();
    var regPhone = /(^[0-9-]{5,20}$)/;
    if(!regPhone.test(inputPhone)){
        showValidateInfo(phoneEle,"error","手机号格式不正确");
    }else{
        showValidateInfo(phoneEle,"ok","");
    }
}


/* 显示校验信息*/
function showValidateInfo(ele,status,msg){

    $(ele).removeClass("is-valid is-invalid");
    if("ok" == status){
        $(ele).addClass("is-valid");
        $(ele).next("span").removeClass("valid-feedback invalid-feedback").addClass("valid-feedback").text(msg);
    }else{
        $(ele).addClass("is-invalid");
        $(ele).next("span").removeClass("valid-feedback invalid-feedback").addClass("invalid-feedback").text(msg);
    }
}








