webpackJsonp([9],{cQDO:function(a,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("Xxa5"),n=e.n(i),r=e("exGp"),s=e.n(r),c=e("6Bqv"),o=e("l0xS"),l={components:{RecyleList:c.default,Empty:o.default},data:function(){return{data:[],active:2==+this.$route.query.bizType?1:0,loading:!1,finished:!1,params:{page:1,size:10,bizType:1},isupdate:!!this.$route.query.update}},created:function(){this.getList()},methods:{getList:function(){var a=this;return s()(n.a.mark(function t(){return n.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a.loading=!0,t.next=3,a.$ajax("recyleList",a.params).then(function(t){0===t.data.length&&(a.finished=!0),1===a.params.page?a.data=t.data:a.data=a.data.concat(t.data),a.params.page++}).finally(function(){a.loading=!1});case 3:case"end":return t.stop()}},t,a)}))()},onRefresh:function(){this.params.page=1,this.getList()},handleTabbar:function(a){0===a?(this.params.bizType=1,this.params.page=1,this.getList()):1===a?(this.params.bizType=2,this.params.page=1,this.isupdate=!1,this.getList()):window.location.href="/#/usercenter/recylerarea"},handleClick:function(a){this.$router.push("/recyler/detail/?id="+a.id+"&biztype="+this.params.bizType)}}},d={render:function(){var a=this,t=a.$createElement,e=a._self._c||t;return e("div",{attrs:{id:"recyler-wrap"}},[e("van-nav-bar",{attrs:{title:0===a.active?"回收列表":"我的回收"}}),a._v(" "),a.data.length?e("van-pull-refresh",{staticClass:"recyler-content",on:{refresh:a.onRefresh},model:{value:a.loading,callback:function(t){a.loading=t},expression:"loading"}},[e("recyle-list",{attrs:{data:a.data,biztype:a.params.bizType},on:{click:a.handleClick}}),a._v(" "),a.finished?e("p",{staticClass:"loadmore"},[a._v("没有更多了")]):e("p",{staticClass:"loadmore",on:{click:a.getList}},[a._v("点击加载更多")])],1):e("empty"),a._v(" "),e("van-tabbar",{on:{change:a.handleTabbar},model:{value:a.active,callback:function(t){a.active=t},expression:"active"}},[e("van-tabbar-item",{attrs:{icon:"wap-home"}},[a._v("回收列表")]),a._v(" "),e("van-tabbar-item",{attrs:{icon:"records",dot:a.isupdate}},[a._v("我的回收")]),a._v(" "),e("van-tabbar-item",{attrs:{icon:"contact"}},[a._v("个人中心")])],1),a._v(" "),a.loading?e("van-loading",{staticClass:"loading",attrs:{color:"white"}}):a._e()],1)},staticRenderFns:[]};var p=e("VU/8")(l,d,!1,function(a){e("u4dP")},"data-v-45391df3",null);t.default=p.exports},u4dP:function(a,t){}});
//# sourceMappingURL=9.22c4f7118b1aad4e437a.js.map