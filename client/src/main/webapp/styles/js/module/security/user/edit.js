//加载插件
 requirejs(['jquery',,'bootstrap','fuelux','switchs','select','selectCN','validator','vb','validatorLAG','comm','form','message'],
 function ($,_) {

     //返回
     $("#back").bind("click",function(){
         window.history.go(-1);
     });

     //提示框
     var $OK = $.scojs_message.TYPE_OK;
     var $ERROR = $.scojs_message.TYPE_ERROR;


     var rolesVal='',orgIdVal='';

     if(user!=undefined&&user!=null&&user!=""&&(user.id != null )) {
         rolesVal = user.roles;
         delete user["roles"];
         //初始化页面
         meForm($('#formSubmit'), user);
         orgIdVal=user.orgId+"";
     }
     //修改页面结束

     //页面特殊要求
     $("#username").val(($("#email").val()));
     $("#email").change(function(){
         $("#username").val(($("#email").val()));
     });

     //状态插件
     $("input[type=\"checkbox\"], input[type=\"radio\"]").not("[data-switch-no-init]").bootstrapSwitch()
         .on('switchChange.bootstrapSwitch', function(event, state) {
             //console.log(state); // true | false
         });

     //初始化下拉框 //可做异步下拉框选择
     initSelect("roles", WEB_GLOBAL_CTX+"/console/security/user/findRole", {description: ''}, rolesVal.split(","), "id", "description",true);


     //动态调整ifream页面高度
     $('#myTree').on('loaded.fu.tree', function (e) {
         console.log('Loaded');
         setHeight();
     });

     //同步值
     $('#myTree').on('updated.fu.tree', function (e, selected) {
         asyncTreeValue("myTree","orgId");
     });
     $('#myTree').on('selected.fu.tree', function (e, info) {
         asyncTreeValue("myTree","orgId");
     });

    //初始树
     meTreeInit('myTree',orgIdVal.split(""),"/console/security/organization/findJsonById/",false,false,1);


     //提交
     var roles = $('#roles');
     var orgId = $('#orgId');


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
     })
         .on('success.form.fv', function (e) {
             e.preventDefault();
             var $form = $(e.target);
             var fv = $form.data('formValidation');

             if (roles.val() == '' || roles.val() == null || orgId.val() == '' || orgId.val() == null) {
                 highlight_error(roles);
                 highlight_error(orgId);
                 return false;
             } else {
                 var params = $form.serialize();
                 $.post(WEB_GLOBAL_CTX + "/console/security/user/saveUser", params, function (rsp) {
                     if (rsp.successful) {
                         $.scojs_message(rsp.msg, $OK);
                         $("#save").toggleClass("disabled");
                         setTimeout("window.location.href='" + WEB_GLOBAL_CTX + "/console/security/user/list'", 1000);
                     } else {
                         $.scojs_message(rsp.msg, $ERROR);
                     }
                 }).error(function () {
                 });
                 return true;
             }
         });

 });

