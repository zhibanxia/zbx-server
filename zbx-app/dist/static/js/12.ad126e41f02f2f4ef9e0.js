webpackJsonp([12],{q8vY:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var r=e("Xxa5"),n=e.n(r),s=e("exGp"),i=e.n(s),c=e("Irxe"),u=e("Cmve"),d={components:{Detail:c.default},data:function(){return{params:{id:this.$route.query.id,bizType:+this.$route.query.biztype},data:{}}},methods:{getDetail:function(){var t=this;return i()(n.a.mark(function a(){return n.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$ajax("recyleDetail",t.params).then(function(a){t.data=a.data,t.data.resImages=t.data.resImages.split(",");var e=[],r=u.a.province_list[t.data.addr.provinceId],n=u.a.city_list[t.data.addr.cityId],s=u.a.county_list[t.data.addr.areaId];e.push(r),r!==n&&e.push(n),e.push(s),e.push(t.data.addr.addrDetail),t.data.area=e.join("")});case 2:case"end":return a.stop()}},a,t)}))()},submit:function(){var t=this;return i()(n.a.mark(function a(){var e;return n.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,t.$dialog.confirm({message:"您确认回收吗？"});case 2:return e={id:t.params.id},a.next=5,t.$ajax("confirmRecycle",e,{showSuccessMsg:!0}).then(function(){t.$dialog.alert({message:"确认成功"}).then(function(){t.$router.push("/recyler?update=true")})});case 5:case"end":return a.stop()}},a,t)}))()},handlerBack:function(){this.$router.push("/recyler?bizType="+this.params.bizType)}},created:function(){this.getDetail()}},o={render:function(){var t=this.$createElement,a=this._self._c||t;return a("div",{attrs:{id:"recyler-detail"}},[a("detail",{attrs:{data:this.data},on:{backup:this.handlerBack}},[1==this.data.resStatus&&1===this.params.bizType?a("van-row",{staticStyle:{"margin-top":"30px"}},[a("van-col",{attrs:{span:"24"}},[a("van-button",{attrs:{type:"primary",block:""},on:{click:this.submit}},[this._v("确认回收")])],1)],1):this._e()],1)],1)},staticRenderFns:[]},p=e("VU/8")(d,o,!1,null,null,null);a.default=p.exports}});
//# sourceMappingURL=12.ad126e41f02f2f4ef9e0.js.map