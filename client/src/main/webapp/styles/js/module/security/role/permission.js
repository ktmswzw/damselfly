/**
 * Created by Vincent on 2015/3/2.
 */
//加载插件
requirejs(['jquery', , 'bootstrap', 'fuelux', 'select', 'switchs','selectCN', 'validator', 'vb', 'validatorLAG', 'comm', 'form', 'message'],
    function ($) {

        //返回
        $("#back").bind("click", function () {
            window.history.go(-1);
        });

        //提示框
        var $OK = $.scojs_message.TYPE_OK;
        var $ERROR = $.scojs_message.TYPE_ERROR;

        var operation = [{"name":"show","value":"查"},{"name":"save","value":"增"},{"name":"edit","value":"改"},{"name":"delete","value":"删"}];

        var $ma=$("#accordion");

        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: WEB_GLOBAL_CTX + '/console/index/getMenuModule',
            error: function () {// 请求失败处理函数
                $.scojs_message("查询数据失败,请重新登陆!", $ERROR);
            },
            success: function (rsp) {
                initP(rsp);
            }
        });


        function initP(rsp)
        {
            if(rsp.children && rsp.children != ''){
                $.each(rsp.children,function(i,e){

                    var id_i = "div_"+i;

                    var menulist='<div class="panel panel-default"><div class="panel-heading" role="tab" id="collapseListGroupHeading'+i
                    +'"><div class="panel-title"><a class="collapsed" style="text-decoration-line: none" onclick="setHeightSelf(800);" name="titleLink"'+i+' data-parent="#accordion" data-toggle="collapse" href="#collapseListGroup'+i
                    +'" aria-expanded="false" aria-controls="collapseListGroup'+ i
                    +'"><span class="'+e.className
                    +'"></span>&nbsp;<span class="text" style="font-size: small">'+ e.name
                    +'</span></a></div></div><div id="collapseListGroup'+i
                    +'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseListGroupHeading'+i
                    +'"><div class="list-group">';

                    menulist +="<div id="+id_i+">";
                    menulist+="<div class='row'>" +
                    "<div class='col-xs-2 col-xs-offset-4'><input type='checkbox' class='me' data-size='mini' data-on-color='success' data-off-color='warning' id='show' name='"+id_i+"'>【查】</input></div>" +
                    "<div class='col-xs-2'><input type='checkbox' class='me' data-size='mini' data-on-color='success' data-off-color='warning' id='save' name='"+id_i+"'>【增】</input></div>" +
                    "<div class='col-xs-2'><input type='checkbox' class='me' data-size='mini' data-on-color='success' data-off-color='warning' id='edit' name='"+id_i+"'>【改】</input></div>" +
                    "<div class='col-xs-2'><input type='checkbox' class='me' data-size='mini' data-on-color='success' data-off-color='warning' id='delete' name='"+id_i+"'>【删】</input></div></div>";


                    menulist+="<div class='row'><div class='col-xs-2 col-xs-offset-2'><span class='badge "+ e.className+"'>"+e.name+"</span></div>";
                    for (var thisO in operation)
                    {
                        menulist+= '<div class="col-xs-2"><input type="checkbox" data-size="mini" data-on-color="success" data-off-color="warning" id="'+operation[thisO].name+'_'+e.id+'" value="'+operation[thisO].name+'_'+e.id+'_'+operation[thisO].name+'"></input></div>';
                    }
                    menulist+="</div>";

                    if(e.children && e.children.length>0){
                        $.each(e.children,function(ci,ce){
                            menulist+="<div class='row switch'><div class='col-xs-2 col-xs-offset-2'><span class='badge "+ ce.className+"'>"+ce.name+"</span></div>";
                            for (var thisO in operation)
                            {
                                menulist+= '<div class="col-xs-2"><input type="checkbox" data-size="mini" data-on-color="success" data-off-color="warning" id="'+operation[thisO].name+'_'+ce.id+'" value="'+operation[thisO].name+'_'+ce.id+'_'+operation[thisO].name+'"></input></div>';
                            }
                            menulist+="</div>";
                        });
                    }
                    menulist+="</div></div></div></div>";
                    //console.debug(menulist);
                    $ma.append(menulist);
                    //return false;
                });
                for (var thisO in permissionMe)
                {
                    var nows = "#"+permissionMe[thisO].shortName+"_"+permissionMe[thisO].moduleId;
                    $(nows).attr('checked',true);
                    $(nows).bootstrapSwitch('state', true);
                }

                //状态插件
                $("input[type=\"checkbox\"], input[type=\"radio\"]").not("[data-switch-no-init]").bootstrapSwitch()
                    .on('switchChange.bootstrapSwitch', function(e, data) {
                        if($(this).hasClass("me")){
                            var me = $(this)[0];
                            selectAll(me.name,me.id,data);
                        }
                    });

            }else{
                $.scojs_message("暂无菜单数据,请初始化菜单!", $ERROR);
            }
        }

        parent.Loading.modal('hide');
        //修改页面结束

        //页面特殊要求

        //初始化下拉框 //可做异步下拉框选择


        //提交

        $('#formSubmit').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            locale: 'zh_CN',
            excluded: ':disabled'
        }).on('success.field.fv', function (e, data) {
            if (data.fv.getInvalidFields().length > 0) {    // There is invalid field
                data.fv.disableSubmitButtons(true);
            }
        }).on('success.form.fv', function (e) {
            e.preventDefault();
            var $form = $(e.target);
            var fv = $form.data('formValidation');

            var chk_value =[];//定义一个数组
            $('input[id*="_"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数
                if($(this).val().length!=0)
                    chk_value.push($(this).val());//将选中的值添加到数组chk_value中
            });
            $("#rolePermission").val(chk_value);

            $.post(WEB_GLOBAL_CTX + "/console/security/role/saveRolePermission/"+$("#roleId").val()+"?rolePermissionStr="+$("#rolePermission").val() ,  function (rsp) {
                if (rsp.successful) {
                    $.scojs_message(rsp.msg, $OK);
                    $("#save").toggleClass("disabled");
                    setTimeout("window.location.href='" + WEB_GLOBAL_CTX + "/console/security/role/list'", 1000);
                } else {
                    $.scojs_message(rsp.msg, $ERROR);
                }
            }).error(function () {
            });
            return true;
        });



//全选某div下数据
        function selectAll(divname,type,data)
        {
            var inputArray = $("#"+divname+" input[id*='"+type+"']");

            inputArray.each(//使用数组的循环函数 循环这个input数组
                function () {
                    if(!$(this).hasClass("me")) {
                        $(this).attr("checked", data);
                        $(this).bootstrapSwitch('state', data);
                    }
                }
            );
        }

    });