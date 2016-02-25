/**
 * Created by v on 2015/8/20.
 */
//加载插件
requirejs(['jquery','bootstrap','fuelux','validator','vb','validatorLAG','comm','form','message'],
    function ($,_) {

        //返回
        $("#back").bind("click",function(){
            window.history.go(-1);
        });

        //提示框
        var $OK = $.scojs_message.TYPE_OK;
        var $ERROR = $.scojs_message.TYPE_ERROR;


        var parentIdVal='';

        if(opportunities!=undefined&&opportunities!=null&&opportunities!=""&&(opportunities.opportunitiesId != null )) {
            //初始化页面
            meForm($('#formSubmit'), opportunities);
        }
        //修改页面结束

        //页面特殊要求


        //提交
        //var roles = $('#roles');

        $('#formSubmit').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            locale: 'zh_CN',
            excluded: ':disabled'
        }).on('success.field.fv', function(e, data) {
            if (data.fv.getInvalidFields().length > 0) {    // There is invalid field
                data.fv.disableSubmitButtons(true);
            }
        }).on('success.form.fv', function (e) {
            e.preventDefault();
            var $form = $(e.target);
            var fv = $form.data('formValidation');
            var params = $form.serialize();
            $.post(WEB_GLOBAL_CTX + "/business/opportunities/saveEdit", params, function (rsp) {
                if (rsp.successful) {
                    $.scojs_message(rsp.msg, $OK);
                    $("#save").toggleClass("disabled");
                    setTimeout("window.location.href='" + WEB_GLOBAL_CTX + "/business/opportunities/index'", 1000);
                } else {
                    $.scojs_message(rsp.msg, $ERROR);
                }
            }).error(function () {
            });
            return true;
        });

        parent.Loading.modal('hide');
    });
