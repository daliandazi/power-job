(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-14b23d20"],{8418:function(o,e,t){"use strict";var n=t("c04e"),r=t("9bf2"),i=t("5c6c");o.exports=function(o,e,t){var l=n(e);l in o?r.f(o,l,i(0,t)):o[l]=t}},a434:function(o,e,t){"use strict";var n=t("23e7"),r=t("23cb"),i=t("a691"),l=t("50c4"),s=t("7b0b"),a=t("65f0"),f=t("8418"),c=t("1dde"),d=t("ae40"),w=c("splice"),u=d("splice",{ACCESSORS:!0,0:0,1:2}),p=Math.max,k=Math.min,m=9007199254740991,b="Maximum allowed length exceeded";n({target:"Array",proto:!0,forced:!w||!u},{splice:function(o,e){var t,n,c,d,w,u,h=s(this),I=l(h.length),v=r(o,I),g=arguments.length;if(0===g?t=n=0:1===g?(t=0,n=I-v):(t=g-2,n=k(p(i(e),0),I-v)),I+t-n>m)throw TypeError(b);for(c=a(h,n),d=0;d<n;d++)w=v+d,w in h&&f(c,d,h[w]);if(c.length=n,t<n){for(d=v;d<I-n;d++)w=d+n,u=d+t,w in h?h[u]=h[w]:delete h[u];for(d=I;d>I-n+t;d--)delete h[d-1]}else if(t>n)for(d=I-n;d>v;d--)w=d+n-1,u=d+t-1,w in h?h[u]=h[w]:delete h[u];for(d=0;d<t;d++)h[d+v]=arguments[d+2];return h.length=I-n+t,c}})},b4cb:function(o,e,t){"use strict";var n=t("d599"),r=t.n(n);r.a},c740:function(o,e,t){"use strict";var n=t("23e7"),r=t("b727").findIndex,i=t("44d2"),l=t("ae40"),s="findIndex",a=!0,f=l(s);s in[]&&Array(1)[s]((function(){a=!1})),n({target:"Array",proto:!0,forced:a||!f},{findIndex:function(o){return r(this,o,arguments.length>1?arguments[1]:void 0)}}),i(s)},d566:function(o,e,t){"use strict";t.r(e);var n=function(){var o=this,e=o.$createElement,t=o._self._c||e;return t("div",[t("el-row",{staticStyle:{margin:"20px"}},[t("el-col",{attrs:{span:1}},[t("el-button",{attrs:{type:"primary"},on:{click:o.back}},[o._v("返回")])],1),t("el-col",{attrs:{span:1,offset:22}},[t("el-button",{attrs:{type:"success"},on:{click:o.saveWorkflow}},[o._v("保存")])],1)],1),t("el-row",[t("el-form",{ref:"form",attrs:{model:o.workflowInfo}},[t("el-form-item",{attrs:{label:"工作流名称"}},[t("el-input",{model:{value:o.workflowInfo.wfName,callback:function(e){o.$set(o.workflowInfo,"wfName",e)},expression:"workflowInfo.wfName"}})],1),t("el-form-item",{attrs:{label:"工作流描述"}},[t("el-input",{model:{value:o.workflowInfo.wfDescription,callback:function(e){o.$set(o.workflowInfo,"wfDescription",e)},expression:"workflowInfo.wfDescription"}})],1),t("el-form-item",{attrs:{label:"定时信息"}},[t("el-row",[t("el-col",{attrs:{span:6}},[t("el-select",{attrs:{placeholder:"时间表达式类型"},model:{value:o.workflowInfo.timeExpressionType,callback:function(e){o.$set(o.workflowInfo,"timeExpressionType",e)},expression:"workflowInfo.timeExpressionType"}},o._l(o.timeExpressionTypeOptions,(function(o){return t("el-option",{key:o.key,attrs:{label:o.label,value:o.key}})})),1)],1),t("el-col",{attrs:{span:12}},[t("el-input",{attrs:{placeholder:"CRON填写CRON表达式，API无需填写"},model:{value:o.workflowInfo.timeExpression,callback:function(e){o.$set(o.workflowInfo,"timeExpression",e)},expression:"workflowInfo.timeExpression"}})],1)],1)],1),t("el-form-item",{attrs:{label:"最大实例"}},[t("el-input-number",{model:{value:o.workflowInfo.maxWfInstanceNum,callback:function(e){o.$set(o.workflowInfo,"maxWfInstanceNum",e)},expression:"workflowInfo.maxWfInstanceNum"}})],1),t("el-form-item",{attrs:{label:"报警配置"}},[t("el-select",{attrs:{multiple:"",filterable:"",placeholder:"选择报警通知人员"},model:{value:o.workflowInfo.notifyUserIds,callback:function(e){o.$set(o.workflowInfo,"notifyUserIds",e)},expression:"workflowInfo.notifyUserIds"}},o._l(o.userList,(function(o){return t("el-option",{key:o.id,attrs:{label:o.username,value:o.id}})})),1)],1)],1)],1),t("el-row",[t("el-row",[t("el-button",{on:{click:o.onClickImportNode}},[o._v("导入任务")]),t("el-button",{on:{click:o.onClickRemoveNode}},[o._v("删除任务")]),t("el-button",{on:{click:o.onClickAddFrom}},[o._v("新增起点")]),t("el-button",{on:{click:o.onClickAddTo}},[o._v("新增终点")]),t("el-button",{on:{click:o.onClickRemoveEdge}},[o._v("删除边")])],1),t("div",[t("svg",{attrs:{width:"80%",height:"1000px",id:"svgCanvas"}},[t("g"),t("rect")])])],1),t("el-drawer",{attrs:{title:"请选择需要导入工作流的任务",visible:o.importDrawerVisible,direction:"rtl",size:"50%"},on:{"update:visible":function(e){o.importDrawerVisible=e}}},[t("el-row",[t("el-form",{staticClass:"el-form--inline",attrs:{inline:!0,model:o.jobQueryContent}},[t("el-form-item",{attrs:{label:"任务ID"}},[t("el-input",{attrs:{placeholder:"任务ID"},model:{value:o.jobQueryContent.jobId,callback:function(e){o.$set(o.jobQueryContent,"jobId",e)},expression:"jobQueryContent.jobId"}})],1),t("el-form-item",{attrs:{label:"关键字"}},[t("el-input",{attrs:{placeholder:"关键字"},model:{value:o.jobQueryContent.keyword,callback:function(e){o.$set(o.jobQueryContent,"keyword",e)},expression:"jobQueryContent.keyword"}})],1),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:o.listJobInfos}},[o._v("查询")]),t("el-button",{attrs:{type:"cancel"},on:{click:o.onClickReset}},[o._v("重置")])],1)],1)],1),t("el-table",{attrs:{data:o.jobInfoPageResult.data}},[t("el-table-column",{attrs:{property:"id",label:"任务ID",width:"80"}}),t("el-table-column",{attrs:{property:"jobName",label:"任务名称",width:"200"}}),t("el-table-column",{attrs:{label:"操作",width:"300"},scopedSlots:o._u([{key:"default",fn:function(e){return[t("el-button",{attrs:{size:"medium"},on:{click:function(t){return o.importNode(e.row)}}},[o._v("导入")])]}}])})],1),t("el-row",[t("el-pagination",{attrs:{layout:"prev, pager, next",total:this.jobInfoPageResult.totalItems,"page-size":this.jobInfoPageResult.pageSize},on:{"current-change":o.onClickChangePage}})],1)],1)],1)},r=[],i=(t("c740"),t("4160"),t("d81d"),t("a434"),t("159b"),t("1226")),l=t.n(i),s=t("5698"),a={name:"WorkflowEditor",data:function(){return{workflowInfo:{appId:this.$store.state.appInfo.id,enable:!0,maxWfInstanceNum:1,notifyUserIds:[],peworkflowDAG:{nodes:[],edges:[]},timeExpression:void 0,timeExpressionType:void 0,wfDescription:void 0,wfName:void 0},timeExpressionTypeOptions:[{key:"API",label:"API"},{key:"CRON",label:"CRON表达式"}],userList:[],importDrawerVisible:!1,jobQueryContent:{appId:this.$store.state.appInfo.id,index:0,pageSize:20,jobId:void 0,keyword:void 0},jobInfoPageResult:{pageSize:20,totalItems:0,data:[]},event:void 0,from:void 0}},methods:{back:function(){this.$router.go(-1)},onClickReset:function(){this.jobQueryContent.keyword=void 0,this.jobQueryContent.jobId=void 0},listJobInfos:function(){var o=this;this.axios.post("/job/list",this.jobQueryContent).then((function(e){o.jobInfoPageResult=e}))},onClickChangePage:function(o){this.jobQueryContent.index=o-1,this.listJobInfos()},onClickImportNode:function(){this.listJobInfos(),this.importDrawerVisible=!0},importNode:function(o){var e=!0;this.workflowInfo.peworkflowDAG.nodes.forEach((function(t){o.id===t.jobId&&(e=!1,console.log("nodes in workflow cannot be duplicated"))})),e&&(console.log("add node: "+o.id),this.workflowInfo.peworkflowDAG.nodes.push({jobId:o.id,jobName:o.jobName}),this.draw())},onClickRemoveNode:function(){this.event=3,this.$message.info("请点击需要删除的节点")},onClickAddFrom:function(){this.event=1,this.$message.info("请点击起始节点")},onClickAddTo:function(){this.event=2,this.$message.info("请点击目标节点")},onClickRemoveEdge:function(){this.event=4,this.$message.info("请点击需要删除的边")},saveWorkflow:function(){var o=this;this.axios.post("/workflow/save",this.workflowInfo).then((function(){o.$message.success("保存成功！"),o.back()}))},draw:function(){var o=this;console.log("draw by data: "+JSON.stringify(this.workflowInfo.peworkflowDAG));var e=(new l.a.graphlib.Graph).setGraph({}),t=this.workflowInfo.peworkflowDAG.nodes.map((function(o){return{id:o.jobId,label:"jobId: "+o.jobId+"\njobName: "+o.jobName}}));t.forEach((function(o){e.setNode(o.id,o)})),this.workflowInfo.peworkflowDAG.edges.forEach((function(o){e.setEdge(o.from,o.to,{})}));var n=s["select"]("svg"),r=n.select("g"),i=s["zoom"]().on("zoom",(function(){r.attr("transform",s["event"].transform)}));n.call(i);var a=new l.a.render;a(r,e),r.selectAll("g.node").on("click",(function(t){for(var n=0;n<o.workflowInfo.peworkflowDAG.nodes.length;n++)t==o.workflowInfo.peworkflowDAG.nodes[n].jobId&&(console.log("onClickNode, jobId="+t),o.onClickDAGNode(t,e))})),r.selectAll("path").on("click",(function(e){if(4===o.event){var t=o.workflowInfo.peworkflowDAG.edges;o.workflowInfo.peworkflowDAG.edges=[],t.forEach((function(t){t.from===e.v&&t.to===e.w||o.workflowInfo.peworkflowDAG.edges.push(t),o.draw()}))}}))},onClickDAGNode:function(o,e){var t=this;switch(this.event){case 3:e.removeNode(o);var n=this.workflowInfo.peworkflowDAG.nodes,r=this.workflowInfo.peworkflowDAG.edges;this.workflowInfo.peworkflowDAG.edges=[],n.splice(n.findIndex((function(e){return e.jobId==o})),1),r.forEach((function(e){e.from==o||e.to==o?console.log("remove edge: "+JSON.stringify(e)):t.workflowInfo.peworkflowDAG.edges.push(e)})),this.draw();break;case 1:this.from=o;break;case 2:var i=o;if(void 0===this.from){this.$message.warning("请先添加起点！");break}if(this.from===i){this.$message.warning("非法操作（起点终点相同）！");break}var l=!0;this.workflowInfo.peworkflowDAG.edges.forEach((function(o){o.from===t.from&&o.to===i&&(l=!1,console.log("edge(%o -> %o) already exists!",t.from,i))})),l&&(this.workflowInfo.peworkflowDAG.edges.push({from:this.from,to:i}),console.log("new edge(%o -> %o)",this.from,i)),this.draw();break}}},mounted:function(){var o=this;o.axios.get("/user/list").then((function(e){return o.userList=e}));var e=this.$route.params.modify;e&&(this.workflowInfo=this.$route.params.workflowInfo,this.workflowInfo.appId=this.$store.state.appInfo.id,this.draw())}},f=a,c=(t("b4cb"),t("2877")),d=Object(c["a"])(f,n,r,!1,null,"34dc2e86",null);e["default"]=d.exports},d599:function(o,e,t){}}]);
//# sourceMappingURL=chunk-14b23d20.b09c6601.js.map