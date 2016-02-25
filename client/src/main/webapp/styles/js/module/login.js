/**
 * Created by Vincent on 2015/1/21.
 */

if (top != self) {
    if (top.location != self.location)
        top.location = self.location;
}

requirejs(['jquery', 'ie10', 'comm'],
    function () {
        requirejs(['bootstrap', 'switchs', 'hideShowPassword'], function () {
            $('#formLogin').submit(function (e) {
                var username = $('#username');
                var password = $('#password');
                var captcha = $('#captcha');
                if (username.val() == '' || password.val() == '' || captcha.val() == '') {
                    highlight_error(username);
                    highlight_error(password);
                    highlight_error(captcha);
                    return false;
                } else {
                    e.preventDefault();
                    var params = $("#formLogin").serialize();
                    $.ajax({
                        async: false,
                        cache: false,
                        data: params,
                        dataType: 'json',
                        type: 'POST',
                        url: WEB_GLOBAL_CTX + "/login/do",
                        error: function () {// 请求失败处理函数
                            doMsg("错误", 2);
                            return false;
                        },
                        success: function (result) {
                            if (result != null) {
                                doMsg(result.msg, result.successful ? 1 : 2);
                                if (result.successful) {
                                    $("#but_login").toggleClass("disabled");
                                    setTimeout("window.location.href='" + WEB_GLOBAL_CTX + "/console/index'", 100);
                                }
                            }
                            return true;
                        }
                    });
                }
            });

            $("#captcha").focus(function () {
                if ($("#captcha").val().length == 0)
                    randomKey();
                return false;
            });

            $("#Kaptcha, #refresh").click(function () {
                randomKey();
                return false;
            });

            $('#show-password').on('switchChange.bootstrapSwitch', function (event, state) {
                $('#password').hideShowPassword(state);
            });

            // 点击登录
            $('#but_login').click(function (e) {
                if ($("#msg") != undefined)
                    $("#msg").alert('close');
            });


            $('#username, #password, #captcha').on('keyup', function () {
                highlight_error($(this));
                /*
                 }).focus(function(){
                 highlight_error($(this));*/
            }).blur(function () {
                highlight_error($(this));
            });

            $("#rememberMe").bootstrapSwitch();
            $("#show-password").bootstrapSwitch();

            setInterval(function(){
                $.ajax({
                    async: false,
                    cache: false,
                    type: 'GET',
                    url: WEB_GLOBAL_CTX +"/captcha/zxingFlush/"+$("#salt").val(),
                    error: function () {// 请求失败处理函数

                    },
                    success: function (result2) {
                        if (result2 != null) {
                            if (result2.successful) {
                                var params = $("#formLogin").serialize();
                                $.ajax({
                                    async: false,
                                    cache: false,
                                    data: params,
                                    dataType: 'json',
                                    type: 'POST',
                                    url: WEB_GLOBAL_CTX + "/login/autoLoad",
                                    error: function () {// 请求失败处理函数
                                        return false;
                                    },
                                    success: function (result) {
                                        if (result != null) {
                                            doMsg(result.msg, result.successful ? 1 : 2);
                                            if (result.successful) {
                                                $("#but_login").toggleClass("disabled");
                                                setTimeout("window.location.href='" + WEB_GLOBAL_CTX + "/console/index'", 100);
                                            }
                                        }
                                        return true;
                                    }
                                });
                            }
                        }
                    }
                });
            },5000);

        });


    });