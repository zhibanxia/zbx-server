webpackJsonp([9],{SxHN:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var r=e("Xxa5"),n=e.n(r),i=e("exGp"),l=e.n(i),s=e("AQir"),o=e("8e4C"),c=e("Cmve"),d=e("Fd2+"),u={data:function(){return{params:{id:this.$route.params.id,bizType:this.$route.params.biztype},data:{}}},methods:{formatTime:function(t){return Object(s.a)(new Date(t),"yyyy-MM-dd hh:mm:ss")},formatType:function(t){var a=o.c.filter(function(a){return a.id===+t});return a.length?a[0].label:"--"},formatStatus:function(t){var a=o.b.filter(function(a){return a.id===+t});return a.length?a[0].label:"--"},formatAmount:function(t){var a=o.a.filter(function(a){return a.id===+t});return a.length?a[0].label:"--"},getDetail:function(){var t=this;return l()(n.a.mark(function a(){return n.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$ajax("recyleDetail",t.params).then(function(a){t.data=a.data,t.data.resImages=t.data.resImages.split(",");var e=[],r=c.a.province_list[t.data.addr.provinceId],n=c.a.city_list[t.data.addr.cityId],i=c.a.county_list[t.data.addr.areaId];e.push(r),r!==n&&e.push(n),e.push(i),e.push(t.data.addr.addrDetail),t.data.area=e.join("")});case 2:case"end":return a.stop()}},a,t)}))()},onClickLeft:function(){this.$router.go(-1)},handlePreviw:function(t){Object(d.b)({images:t,startPosition:1,onClose:function(){}})}},created:function(){this.getDetail()}},v={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{attrs:{id:"detail-container"}},[e("van-nav-bar",{attrs:{title:"回收详情","left-text":"返回","right-text":"","left-arrow":""},on:{"click-left":t.onClickLeft}}),t._v(" "),e("div",{staticClass:"detail-content"},[e("van-row",{staticClass:"user-title"},[e("van-col",{attrs:{span:"20"}},[e("span",[t._v(" "+t._s(t.formatTime(t.data.publishTime)))])]),t._v(" "),e("van-col",{staticClass:"status",class:"status"+t.data.resStatus,attrs:{span:"4"}},[t._v(t._s(t.formatStatus(t.data.resStatus)))])],1),t._v(" "),e("van-cell-group",[e("van-cell",{attrs:{title:"回收类型",value:t.formatType(t.data.resType)}}),t._v(" "),e("van-cell",{attrs:{title:"数量",value:t.formatAmount(t.data.resAmount)}}),t._v(" "),e("van-cell",{attrs:{title:"照片"}},[e("van-button",{attrs:{slot:"right-icon",type:"default",size:"small"},on:{click:function(a){t.handlePreviw(t.data.resImages)}},slot:"right-icon"},[t._v("预览照片")])],1),t._v(" "),e("van-cell",{attrs:{title:"是否帮忙带仍垃圾",value:t.data.takeGarbage?"是":"否"}}),t._v(" "),t.data.doorServStartTime&&t.data.doorServEndTime?e("van-cell",{attrs:{title:"上门服务时间段",value:t.formatTime(t.data.doorServStartTime)+"~"+t.formatTime(t.data.doorServEndTime)}}):e("van-cell",{attrs:{title:"上门服务时间段",value:"任意时间段"}}),t._v(" "),e("van-cell",{attrs:{title:"回收地址",icon:"location",value:t.data.area}}),t._v(" "),e("van-cell",{attrs:{title:"联系电话",icon:"phone",value:t.data.mobilePhone}}),t._v(" "),t.data.recyleUserLogo?e("van-cell",{attrs:{title:"回收人员",icon:"contact"}},[e("img",{staticClass:"logo",attrs:{src:t.data.recyleUserLogo}}),t._v(t._s(t.data.recyleUserNickname)+"\n      ")]):t._e(),t._v(" "),t.data.recyleTime?e("van-cell",{attrs:{title:"回收时间",icon:"pending-deliver",value:t.formatTime(t.data.recyleTime)}}):t._e()],1)],1),t._v(" "),e("div",{attrs:{slot:"btn"},slot:"btn"})],1)},staticRenderFns:[]};var f=e("VU/8")(u,v,!1,function(t){e("apQ3")},null,null);a.default=f.exports},apQ3:function(t,a){}});
//# sourceMappingURL=9.575f0a082627d91d9c11.js.map