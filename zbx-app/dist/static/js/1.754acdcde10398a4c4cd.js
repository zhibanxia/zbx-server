webpackJsonp([1],{"3mkF":function(e,t){},bqOp:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("TIfe"),n={data:function(){return{username:Object(a.a)("username"),type:[{label:"业主",id:1},{label:"回收人员",id:2}],selectType:"",selectName:""}},methods:{handleClick:function(e){this.selectType=+e.id,this.selectName=e.label},handleNext:function(){if(!this.selectType||!this.selectName)return this.$dialog.alert({title:"提示",message:"请选择身份"}),!1;this.$router.push("/register/recyler/"+this.selectType)}}},l={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{attrs:{id:"register"}},[s("van-row",[s("van-col",{staticClass:"title",attrs:{span:"24"}},[e._v("Hi,"+e._s(e.username))])],1),e._v(" "),s("van-row",[s("van-col",{staticClass:"title",attrs:{span:"24"}},[e._v("请选择身份")])],1),e._v(" "),e._l(e.type,function(t){return s("van-row",{key:t.id,staticClass:"user-type"},[s("van-col",{class:{active:t.id===e.selectType},attrs:{span:"24"}},[s("div",{on:{click:function(s){e.handleClick(t)}}},[e._v(e._s(t.label))])])],1)}),e._v(" "),s("van-row",[s("van-col",{staticClass:"tips",attrs:{span:"24"}},[e.selectName?s("div",[e._v("当前选择: "+e._s(e.selectName))]):e._e()])],1),e._v(" "),s("van-button",{attrs:{type:"primary",size:"large"},on:{click:e.handleNext}},[e._v("下一步")])],2)},staticRenderFns:[]};var i=s("VU/8")(n,l,!1,function(e){s("3mkF")},"data-v-f8be657e",null);t.default=i.exports}});
//# sourceMappingURL=1.754acdcde10398a4c4cd.js.map