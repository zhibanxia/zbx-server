webpackJsonp([17],{QZDQ:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("Xxa5"),r=a.n(n),o=a("exGp"),s=a.n(o),i=a("Irxe"),c=(a("Cmve"),{components:{Detail:i.default},data:function(){return{params:{id:this.$route.query.id,bizType:this.$route.query.biztype},data:{}}},methods:{getDetail:function(){var e=this;return s()(r.a.mark(function t(){return r.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,e.$ajax("recyleDetail",e.params).then(function(t){e.data=t.data,e.data.resImages=e.data.resImages.split(",");var a=e.data.addr;e.data.area=a.complexVo.addrDetail+a.complexVo.complexName+a.doorInfo});case 2:case"end":return t.stop()}},t,e)}))()},handleDelete:function(){var e=this;return s()(r.a.mark(function t(){var a;return r.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,e.$dialog.confirm({message:"您确定删除吗？"});case 2:return a={id:e.params.id},t.next=5,e.$ajax("delete",a).then(function(){e.$dialog.alert({message:"删除成功"}).then(function(){e.$router.push("/owner")})});case 5:case"end":return t.stop()}},t,e)}))()},handleUpdate:function(){this.$router.push("/owner/publish/"+this.params.id)},handlerBack:function(){this.$router.push("/owner")},handleComplete:function(){var e=this;return s()(r.a.mark(function t(){var a;return r.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,e.$dialog.confirm({message:"您确定回收完成了吗？"});case 2:return a={id:e.params.id},t.next=5,e.$ajax("completeRecycle",a).then(function(){e.$dialog.alert({message:"操作成功"}).then(function(){e.$router.push("/owner")})});case 5:case"end":return t.stop()}},t,e)}))()}},created:function(){this.getDetail()}}),u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"owner-detail"}},[a("detail",{attrs:{data:e.data},on:{backup:e.handlerBack}},[1==e.data.resStatus?a("van-row",{staticStyle:{"margin-top":"30px"}},[a("van-col",{attrs:{span:"11"}},[a("van-button",{attrs:{block:""},on:{click:e.handleDelete}},[e._v("删除")])],1),e._v(" "),a("van-col",{attrs:{span:"11",offset:"2"}},[a("van-button",{attrs:{type:"primary",block:""},on:{click:e.handleUpdate}},[e._v("更新")])],1)],1):e._e(),e._v(" "),2==e.data.resStatus?a("van-row",{staticStyle:{"margin-top":"30px"}},[a("van-col",{attrs:{span:"24"}},[a("van-button",{attrs:{type:"primary",block:""},on:{click:e.handleComplete}},[e._v("回收完成")])],1)],1):e._e()],1)],1)},staticRenderFns:[]},l=a("VU/8")(c,u,!1,null,null,null);t.default=l.exports}});
//# sourceMappingURL=17.7319dc1f3ba0c241c96a.js.map