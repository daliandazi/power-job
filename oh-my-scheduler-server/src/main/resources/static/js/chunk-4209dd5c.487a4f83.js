(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4209dd5c"],{"1ddd":function(e,t,i){"use strict";i.r(t);var s=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{attrs:{id:"welcome"}},[i("el-button",{attrs:{type:"primary",plain:""},on:{click:function(t){e.appRegisterFormVisible=!0}}},[e._v("应用注册")]),i("div",{attrs:{id:"entrance"}},[i("el-select",{attrs:{id:"appSelect",filterable:"",remote:"","reserve-keyword":"",placeholder:"请输入应用名称","remote-method":e.fetchAppNames,loading:e.loading},on:{change:e.selectedApp},model:{value:e.selectedAppInfo,callback:function(t){e.selectedAppInfo=t},expression:"selectedAppInfo"}},e._l(e.appInfoList,(function(e){return i("el-option",{key:e.id,attrs:{label:e.appName,value:e}})})),1)],1),i("el-button",{attrs:{type:"success",plain:""},on:{click:function(t){e.userRegisterFormVisible=!0}}},[e._v("用户注册")]),i("el-dialog",{attrs:{title:"应用注册",visible:e.appRegisterFormVisible,width:"35%"},on:{"update:visible":function(t){e.appRegisterFormVisible=t}}},[i("el-form",{staticStyle:{margin:"0 5px"},attrs:{model:e.appRegisterForm}},[i("el-form-item",{attrs:{label:"应用名称"}},[i("el-input",{model:{value:e.appRegisterForm.appName,callback:function(t){e.$set(e.appRegisterForm,"appName",t)},expression:"appRegisterForm.appName"}})],1),i("el-form-item",{attrs:{label:"应用描述"}},[i("el-input",{model:{value:e.appRegisterForm.description,callback:function(t){e.$set(e.appRegisterForm,"description",t)},expression:"appRegisterForm.description"}})],1),i("el-form-item",[i("el-button",{attrs:{type:"primary"},on:{click:e.registerApp}},[e._v("注册")]),i("el-button",{on:{click:function(t){e.appRegisterFormVisible=!1}}},[e._v("取消")])],1)],1)],1),i("el-dialog",{attrs:{title:"用户注册",visible:e.userRegisterFormVisible,width:"35%"},on:{"update:visible":function(t){e.userRegisterFormVisible=t}}},[i("el-form",{staticStyle:{margin:"0 5px"},attrs:{model:e.userRegisterForm}},[i("el-form-item",{attrs:{label:"姓名"}},[i("el-input",{model:{value:e.userRegisterForm.username,callback:function(t){e.$set(e.userRegisterForm,"username",t)},expression:"userRegisterForm.username"}})],1),i("el-form-item",{attrs:{label:"手机号"}},[i("el-input",{model:{value:e.userRegisterForm.phone,callback:function(t){e.$set(e.userRegisterForm,"phone",t)},expression:"userRegisterForm.phone"}})],1),i("el-form-item",{attrs:{label:"邮箱地址"}},[i("el-input",{model:{value:e.userRegisterForm.email,callback:function(t){e.$set(e.userRegisterForm,"email",t)},expression:"userRegisterForm.email"}})],1),i("el-form-item",[i("el-button",{attrs:{type:"primary"},on:{click:e.registerUser}},[e._v("注册")]),i("el-button",{on:{click:function(t){e.userRegisterFormVisible=!1}}},[e._v("取消")])],1)],1)],1)],1)},r=[],o={name:"Welcome",data:function(){return{selectedAppInfo:{},appInfoList:[],appRegisterFormVisible:!1,userRegisterFormVisible:!1,appRegisterForm:{appName:"",description:""},userRegisterForm:{username:"",phone:"",email:""}}},methods:{fetchAppNames:function(e){var t=this,i="/appInfo/list?condition="+e;this.axios.get(i).then((function(e){t.appInfoList=e}),(function(e){return t.$message.error(e)}))},selectedApp:function(){this.$store.commit("initAppInfo",this.selectedAppInfo),this.$router.push("/oms/home")},registerApp:function(){var e=this;this.axios.post("/appInfo/save",this.appRegisterForm).then((function(){e.$message.success("应用注册成功!"),e.appRegisterFormVisible=!1}),e.appRegisterFormVisible=!1)},registerUser:function(){var e=this;this.axios.post("/user/save",this.userRegisterForm).then((function(){e.$message.success("用户注册成功!"),e.userRegisterFormVisible=!1}),e.userRegisterFormVisible=!1)}}},n=o,a=(i("b0ce"),i("2877")),l=Object(a["a"])(n,s,r,!1,null,"d0a6e3d4",null);t["default"]=l.exports},"21a8":function(e,t,i){},b0ce:function(e,t,i){"use strict";var s=i("21a8"),r=i.n(s);r.a}}]);
//# sourceMappingURL=chunk-4209dd5c.487a4f83.js.map