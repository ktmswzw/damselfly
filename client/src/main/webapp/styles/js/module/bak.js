
/*
 //树形菜单
 function UrlToTree(url,idFiled,textFiled,parentField,nodes)
 {
 $.ajax({
 async: false,
 cache: false,
 type: 'GET',
 url: WEB_GLOBAL_CTX+url,
 error: function () {// 请求失败处理函数
 $.scojs_message("更新失败,请重新登陆!", $ERROR);
 },
 success: function (result) {
 var data = result;
 var i, l, treeData = [], tmpMap = [];
 for (i = 0, l = data.length; i < l; i++) {
 tmpMap[data[i][idFiled]] = data[i];
 }
 for (i = 0, l = data.length; i < l; i++) {
 if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
 if (!tmpMap[data[i][parentField]][nodes])
 tmpMap[data[i][parentField]][nodes] = [];
 data[i]["text"] = data[i][textFiled];
 tmpMap[data[i][parentField]][nodes].push(data[i]);
 } else {
 data[i]["text"] = data[i][textFiled];
 treeData.push(data[i]);
 }
 }
 $('#tree').treeview({data:treeData});
 //return ;
 }
 });
 }*/
//异步查询
/*
 var picker = roles
 .ajaxSelectPicker({
 ajax: {
 url: WEB_GLOBAL_CTX+"/console/security/user/findRole",
 data: function () {
 var params = {
 description: '{{{q}}}'
 };
 return params;
 }
 },
 locale: {
 emptyTitle: '请输入并选择，%表示所有'
 },
 preprocessData: function(data){
 var roles = [];
 var dis = false;
 var sel = false;
 for(var i = 0; i < data.length; i++){
 var curr = data[i];
 dis=(curr.id==1)?true:false;
 sel=(curr.id>3&&curr.id<8)?true:false;
 roles.push(
 {
 'value': curr.id,
 'text': curr.description,
 'data': {
 'subtext': curr.name
 },
 'disabled': dis,
 'selected':sel
 }
 );
 }
 return roles;
 },
 emptyRequest: true,
 clearOnEmpty:false,preserveSelected:true
 });



 */
/**
 * Created by Vincent on 2015/1/22.
 */
