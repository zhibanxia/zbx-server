webpackJsonp([4],{"D0w/":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=a("Xxa5"),n=a.n(r),s=a("woOf"),i=a.n(s),u=a("exGp"),o=a.n(u),c=a("8e4C"),l=a("Cmve"),d=a("Fd2+"),v={data:function(){return{id:this.$route.params.id,usertype:+this.$route.params.userType,data:{status:+this.$route.params.userStatus}}},watch:{$route:function(t,e){t.params.id&&t.params.userType&&t.params.userStatus&&(this.id=this.$route.params.id,this.usertype=+this.$route.params.userType,this.data.status=+this.$route.params.userStatus,this.getUserDetail())}},created:function(){this.getUserDetail()},methods:{getUserDetail:function(){var t=this;return o()(n.a.mark(function e(){var a;return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(a=1===t.usertype?"getOwnerInfo":2===t.usertype?"getRecylerInfo":""){e.next=4;break}return window.location.href="/#/error",e.abrupt("return");case 4:return e.next=6,t.$ajax(a,{id:t.id}).then(function(e){t.data=i()({},t.data,e.data)});case 6:case"end":return e.stop()}},e,t)}))()},formatStatus:function(t){var e=c.f.filter(function(e){return e.id===+t});return e.length?e[0].label:"--"},formatArea:function(t){var e=[],a=l.a.province_list[t.provinceId],r=l.a.city_list[t.cityId],n=l.a.county_list[t.areaId];return e.push(a),a!==r&&e.push(r),e.push(n),e.push(t.addrDetail),e.join("")},onClickLeft:function(){this.$router.push("/admin/?user=1&timestamp="+(new Date).getTime())},handlePreviw:function(t){Object(d.b)({images:t,startPosition:1,onClose:function(){}})},handleRecyler:function(t){var e=this;return o()(n.a.mark(function a(){return n.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,e.$dialog.confirm({message:"确定审核"+(t?"通过":"不通过")+"吗？"});case 2:return a.next=4,e.$ajax("verifyHuishou",{id:e.id,verifyResult:t});case 4:return a.next=6,e.$dialog.alert({message:"操作成功"});case 6:e.$router.push("/admin/?user=1&timestamp="+(new Date).getTime());case 7:case"end":return a.stop()}},a,e)}))()},handleYezhu:function(t){var e=this;return o()(n.a.mark(function a(){return n.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,e.$dialog.confirm({message:"确定"+(5===t?"禁用":"恢复")+"吗？"});case 2:return a.next=4,e.$ajax("modifyUserStatus",{id:e.id,status:t});case 4:return a.next=6,e.$dialog.alert({message:"操作成功"});case 6:e.$router.push("/admin/?user=1&timestamp="+(new Date).getTime());case 7:case"end":return a.stop()}},a,e)}))()}}},f={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"user-container"}},[a("van-nav-bar",{attrs:{title:"用户详情","left-text":"返回","right-text":"","left-arrow":""},on:{"click-left":t.onClickLeft}}),t._v(" "),a("div",{staticClass:"detail-content"},[a("van-cell-group",[a("van-cell",{attrs:{title:"昵称",value:t.data.wxNickName}}),t._v(" "),a("van-cell",{attrs:{title:"微信头像"}},[a("img",{staticClass:"logo",attrs:{src:t.data.wxLogo},on:{click:function(e){t.handlePreviw([t.data.wxLogo])}}})]),t._v(" "),2===t.usertype?a("van-cell",{attrs:{title:"上传头像"}},[a("img",{staticClass:"logo",attrs:{src:t.data.verifyLogo},on:{click:function(e){t.handlePreviw([t.data.verifyLogo])}}})]):t._e(),t._v(" "),a("van-cell",{attrs:{title:"联系电话",value:t.data.mobilePhone}}),t._v(" "),1===t.usertype&&t.data.defaultAddr?a("van-cell",{attrs:{title:"业主住址",icon:"location",value:t.formatArea(t.data.defaultAddr)}}):t._e(),t._v(" "),2===t.usertype?a("h2",{staticClass:"focus-items"},[t._v("关注小区")]):t._e(),t._v(" "),t._l(t.data.focusAddrList,function(e,r){return 2===t.usertype?a("van-cell",{key:"focus"+r,attrs:{title:"小区地址",value:t.formatArea(e)}}):t._e()}),t._v(" "),a("van-cell",{attrs:{title:"用户状态",value:t.formatStatus(t.data.status)}})],2)],1),t._v(" "),a("van-row",{staticStyle:{"margin-top":"30px"}},[2===t.usertype&&4===t.data.status?a("van-col",{attrs:{span:"24"}},[a("van-button",{attrs:{block:""},on:{click:function(e){t.handleRecyler(!0)}}},[t._v("审核通过")]),t._v(" "),a("van-button",{staticStyle:{"margin-top":"20px"},attrs:{type:"primary",block:""},on:{click:function(e){t.handleRecyler(!1)}}},[t._v("审核不通过")])],1):t._e(),t._v(" "),6!==t.data.status&&5!==t.data.status||5===t.data.status?a("van-col",{attrs:{span:"24"}},[6!==t.data.status&&5!==t.data.status?a("van-button",{attrs:{type:"primary",block:""},on:{click:function(e){t.handleYezhu(t.data.status)}}},[t._v("禁用账号")]):t._e(),t._v(" "),5===t.data.status?a("van-button",{attrs:{type:"primary",block:""},on:{click:function(e){t.handleYezhu(1)}}},[t._v("恢复账号")]):t._e()],1):t._e()],1)],1)},staticRenderFns:[]};var p=a("VU/8")(v,f,!1,function(t){a("KX8v")},null,null);e.default=p.exports},KX8v:function(t,e){}});
//# sourceMappingURL=4.09401623ed566c562dd0.js.map