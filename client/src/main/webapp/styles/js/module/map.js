/**
 * Created by vincent on 2015/1/1.
 */
requirejs(['jquery', 'async'],
    function () {
        requirejs(['async!http://api.map.baidu.com/api?v=2.0&ak=1L5aqpafGW6Yu76pMGEaRCuq&callback=init'],
            function () {
                map = new BMap.Map("allmap", {mapType: BMAP_HYBRID_MAP});            // 创建Map实例

                getBoundary();

                //map = new BMap.Map("allmap");            // 创建Map实例
                var point = new BMap.Point(119.65387, 29.085603); // 创建点坐标
                map.addControl(new BMap.MapTypeControl({
                    mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP]
                }));

                myIconAn = new BMap.Icon(WEB_GLOBAL_CTX + "/styles/images/hljh.png", new BMap.Size(16, 16), {
                    anchor: new BMap.Size(8, 16)
                });

                map.setMapStyle({style: 'dark'});

                map.centerAndZoom(point, 11);
                map.enableScrollWheelZoom();                 //启用滚轮放大缩小
                opts = {
                    width: 250,     // 信息窗口宽度
                    height: 80,     // 信息窗口高度
                    title: "信息窗口", // 信息窗口标题
                    enableMessage: true//设置允许信息窗发送短息
                };

                var copyCtrl = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
                copyCtrl.addCopyright({
                    id: 1,
                    content: "<a href='http://www.JHWGX.GOV.CN' target='_blank' style='font-size:10px;color:#999'>数据版权：金华市文化广电新闻出版局</a>"
                });
                map.addControl(copyCtrl);

                requirejs(['bootstrap', 'ie10', 'newsbox', 'message'],
                    function () {
                        $(".demo1").bootstrapNews({
                            newsPerPage: 5,
                            autoplay: true,
                            pauseOnHover: true,
                            direction: 'up',
                            newsTickerInterval: 3000,
                            onToDo: function () {
                                // console.log(this);
                            }
                        });

                        if ($("#" + current) != undefined) {
                            $("#" + current).addClass("active");
                        }


                        //$("[data-toggle='tooltip']").tooltip();
                        $("[name='example']").popover();
                        $("[id='refresh']").popover();


                        $("#refresh").click(function () {
                            $("#refresh").toggleClass("disabled");
                            $.post(WEB_GLOBAL_CTX + '/flash', function (rsp) {
                                if (rsp.successful) {
                                    $.scojs_message(rsp.msg, $.scojs_message.TYPE_OK);
                                    setTimeout($("#refresh").toggleClass("disabled"), 5000);
                                } else {
                                    $.scojs_message(rsp.msg, $.scojs_message.TYPE_ERROR);
                                }
                            }).error(function () {
                                $.scojs_message('查询数据失败', $.scojs_message.TYPE_ERROR);
                            });
                        });

                    });
            });
    });




function init() {

}

function mapMove(objId) {
    for (var i = 0; i < cultureList.length; i++) {
        var obj = cultureList[i];
        if (parseInt(obj.id) == parseInt(objId)) {
            var tempPoint;
            if (obj.longitude == null) {
                // 创建地址解析器实例
                myGeo = new BMap.Geocoder();
                myGeo.getPoint(obj.venue, function (point) {
                    if (point) {
                        goto(point, obj);
                        $.post(WEB_GLOBAL_CTX + '/update/' + objId + '/' + point.lng + '/' + point.lat + '/' + objId, function (rsp) {

                        }).error(function () {
                            alert("提示" + "查询数据失败,请重新登陆!");
                        });
                    }
                    else {
                        tempPoint = new BMap.Point(119.65387, 29.085603); // 创建点坐标
                        goto(tempPoint, obj);
                    }
                }, "金华");
            }
            else {
                tempPoint = new BMap.Point(obj.longitude, obj.latitude);
                goto(tempPoint, obj);
            }
        }
    }
}

function goto(point, obj) {
    //map.centerAndZoom(point, 7);
    var markergps = new BMap.Marker(point, {icon: myIconAn});
    map.addOverlay(markergps); //添加标注
    var content = '<img src=' + WEB_GLOBAL_CTX + '"/styles/images/hljh_bg.png" width="60" class="img-circle" />' + obj.content;
    map.addOverlay(markergps);               // 将标注添加到地图中
    addClickHandler(content, markergps, obj.title);

    if (markerAnimation != undefined || markerAnimation != null)
        markerAnimation.hide();
    markerAnimation = new BMap.Marker(point);
    map.addOverlay(markerAnimation);
    markerAnimation.setAnimation(BMAP_ANIMATION_BOUNCE);
    //markerAnimation.show();
}

function addClickHandler(content, marker, title) {
    marker.addEventListener("click", function (e) {
            openInfo(content, e, title)
        }
    );
}

function openInfo(content, e, title) {
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    opts.title = title;
    var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
    map.openInfoWindow(infoWindow, point); //开启信息窗口
}

function getBoundary() {
    var citys = ["义乌-green", "东阳-#2FDFFF", "武义-#FF8A51", "永康-#FBFBFF", "磐安-black",
        "兰溪-blue", "浦江-yellow", "金华-red"];

    for (var x = 0; x < citys.length; x++) {
        var city = citys[x].split("-");
        setBoundary(city[0], city[1]);
    }
}

function setBoundary(city, color) {
    var bdary = new BMap.Boundary();
    bdary.get(city, function (rs) {       //获取行政区域
        var count = rs.boundaries.length; //行政区域的点有多少个
        for (var i = 0; i < count; i++) {
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: color}); //建立多边形覆盖物
            map.addOverlay(ply);  //添加覆盖物
            map.setViewport(ply.getPath());    //调整视野
        }
    });
}