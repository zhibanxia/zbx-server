webpackJsonp([2],{"58NB":function(a,t){},x0vE:function(a,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=e("Xxa5"),s=e.n(n),r=e("exGp"),i=e.n(r),c=e("6Bqv"),o=e("8e4C"),l={components:{RecyleList:c.default},data:function(){return{data:[],data2:[],active:this.$route.query.user?1:0,loading:!1,finished:!1,params:{page:1,size:10},params2:{page:1,size:10}}},created:function(){this.handleTabbar(this.active)},methods:{getRecyleList:function(){var a=this;return i()(s.a.mark(function t(){var e;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a.loading=!0,e={status:"",page:a.params.page,size:a.params.size},t.next=4,a.$ajax("recylerListByAdmin",e).then(function(t){0===t.data.length&&(a.finished=!0),1===a.params.page?a.data=t.data:a.data=a.data.concat(t.data),a.params.page++}).finally(function(){a.loading=!1});case 4:case"end":return t.stop()}},t,a)}))()},getAllUser:function(){var a=this;return i()(s.a.mark(function t(){var e;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a.loading=!0,e={page:a.params2.page,size:a.params2.size},t.next=4,a.$ajax("getAllUser",e).then(function(t){0===t.data.list.length&&(a.finished=!0),1===a.params2.page?a.data2=t.data.list:a.data2=a.data2.concat(t.data.list),a.params2.page++}).finally(function(){a.loading=!1});case 4:case"end":return t.stop()}},t,a)}))()},onRefresh:function(){var a=this;return i()(s.a.mark(function t(){return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a.params.page=1,t.next=3,a.getRecyleList();case 3:case"end":return t.stop()}},t,a)}))()},onRefresh2:function(){var a=this;return i()(s.a.mark(function t(){return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a.params2.page=1,t.next=3,a.getAllUser();case 3:case"end":return t.stop()}},t,a)}))()},handleTabbar:function(a){if(this.active=a,0===a)return this.params.page=1,void this.getRecyleList();this.params2.page=1,this.getAllUser()},handleRecylerClick:function(a){this.$router.push("/admin/recyledetail/"+a.id)},handleUserClick:function(a){3!==a.userType?this.$router.push("/admin/userdetail/"+a.id+"/"+a.userType+"/"+a.userStatus):this.$dialog.alert({message:"该用户为管理员"})},formatType:function(a){return 1===a?"业主":2===a?"回收人员":"管理员"},formatStatus:function(a){var t=o.f.filter(function(t){return t.id===+a});return t.length?t[0].label:"--"}}},u={render:function(){var a=this,t=a.$createElement,e=a._self._c||t;return e("div",{attrs:{id:"admin-wrap"}},[e("van-nav-bar",{attrs:{title:0===a.active?"回收列表":"用户列表"}}),a._v(" "),0===a.active?e("van-pull-refresh",{staticClass:"admin-content",on:{refresh:a.onRefresh},model:{value:a.loading,callback:function(t){a.loading=t},expression:"loading"}},[e("recyle-list",{attrs:{data:a.data},on:{click:a.handleRecylerClick}}),a._v(" "),a.finished?e("p",[a._v("没有更多了")]):e("p",{staticClass:"loadmore",on:{click:a.getRecyleList}},[a._v("点击加载更多")])],1):e("van-pull-refresh",{staticClass:"admin-content",on:{refresh:a.onRefresh2},model:{value:a.loading,callback:function(t){a.loading=t},expression:"loading"}},[a._l(a.data2,function(t,n){return e("div",{key:n,on:{click:function(e){a.handleUserClick(t)}}},[e("van-row",{staticClass:"user-item"},[e("van-col",{attrs:{span:"24"}},[e("van-row",{staticClass:"item-content"},[e("van-col",{attrs:{span:"4"}},[e("img",{staticClass:"resImage",attrs:{src:t.wxLogo}})]),a._v(" "),e("van-col",{attrs:{span:"12"}},[e("van-row",[e("span",[a._v("昵称："+a._s(t.wxNickName))])]),a._v(" "),e("van-row",[e("span",[a._v("类型："+a._s(a.formatType(t.userType)))])])],1),a._v(" "),e("van-col",{staticClass:"status",class:"status"+t.userStatus,attrs:{span:"8"}},[a._v(a._s(a.formatStatus(t.userStatus)))])],1)],1)],1)],1)}),a._v(" "),a.finished?e("p",[a._v("没有更多了")]):e("p",{staticClass:"loadmore",on:{click:a.getAllUser}},[a._v("点击加载更多")])],2),a._v(" "),e("van-tabbar",{on:{change:a.handleTabbar},model:{value:a.active,callback:function(t){a.active=t},expression:"active"}},[e("van-tabbar-item",{attrs:{icon:"wap-home"}},[a._v("回收列表")]),a._v(" "),e("van-tabbar-item",{attrs:{icon:"records"}},[a._v("用户列表")])],1),a._v(" "),a.loading?e("van-loading",{staticClass:"loading",attrs:{color:"white"}}):a._e()],1)},staticRenderFns:[]};var d=e("VU/8")(l,u,!1,function(a){e("58NB")},"data-v-1e02bee3",null);t.default=d.exports}});
//# sourceMappingURL=2.b427fe71322cfb7364cf.js.map