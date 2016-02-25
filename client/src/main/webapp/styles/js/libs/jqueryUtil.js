(function ($)
{
    //全局系统对象
    window['jqueryUtil'] = {};
    //修改ajax默认设置

    /**
     * @author 孙宇
     *
     * @requires jQuery,EasyUI
     *
     * 扩展treegrid，使其支持平滑数据格式
     */
    $.fn.treegrid.defaults.loadFilter = function(data, parentId) {
        var opt = $(this).data().treegrid.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };

    /**
     * @author 夏悸
     *
     * @requires jQuery,EasyUI
     *
     * 扩展tree，使其支持平滑数据格式
     */
    $.fn.tree.defaults.loadFilter = function(data, parent) {
        var opt = $(this).data().tree.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };
    /**
     * @author sy
     *
     * @requires jQuery,EasyUI
     *
     * 扩展combotree，使其支持平滑数据格式
     */
    $.fn.combotree.defaults.loadFilter = function(data, parent) {
        var opt = $(this).data().tree.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };


    //高级查询
    jqueryUtil.gradeSearch=function($dg,formId,url) {
        $("<div/>").dialog({
            href : url,
            modal : true,
            title : '高级查询',
            top : 120,
            width : 480,
            buttons : [ {
                text : '增加一行',
                iconCls : 'icon-add',
                handler : function() {
                    var currObj = $(this).closest('.panel').find('table');
                    currObj.find('tr:last').clone().appendTo(currObj);
                    currObj.find('tr:last a').show();
                }
            }, {
                text : '确定',
                iconCls : 'icon-ok',
                handler : function() {
                    $dg.datagrid('reload',jqueryUtil.serializeObject($(formId)));
                }
            }, {
                text : '取消',
                iconCls : 'icon-cancel',
                handler : function() {
                    $(this).closest('.window-body').dialog('destroy');
                }
            } ],
            onClose : function() {
                $(this).dialog('destroy');
            }
        });
    };

})(jQuery);