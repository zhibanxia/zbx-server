webpackJsonp([10],{qZa5:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var a=e("8e4C"),n={data:function(){return{status:+this.$route.query.status,type:+this.$route.query.type}},computed:{statusTxt:function(){var t=this,s=a.f.filter(function(s){return s.id===t.status});return s.length?s[0].label:null}},methods:{handleClick:function(){this.$router.push("/register?type="+this.type)}}},r={render:function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",{attrs:{id:"error"}},[t.statusTxt&&t.status?e("p",{staticClass:"desc"},[t._v("账号"+t._s(t.statusTxt))]):e("p",{staticClass:"desc"},[t._v("啊哦，出错了")]),t._v(" "),3===t.status?e("van-row",[e("van-col",{attrs:{span:"10",offset:"7"}},[e("van-button",{attrs:{size:"normal",block:""},on:{click:t.handleClick}},[t._v("重新提交")])],1)],1):t._e(),t._v(" "),2===t.status&&2===t.type?e("van-row",[e("van-col",{attrs:{span:"10",offset:"7"}},[e("van-button",{attrs:{size:"normal",block:""},on:{click:t.handleClick}},[t._v("完善信息")])],1)],1):t._e()],1)},staticRenderFns:[]};var u=e("VU/8")(n,r,!1,function(t){e("r2Rf")},"data-v-4928f882",null);s.default=u.exports},r2Rf:function(t,s){}});
//# sourceMappingURL=10.586f79cdb0d69cb46154.js.map