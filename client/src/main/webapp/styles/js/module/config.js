/**
 * Created by Vincent on 2015/2/4.
 */
require.config({
    baseUrl: '/styles/js/libs/',
    config: {
        moment: {
            noGlobal: true
        }
    },
    paths: {
        jquery: '../libs/jquery-1.11.2.min',
        fuelux: '../libs/fuelux',
        underscore: '../libs/underscore-min',
        bootstrap: '../libs/bootstrap.min',
        switchs: '../libs/bootstrap-switch.min',
        select: '../select/bootstrap-select.min',
        selectCN: '../select/i18n/defaults-zh_CN.min',
        table: '../table/bootstrap-table.min',
        editable: '../table/extensions/editable/bootstrap-table-editable',
        xeditable: '../table/extensions/editable/bootstrap-editable',
        tablezn: '../table/locale/bootstrap-table-zh-CN.min',
        tExport: '../table/extensions/export/bootstrap-table-export.min',
        tExportS: '../table/extensions/export/tableExport',
        base64: '../table/extensions/export/jquery.base64',
        ajaxSelect: '../select/ajax-bootstrap-select.min',
        validator: '../validator/js/formValidation.min',
        vb: '../validator/js/framework/bootstrap.min',
        message: '../../sco/js/sco.message',
        validatorLAG: '../validator/js/language/zh_CN',
        ajaxSelectCN: '../select/locale/ajax-bootstrap-select.zh_CN',
        hideShowPassword: '../libs/hideShowPassword.min',
        comm: '../module/comm',
        form: '../module/form',
        async: '../libs/async',
        newsbox: '../libs/jquery.bootstrap.newsbox',
        ie10: '../libs/ie10-viewport-bug-workaround'
    },
    shim: {
        bootstrap: { deps: ['jquery'] , exports : 'bootstrap' },
        validator: { deps: ['jquery'], exports : 'validator'},
        select: { deps: ['jquery'] , exports :'select'},
        fuelux: { deps: ['jquery'] , exports :'fuelux' },
        table: { deps: ['jquery']  , exports :'table'},
        editable: { deps: ['jquery']  , exports :'editable'},
        xeditable: { deps: ['jquery']  , exports :'xeditable'},
        tExportS: { deps: ['jquery'] , exports :'tExportS' },
        base64: { deps: ['jquery']  , exports :'base64'},
        tablezn: { deps: ['table'] , exports :'tablezn' },
        tExport: { deps: ['table','tExportS','base64'] , exports :'tExport' },
        vb: { deps: ['validator'] , exports :'vb' },
        message: { deps: ['jquery'] , exports :'message' },
        selectCN: { deps: ['jquery','select']  , exports :'selectCN'},
        validatorLAG: { deps: ['validator']  , exports :'validatorLAG'},
        comm: { deps: ['jquery']  , exports :'comm'},
        form: { deps: ['jquery']  , exports :'form'}
    }
});