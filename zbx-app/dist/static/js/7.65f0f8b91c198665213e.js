webpackJsonp([7],{UKzF:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var o=t("woOf"),a=t.n(o),n=t("fZjL"),i=t.n(n),s=t("Xxa5"),l=t.n(s),d=t("exGp"),c=t.n(d),m=t("8e4C"),u=t("Fd2+"),f=t("AQir"),p=t("Cmve"),h={data:function(){return{resTypes:m.d,amounts:m.b,takeGarbages:m.g,resTypesPopShow:!1,amountPopShow:!1,takeGarbagePopShow:!1,doorServStartTimePopShow:!1,doorServEndTimePopShow:!1,form:{addr:{}},startDate:new Date,endDate:new Date,errors:{defaultAddrTxt:"",mobilePhone:"",area:"",doorInfo:""},areaSelectShow:!1,areaList:p.a,id:this.$route.params.id,area:"",selectArea:"",defaultAddrTxt:"",loading:!1,resImages:[],searchDialog:{show:!1}}},computed:{title:function(){return this.id?"更新回收":"发布回收"},btnTxt:function(){return this.id?"更新":"发布"}},watch:{$route:function(e,r){this.id=this.$route.params.id}},created:function(){this.getDetail()},methods:{getDetail:function(){var e=this;return c()(l.a.mark(function r(){var t;return l.a.wrap(function(r){for(;;)switch(r.prev=r.next){case 0:if(!e.id){r.next=5;break}return t={id:e.id,bizType:3},r.next=4,e.$ajax("recyleDetail",t).then(function(r){e.form=r.data,e.resImages=e.form.resImages.split(","),e.form.addr=r.data.addr,e.form.addr.complexVo&&(e.defaultAddrTxt=e.form.addr.complexVo.addrDetail+e.form.addr.complexVo.complexName)});case 4:return r.abrupt("return");case 5:return r.next=7,e.$ajax("getYezhuUserInfo").then(function(r){var t=r.data.defaultAddr||{};e.form.mobilePhone=r.data.mobilePhone,e.form.addr=t,e.form.takeGarbageFlag=!1,t.complexVo&&(e.defaultAddrTxt=t.complexVo.addrDetail+t.complexVo.complexName)});case 7:case"end":return r.stop()}},r,e)}))()},handleRestypeConfirm:function(e){this.form.resType=e.id,this.resTypesPopShow=!1},formatType:function(e){var r=m.d.filter(function(r){return r.id===+e});return r.length?r[0].label:"请选择"},handleAmountConfirm:function(e){this.form.resAmount=e.id,this.amountPopShow=!1},formatAmount:function(e){var r=m.b.filter(function(r){return r.id===+e});return r.length?r[0].label:"请选择"},handleTakeGarbageConfirm:function(e){this.form.takeGarbageFlag=e.id,this.takeGarbagePopShow=!1},formatTakegarbage:function(e){var r=m.g.filter(function(r){return r.id===e});return r.length?r[0].label:"请选择"},handleDoorServStartTimeConfirm:function(e){this.form.doorServStartTime=Object(f.a)(this.startDate,"yyyy-MM-dd hh:mm:ss"),this.doorServStartTimePopShow=!1},handledoorServEndTimeConfirm:function(e){this.form.doorServEndTime=Object(f.a)(this.endDate,"yyyy-MM-dd hh:mm:ss"),this.doorServEndTimePopShow=!1},formatter:function(e,r){return"year"===e?r+"年":"month"===e?r+"月":r},onRead:function(e){var r=this;return c()(l.a.mark(function t(){var o;return l.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return r.loading=!0,o={imgBase64:e.content,bizType:2},r.resImages=r.resImages||[],t.next=5,r.$ajax("upload",o).then(function(e){r.resImages.push(e.data)}).finally(function(){r.loading=!1});case 5:case"end":return t.stop()}},t,r)}))()},onClickLeft:function(){this.$router.go(-1)},handlePreviw:function(e){if(!e||!e.length)return this.$dialog.alert({message:"您还未上传照片"}),!1;Object(u.b)({images:e,startPosition:1,onClose:function(){}})},submit:function(){var e=this;return c()(l.a.mark(function r(){var t,o,a;return l.a.wrap(function(r){for(;;)switch(r.prev=r.next){case 0:if(e.form.mobilePhone?e.errors.mobilePhone=null:e.errors.mobilePhone="请填写",e.form.mobilePhone&&(/^\d{11}$/.test(e.form.mobilePhone)?e.errors.mobilePhone=null:e.errors.mobilePhone="格式不正确"),e.defaultAddrTxt?e.errors.defaultAddrTxt=null:e.errors.defaultAddrTxt="请填写",t=[],i()(e.errors).map(function(r){var o=e.errors[r]&&e.errors[r].toString();o&&t.push(o)}),!t.length){r.next=7;break}return r.abrupt("return",!1);case 7:if(e.form.resType){r.next=10;break}return e.$dialog.alert({message:"请选择回收类型"}),r.abrupt("return",!1);case 10:if(e.form.resAmount){r.next=13;break}return e.$dialog.alert({message:"请选择回收数量"}),r.abrupt("return",!1);case 13:if(e.resImages&&e.resImages.length){r.next=16;break}return e.$dialog.alert({message:"请上传回收照片"}),r.abrupt("return",!1);case 16:if("boolean"==typeof e.form.takeGarbageFlag){r.next=19;break}return e.$dialog.alert({message:"请选择是否带仍垃圾"}),r.abrupt("return",!1);case 19:if(!e.form.doorServStartTime||!e.form.doorServEndTime){r.next=24;break}if(!(new Date(e.form.doorServEndTime).getTime()-new Date(e.form.doorServStartTime).getTime()<=0)){r.next=24;break}return e.$dialog.alert({message:"上门服务时间选择不合理，请重新选择"}),r.abrupt("return",!1);case 24:return e.form.resImages=e.resImages.join(","),o={complexId:e.form.addr.complexId,doorInfo:e.form.addr.doorInfo},e.id&&(o.addrId=e.form.addr.addrId),e.form.addr=o,console.log(e.form),a=e.id?"updateRecyle":"createRecyle",r.next=32,e.$ajax(a,e.form,{showSuccessMsg:!0});case 32:e.$router.push("/owner");case 33:case"end":return r.stop()}},r,e)}))()},clickDefaultAddrHandle:function(){this.searchDialog=a()({},{show:!1},this.form.addr.complexVo||{}),this.searchDialog.show=!0},searchHandle:function(e){this.form.addr=a()({},this.form.addr,{complexVo:e,complexId:e.id}),this.defaultAddrTxt=e.addrDetail+e.complexName}}},v={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{attrs:{id:"publish-container"}},[t("van-nav-bar",{attrs:{title:e.title,"left-text":"返回","right-text":"","left-arrow":""},on:{"click-left":e.onClickLeft}}),e._v(" "),t("div",{staticClass:"detail-content"},[t("van-cell-group",[t("van-cell",{attrs:{title:"回收类型",value:e.formatType(e.form.resType)},on:{click:function(r){e.resTypesPopShow=!0}}}),e._v(" "),t("van-cell",{attrs:{title:"数量",value:e.formatAmount(e.form.resAmount)},on:{click:function(r){e.amountPopShow=!0}}}),e._v(" "),t("van-cell",{attrs:{title:"照片"}},[!e.resImages||e.resImages.length<3?t("van-uploader",{attrs:{slot:"right-icon","after-read":e.onRead},slot:"right-icon"},[t("van-button",{attrs:{type:"default",size:"small"}},[e._v("上传")])],1):e._e()],1),e._v(" "),e.resImages&&e.resImages.length?t("van-cell",[e._l(e.resImages,function(e,r){return t("div",{key:r,staticClass:"resImage"},[t("img",{attrs:{src:e}})])}),e._v(" "),t("van-button",{attrs:{slot:"right-icon",type:"default",size:"small"},on:{click:function(r){e.handlePreviw(e.resImages)}},slot:"right-icon"},[e._v("预览")])],2):e._e(),e._v(" "),t("van-field",{attrs:{label:"住址",required:"",readonly:"",rows:"1",type:"textarea",autosize:"",placeholder:"请输入您的小区地址","error-message":e.errors.defaultAddrTxt},on:{focus:function(r){return r.preventDefault(),e.clickDefaultAddrHandle(r)}},model:{value:e.defaultAddrTxt,callback:function(r){e.defaultAddrTxt=r},expression:"defaultAddrTxt"}}),e._v(" "),t("van-field",{attrs:{label:"门牌号",required:"",rows:"1",type:"textarea",autosize:"",placeholder:"请输入您的小区门牌号","error-message":e.errors.doorInfo},model:{value:e.form.addr.doorInfo,callback:function(r){e.$set(e.form.addr,"doorInfo",r)},expression:"form.addr.doorInfo"}}),e._v(" "),t("van-cell",{attrs:{title:"是否帮忙带扔垃圾",value:e.formatTakegarbage(e.form.takeGarbageFlag)},on:{click:function(r){e.takeGarbagePopShow=!0}}}),e._v(" "),t("van-cell",{attrs:{title:"上门服务时间段起点",value:e.form.doorServStartTime||"任意时间段"},on:{click:function(r){e.doorServStartTimePopShow=!0}}}),e._v(" "),t("van-cell",{attrs:{title:"上门服务时间段止点",value:e.form.doorServEndTime||"任意时间段"},on:{click:function(r){e.doorServEndTimePopShow=!0}}}),e._v(" "),t("van-field",{attrs:{label:"手机号",required:"",placeholder:"请输入手机号","error-message":e.errors.mobilePhone},model:{value:e.form.mobilePhone,callback:function(r){e.$set(e.form,"mobilePhone",r)},expression:"form.mobilePhone"}}),e._v(" "),t("van-field",{attrs:{label:"备注",type:"textarea",placeholder:"请输入备注",rows:"2",autosize:""},model:{value:e.form.resNote,callback:function(r){e.$set(e.form,"resNote",r)},expression:"form.resNote"}})],1),e._v(" "),t("van-row",[t("van-col",{attrs:{span:"24"}},[t("van-button",{attrs:{type:"primary",block:""},on:{click:e.submit}},[e._v(e._s(e.btnTxt))])],1)],1),e._v(" "),t("van-popup",{attrs:{position:"bottom"},model:{value:e.resTypesPopShow,callback:function(r){e.resTypesPopShow=r},expression:"resTypesPopShow"}},[t("van-picker",{attrs:{columns:e.resTypes,"value-key":"label","show-toolbar":"",title:"回收类型"},on:{confirm:e.handleRestypeConfirm,cancel:function(r){e.resTypesPopShow=!1}}})],1),e._v(" "),t("van-popup",{attrs:{position:"bottom"},model:{value:e.amountPopShow,callback:function(r){e.amountPopShow=r},expression:"amountPopShow"}},[t("van-picker",{attrs:{columns:e.amounts,"value-key":"label","show-toolbar":"",title:"数量"},on:{confirm:e.handleAmountConfirm,cancel:function(r){e.amountPopShow=!1}}})],1),e._v(" "),t("van-popup",{attrs:{position:"bottom"},model:{value:e.takeGarbagePopShow,callback:function(r){e.takeGarbagePopShow=r},expression:"takeGarbagePopShow"}},[t("van-picker",{attrs:{columns:e.takeGarbages,"value-key":"label","show-toolbar":"",title:"带扔垃圾"},on:{confirm:e.handleTakeGarbageConfirm,cancel:function(r){e.takeGarbagePopShow=!1}}})],1),e._v(" "),t("van-popup",{attrs:{position:"bottom"},model:{value:e.doorServStartTimePopShow,callback:function(r){e.doorServStartTimePopShow=r},expression:"doorServStartTimePopShow"}},[t("van-datetime-picker",{attrs:{type:"datetime","min-date":new Date,formatter:e.formatter},on:{confirm:e.handleDoorServStartTimeConfirm,cancel:function(r){e.doorServStartTimePopShow=!1}},model:{value:e.startDate,callback:function(r){e.startDate=r},expression:"startDate"}})],1),e._v(" "),t("van-popup",{attrs:{position:"bottom"},model:{value:e.doorServEndTimePopShow,callback:function(r){e.doorServEndTimePopShow=r},expression:"doorServEndTimePopShow"}},[t("van-datetime-picker",{attrs:{type:"datetime","min-date":e.startDate,formatter:e.formatter},on:{confirm:e.handledoorServEndTimeConfirm,cancel:function(r){e.doorServEndTimePopShow=!1}},model:{value:e.endDate,callback:function(r){e.endDate=r},expression:"endDate"}})],1)],1),e._v(" "),e.loading?t("van-loading",{staticClass:"loading",attrs:{color:"white"}}):e._e(),e._v(" "),t("search",{attrs:{show:e.searchDialog.show,"province-id":e.searchDialog.provinceId,"city-id":e.searchDialog.cityId,"area-id":e.searchDialog.areaId},on:{"update:show":function(r){e.$set(e.searchDialog,"show",r)},select:e.searchHandle}})],1)},staticRenderFns:[]};var g=t("VU/8")(h,v,!1,function(e){t("Vp9S")},"data-v-7106a0e6",null);r.default=g.exports},Vp9S:function(e,r){}});
//# sourceMappingURL=7.65f0f8b91c198665213e.js.map