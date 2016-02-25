/**
 * Created by Vincent on 2015/1/21.
 */


function highlight_error(el) {
    if(el.val() == '') {
        el.parent().addClass('has-error');
    } else {
        el.parent().removeClass('has-error');
    }
}

function randomKey(){
    $("#Kaptcha").attr("src", WEB_GLOBAL_CTX+"/captcha/captchaing?" + Math.floor(Math.random() * 100)).fadeIn();
}


//c
function doMsg(msg,type)
{
    var typeMsg = '';
    switch(type)
    {
        case 1:
            typeMsg="alert-success";
            break;
        case 2:
            typeMsg="alert-warning";
            break;
        default:
            typeMsg="alert-info";
    }
    var divInfo = '<div class="alert '+typeMsg+'" role="alert" id="msg">'+
        '<button type="button" class="close" data-dismiss="alert">'+
        '<span aria-hidden="true">×</span>'+
        '<span class="sr-only">Close</span>'+
        '</button>'+
        '<h4>'+
        '<strong>'+msg+'</strong>'+
        '</h4>'+
        '</div>';
    $("#hiddenMsg").append(divInfo);
}


function setMain(url, a_this) {
    var inputArray = $("#accordion .list-group a");
    inputArray.each(//使用数组的循环函数 循环这个input数组
        function () {
            var a = $(this);//循环中的每一个input元素
            if (a.hasClass("active"))
                a.removeClass('active');
        }
    )
    $(a_this).toggleClass("active");
    $('#main').attr('src', url);
}



//初始化下拉框函数
function initSelect(id, ajaxUrl, ajaxDataParam, initData, sValue, sText,isQuery) {
    //同步
    $.ajax({
        async: false,
        cache: false,
        type: 'POST',
        url: ajaxUrl,
        data: ajaxDataParam,
        error: function () {// 请求失败处理函数
            //$.scojs_message("更新失败,请重新登陆!", $ERROR);
        },
        success: function (result) {
            var options = "";
            $(result).each(function () {
                options += selectOption($(this), initData, sValue, sText);
            });
            //console.debug(options);
            $("#" + id).append(options);
            if(isQuery)
            addSearchBox(id);
        }
    });
}

function addSearchBox(id) {

    //增加搜索框
    var jObj = $('#' + id)
        .selectpicker({
            liveSearch: true
        });
    //使用移动选择
    if (/Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent)) {
        jObj.selectpicker('mobile');
    }
}
//下拉框选中及产生
//3,4

function selectOption(obj, initData, sValue, sText) {
    var text = (sText != undefined && sText != null && sText != "") ? sText : "name";
    var value = (sValue != undefined && sValue != null && sValue != "") ? sValue : "id";
    var selected = "";//默认不选择
    //判断是否是已存储的选择
    if (initData != undefined && initData != null && initData != "") {
        for(var i=0;i<initData.length;i++) {
            if (initData[i] == obj[0][value]) {
                selected = "selected";
                //console.debug(initData[i]+"==="+obj[0][value]);
                break;
            }
        }
    }
    return "<option " + selected + " value='" + obj[0][value] + "'>" + obj[0][text] + "</option>\n";
}



//初始化fuelue树
function initTree(ajaxUrl, ajaxDataParam, initData) {
    //同步
    var temp_tree_data = {data:[]};
    $.ajax({
        async: false,
        cache: false,
        type: 'POST',
        url: ajaxUrl,
        data: ajaxDataParam,
        error: function () {// 请求失败处理函数
            //$.scojs_message("更新失败,请重新登陆!", $ERROR);
        },
        success: function (result) {
            $(result.data).each(function () {
                var t = selectTreeOption(this,initData);
                temp_tree_data.data.push(t);
            });
            //temp_tree_data = result
        }
    });
    return temp_tree_data;
}
//下拉框选中及产生
var item_data_icon = 'icon-item glyphicon fueluxicon-bullet';
var selected_item_data_icon= 'glyphicon icon-item glyphicon-ok';
var select_class_name = 'tree-selected';
function selectTreeOption(obj, initData) {
    var selected = "";//默认不选择
    //判断是否是已存储的选择
    obj.attr.dataIcon=item_data_icon;
    if (initData != undefined && initData != null && initData != "") {
        for(var i=0;i<initData.length;i++){
            if (initData[i] == obj.attr.id) {
                obj.attr.className = select_class_name;
                obj.attr.dataIcon=selected_item_data_icon;
                return obj
            }
        }
    }
    return obj;
}

function asyncTreeValue(treeId,id)
{
    var temp="";
    $($('#'+treeId).tree('selectedItems')).each(function () {
        temp+=","+this.attr.id;
    });
    $("#"+id).val(temp.substr(1,temp.length));
}


function flashTable(id,url)
{
    $('#'+id).bootstrapTable('refresh', {
        url: WEB_GLOBAL_CTX+url,
        dataType:'json'
    });
}

function serializeObject( formname )
{
    var o = {};
    var a = $("#"+formname).serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


function showEdit(obj, editDivId, appendDivId) {
    var objects = obj.bootstrapTable('getSelections');
    var temp = "";
    if (objects.length != 0 && $("#" + appendDivId + " button").children().length <= 1) {
        temp=appendDivId;
    }
    if (objects.length == 0) {
        temp = editDivId;
    }
    var buttonArray = ["modify","delete","info","force","permission","compressed"];
    $.each(buttonArray,function(i,val){
        if($("#"+val)!=undefined)
            $("#" + temp).append($("#"+val));
    });

}


//调整父级高度
function setHeight()
{
    var now = document.body.clientHeight + 300;
    $('#main', parent.document).attr("style","height:"+now+"px");
}

//调整父级高度l自定义
function setHeightSelf(height)
{
    var now = document.body.clientHeight + height;
    $('#main', parent.document).attr("style","height:"+now+"px");
}

//初始树
function meTreeInit(treeid,initData,url,multiSelect,folderSelect,rootid) {
    $('#'+treeid).tree({
        dataSource: function(parentData, callback){
            setTimeout(function () {
                rootid = (rootid==undefined||rootid==null)?1:rootid;
                var id = (parentData!=undefined&&parentData!=null&&parentData.attr!=undefined)?parentData.attr.id:rootid;
                var data = initTree(WEB_GLOBAL_CTX+url+id,"",initData);
                callback(data);
                parent.Loading.modal('hide');
            }, 200);
        },
        multiSelect: multiSelect,
        folderSelect: folderSelect
    });
}