/**
 * Created by Vincent on 2015/2/14.
 */
requirejs(['jquery', 'bootstrap', 'table', 'tablezn', 'tExport', 'tExportS', 'base64', 'comm', 'message'],
    function () {

        //导出编码
        $.base64.utf8encode = true;
        var $OK = $.scojs_message.TYPE_OK;
        var $ERROR = $.scojs_message.TYPE_ERROR;

        //列表
        var $table = $('#tableB').bootstrapTable({
            url: WEB_GLOBAL_CTX + '/console/security/module/getModuleList',
            dataType: 'json',
            cache:false,
            showToggle:true,
            showExport:true,
            showRefresh:true,
            showColumns:true,
            exportTypes:"['doc', 'excel']",
            toolbar:'#custom-toolbar',
            toolbarAlign:'left',
            sidePagination:'server',
            clickToSelect:true,
            singleSelect:true,
            smartDisplay: false,
            queryParams: 'queryParamsF',
            pagination: true,
            pageSize: 5,
            pageList: [5, 10, 20, 100]
        }).on('check.bs.table', function (e, row) {
            showEdit($table, 'to', 'do', 'in');
        }).on('uncheck.bs.table', function (e, row) {
            showEdit($table, 'to', 'do', 'in');
        }).on('page-change.bs.table', function (e, size, number) {
            setHeightSelf(200*number/10);
        });

        //查询动作
        $('#query').click(function () {
            $table.bootstrapTable('refresh', {
                url: WEB_GLOBAL_CTX + '/console/security/module/getModuleList',
                queryParams: 'queryParamsF'
            });
        });

        //删除
        $('#delete').click(function () {
            var objects = $table.bootstrapTable('getSelections');
            console.debug('Selected values: ' + objects.length);
            $.each(objects, function () {
                $.post(WEB_GLOBAL_CTX + "/console/security/module/deleteInfo/" + this.id, function (rsp) {
                    if (rsp.successful) {
                        $.scojs_message(rsp.msg, $OK);
                        flashTable('tableB', '/console/security/module/getModuleList');
                    } else {
                        $.scojs_message(rsp.msg, $ERROR);
                    }
                }).error(function () {
                    $.scojs_message("更新失败,请重新登陆!", $ERROR);
                });
            });
        });

        //添加
        $('#add').click(function () {
            parent.Loading.modal('show');
            self.location = WEB_GLOBAL_CTX + "/console/security/module/add";
        });

        //修改
        $('#modify').click(function () {
            parent.Loading.modal('show');
            var objects = $table.bootstrapTable('getSelections');
            $.each(objects, function () {
                self.location = WEB_GLOBAL_CTX + "/console/security/module/edit/"+this.id;
            });

        });

        parent.Loading.modal('hide');

    });


//本页查询拼装
function queryParamsF(params) {
    var name = $("#search_select").val();
    var value = $("#search").val();
    var str = "{\"" + name + "\":\"" + value + "\"}";
    var data = eval('(' + str + ')');
    params.sortName = "parentId,id";
    params.sortOrder = "asc";
    return $.extend({}, params, data);
}

function priorityFormatter(value, row, index){
    value = 100 - value;
    var temp = '<div class="progress pgme">'+
        '<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="'+value+'" aria-valuemin="0" aria-valuemax="100" style="width: '+value+'%">'+
        ''+value+'</div></div>';
    return temp;
}