(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-03b4746a"],{"19a4":function(t,s,a){"use strict";var e=a("51c8"),n=a.n(e);n.a},"51c8":function(t,s,a){},6337:function(t,s,a){"use strict";var e=a("ffdc"),n=a.n(e);n.a},"7d8a":function(t,s,a){"use strict";a.r(s);var e=function(){var t=this,s=t.$createElement,a=t._self._c||s;return a("div",{attrs:{id:"home"}},[a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:6}},[a("div",{staticClass:"wrap"},[a("div",{staticClass:"grid-content bg-purple"},[a("div",{staticClass:"text"},[t._v("任务总数")]),a("div",{staticClass:"text"},[t._v(t._s(t.systemInfo.jobCount))])]),a("i",{staticClass:"el-icon-orange"})])]),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"wrap"},[a("div",{staticClass:"grid-content bg-purple"},[a("div",{staticClass:"text"},[t._v("当前运行实例数")]),a("div",{staticClass:"text"},[t._v(t._s(t.systemInfo.runningInstanceCount))])]),a("i",{staticClass:"el-icon-loading"})])]),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"wrap"},[a("div",{staticClass:"grid-content bg-purple"},[a("div",{staticClass:"text"},[t._v("近期失败任务数")]),a("div",{staticClass:"text"},[t._v(t._s(t.systemInfo.failedInstanceCount))])]),a("i",{staticClass:"el-icon-bell"})])]),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"wrap"},[a("div",{staticClass:"grid-content bg-purple"},[a("div",{staticClass:"text"},[t._v("集群机器数")]),a("div",{staticClass:"text"},[t._v(t._s(t.activeWorkerCount))])]),a("i",{staticClass:"el-icon-cpu"})])])],1),a("el-row",[a("el-col",{attrs:{span:24}},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.workerList,height:"400px","row-class-name":t.workerTableRowClassName}},[a("el-table-column",{attrs:{prop:"address",label:"机器地址"}}),a("el-table-column",{attrs:{prop:"cpuLoad",label:"CPU占用"}}),a("el-table-column",{attrs:{prop:"memoryLoad",label:"内存占用"}}),a("el-table-column",{attrs:{prop:"diskLoad",label:"磁盘占用"}})],1)],1)],1)],1)},n=[],i={name:"Home",data:function(){return{systemInfo:{jobCount:"N/A",runningInstanceCount:"N/A",failedInstanceCount:"N/A"},activeWorkerCount:"N/A",workerList:[]}},methods:{workerTableRowClassName:function(t){var s=t.row;switch(s.status){case 1:return"success-row";case 2:return"warning-row";case 3:return"error-row"}}},mounted:function(){var t=this,s=t.$store.state.appInfo.id;t.axios.get("/system/overview?appId="+s).then((function(s){return t.systemInfo=s})),t.axios.get("/system/listWorker?appId="+s).then((function(s){t.workerList=s,t.activeWorkerCount=t.workerList.length}))}},r=i,o=(a("19a4"),a("6337"),a("2877")),l=Object(o["a"])(r,e,n,!1,null,"32453e06",null);s["default"]=l.exports},ffdc:function(t,s,a){}}]);
//# sourceMappingURL=chunk-03b4746a.91f2e29f.js.map