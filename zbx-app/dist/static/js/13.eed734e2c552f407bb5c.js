webpackJsonp([13],{excn:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var n=e("Xxa5"),r=e.n(n),s=e("exGp"),i=e.n(s),o=e("Irxe"),c=e("Cmve"),u={components:{Detail:o.default},data:function(){return{params:{id:this.$route.query.id,bizType:this.$route.query.biztype},data:{}}},methods:{getDetail:function(){var t=this;return i()(r.a.mark(function a(){return r.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$ajax("recyleDetail",t.params).then(function(a){t.data=a.data,t.data.resImages=t.data.resImages.split(",");var e=[],n=c.a.province_list[t.data.addr.provinceId],r=c.a.city_list[t.data.addr.cityId],s=c.a.county_list[t.data.addr.areaId];e.push(n),n!==r&&e.push(r),e.push(s),e.push(t.data.addr.addrDetail),t.data.area=e.join("")});case 2:case"end":return a.stop()}},a,t)}))()},handleDelete:function(){var t=this;return i()(r.a.mark(function a(){var e;return r.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$dialog.confirm({message:"您确定删除吗？"});case 2:return e={id:t.params.id},a.next=5,t.$ajax("delete",e).then(function(){t.$dialog.alert({message:"删除成功"}).then(function(){t.$router.push("/owner")})});case 5:case"end":return a.stop()}},a,t)}))()},handleUpdate:function(){this.$router.push("/owner/publish/"+this.params.id)},handlerBack:function(){this.$router.push("/owner")},handleComplete:function(){var t=this;return i()(r.a.mark(function a(){var e;return r.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$dialog.confirm({message:"您确定回收完成了吗？"});case 2:return e={id:t.params.id},a.next=5,t.$ajax("completeRecycle",e).then(function(){t.$dialog.alert({message:"操作成功"}).then(function(){t.$router.push("/owner")})});case 5:case"end":return a.stop()}},a,t)}))()}},created:function(){this.getDetail()}},d={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{attrs:{id:"owner-detail"}},[e("detail",{attrs:{data:t.data},on:{backup:t.handlerBack}},[1==t.data.resStatus?e("van-row",{staticStyle:{"margin-top":"30px"}},[e("van-col",{attrs:{span:"11"}},[e("van-button",{attrs:{block:""},on:{click:t.handleDelete}},[t._v("删除")])],1),t._v(" "),e("van-col",{attrs:{span:"11",offset:"2"}},[e("van-button",{attrs:{type:"primary",block:""},on:{click:t.handleUpdate}},[t._v("更新")])],1)],1):t._e(),t._v(" "),2==t.data.resStatus?e("van-row",{staticStyle:{"margin-top":"30px"}},[e("van-col",{attrs:{span:"24"}},[e("van-button",{attrs:{type:"primary",block:""},on:{click:t.handleComplete}},[t._v("回收完成")])],1)],1):t._e()],1)],1)},staticRenderFns:[]},l=e("VU/8")(u,d,!1,null,null,null);a.default=l.exports}});
//# sourceMappingURL=13.eed734e2c552f407bb5c.js.map