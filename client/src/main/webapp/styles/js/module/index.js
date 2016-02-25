/**
 * Created by vincent on 2014/12/10.
 */
var Loading;

requirejs(['jquery'],
    function () {
        requirejs(['bootstrap', 'ie10','comm','message'],
            function () {

                //提示框
                var $OK = $.scojs_message.TYPE_OK;
                var $ERROR = $.scojs_message.TYPE_ERROR;


                Loading = $('#myModal').modal({
                    show:false,
                    backdrop:true,
                    keyboard: false
                });
                $('[data-toggle="offcanvas"]').click(function () {
                    $('.row-offcanvas').toggleClass('active')
                    $('#left_memu').toggleClass('glyphicon-chevron-left');
                    $('#left_memu').toggleClass('glyphicon-chevron-right');
                });
                $('[data-toggle="collapse"]').click(function () {
                    $('#top_memu').toggleClass('glyphicon glyphicon-chevron-up');
                    $('#top_memu').toggleClass('glyphicon glyphicon-chevron-down');
                });

                $(window).resize(function () {
                    $(window).scroll(function () {
                        if ($(window).width() >= 751) {
                            if ($(window).scrollTop() > 50) {
                                if ($("#topNavBar").hasClass('resizeScroll'))
                                    $('#topNavBar').removeClass('resizeScroll');
                            }
                            else {
                                if (!$("#topNavBar").hasClass('resizeScroll'))
                                    $('#topNavBar').addClass('resizeScroll');
                            }
                        }
                    });
                });

                var temp  = getModuleString(1);
                $("#accordion").append(temp);
                Loading.modal('hide');
            });

    });


//初始化菜单
function getModuleList(id) {
    //同步
    var obj = {};
    $.ajax({
        async: false,
        cache: false,
        type: 'GET',
        url: WEB_GLOBAL_CTX + "/console/security/module/getModuleByP/"+id,
        error: function () {// 请求失败处理函数
            //$.scojs_message("更新失败,请重新登陆!", $ERROR);
        },
        success: function (result) {
            obj = result;
        }
    });
    return obj;
}

//初始化第一菜单
function getModuleString(id) {
    var temp = "";
    var firstModuleGroup ={};
    var secondModule ={};
    firstModuleGroup = getModuleList(id);
    $(firstModuleGroup).each(function () {
        temp+='<div class="panel panel-default"><div class="panel-heading" role="tab" id="collapseListGroupHeading'+this.id
        +'"><div class="panel-title"><a class="collapsed" style="text-decoration-line: none" data-parent="#accordion" data-toggle="collapse" href="#collapseListGroup'+this.id
        +'" aria-expanded="false" aria-controls="collapseListGroup'+this.id
        +'"><span class="'+this.className
        +'"></span>&nbsp;<span class="text" style="font-size: small">'+this.name
        +'</span></a></div></div><div id="collapseListGroup'+this.id
        +'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseListGroupHeading'+this.id
        +'"><div class="list-group">';
        secondModule = getModuleList(this.id);
        $(secondModule).each(function () {
            temp+='<a href="javascript:void(0);" class="list-group-item" onclick="setMain(\''+WEB_GLOBAL_CTX+this.url
            +'\');"><span class="'+this.className
            +'"></span>&nbsp;<span class="text">'+this.name
            +'</span></a>'
        });
        temp+='</div></div></div>';
    });
    return temp;
}

