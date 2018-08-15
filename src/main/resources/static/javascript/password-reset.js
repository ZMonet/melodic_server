
document.write("<script language=javascript src='http://39.105.54.174:8080/javascript/md5.js'><\/script>");
function showInfo(info){
    $(".info-box").text(info);
    $(".info-box").slideDown("normal");
    setTimeout(function () {
        $(".info-box").slideUp("normal");
    },2000);
}

$("#headerMenu").on("click",
    function () {
        if($(".line-1").hasClass("line-1-active")){
            $(".line-1").removeClass("line-1-active");
            $(".line-2").removeClass("line-2-active");
        }else{
            $(".line-1").addClass("line-1-active");
            $(".line-2").addClass("line-2-active");
        }
    });

$(".input-info_input-box_1").focus(
    function () {
        $(".input-info_1").addClass("input-info-focus");
        $(".input-hd-img_1").attr("src","http://39.105.54.174:8080/images/user_password_active.png");
        $(".input-info_input-box_1").addClass("input-info_input-box-focus")
    });

$(".input-info_input-box_1").blur(
    function () {
        $(".input-info_1").removeClass("input-info-focus");
        $(".input-hd-img_1").attr("src","http://39.105.54.174:8080/images/user_password.png");
        $(".input-info_input-box_1").removeClass("input-info_input-box-focus");
    });

$(".input-info_input-box_2").focus(
    function () {
        $(".input-info_2").addClass("input-info-focus");
        $(".input-hd-img_2").attr("src","http://39.105.54.174:8080/images/user_password_active.png");
        $(".input-info_input-box_2").addClass("input-info_input-box-focus")
    });

$(".input-info_input-box_2").blur(
    function () {
        $(".input-info_2").removeClass("input-info-focus");
        $(".input-hd-img_2").attr("src","http://39.105.54.174:8080/images/user_password.png");
        $(".input-info_input-box_2").removeClass("input-info_input-box-focus")
    });

$("#complete_button").on("click",
    function () {
        console.log("click");
        var reg = new RegExp(/^(?![^a-zA-Z]+$)(?!\D+$)/);

        if( $(".input-info_input-box_1").val() === "" ||  $(".input-info_input-box_2").val() === ""){
            showInfo("您还没有输入密码哦")
        }
        else if(!reg.test($(".input-info_input-box_1").val())){
            showInfo("您的密码不符合要求呢")
        }
        else if($(".input-info_input-box_1").val() !== $(".input-info_input-box_2").val()){
            console.log($(".input-info_input-box_1").val());
            console.log($(".input-info_input-box_2").val());
            showInfo("您两次输入的密码不相同呢")
        }
        else{
            $.ajax({
                type:"POST",
                url:"http://39.105.54.174:8080/user/resetPassword",
                headers:{
                    "authorization":$("#authorization").val()
                },
                dataType:"json",
                data:JSON.stringify({
                    "password": hex_md5($(".input-info_input-box_1").val()),
                    "userEmail": $(".user-email").text()
                }),
                contentType: "application/json;charset=utf-8",
                success:function (res) {
                    $(".container-bd-form").addClass("container-complete");
                    $(".hd-sub-info").text("密码重置成功，请在客户端上用新密码登录");
                    $(".complete-text").text("重置成功");
                    $(".complete-text").addClass("complete-text-show");
                    $(".complete-text").removeClass("complete-text-hidden");
                },
                fail:function (res) {
                    $(".container-bd-form").addClass("container-complete");
                    $(".hd-sub-info").text("密码重置成功，请返回客户端重试");
                    $(".complete-text").text("重置失败");
                    $(".complete-text").addClass("complete-text-show");
                    $(".complete-text").removeClass("complete-text-hidden");
                }
            });
        }
    });