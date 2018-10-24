<template>
  <div id="detail-container">
    <van-nav-bar title="回收详情" left-text="返回" right-text="" left-arrow @click-left="onClickLeft" />
    <div class="detail-content">
      <van-row class="user-title">
        <van-col span="20">
          <!-- <img :src="data.userLogo" class="logo"/>
          <span>{{data.userNickname}}</span>   -->
          <span> {{formatTime(data.publishTime)}}</span>  
        </van-col>
        <van-col span="4" class="status" :class="'status' + data.resStatus">{{formatStatus(data.resStatus)}}</van-col>
      </van-row>
      <van-cell-group>
        <!-- icon="cash-on-deliver" -->
        <van-cell title="回收类型"  :value="formatType(data.resType)"/>
        <!-- icon="exchange" -->
        <van-cell title="数量"  :value="formatAmount(data.resAmount)"/>
        <!-- icon="photo" -->
        <van-cell title="照片" >
          <van-button slot="right-icon" @click="handlePreviw(data.resImages)" type="default" size="small">预览照片</van-button>
        </van-cell>
        <!-- icon="goods-collect" -->
        <van-cell title="是否帮忙带仍垃圾"  :value="data.takeGarbage ? '是' : '否'"/>
        <!-- icon="pending-deliver" -->
        <van-cell v-if="data.doorServStartTime && data.doorServEndTime" title="上门服务时间段"  :value="formatTime(data.doorServStartTime) + '~' + formatTime(data.doorServEndTime)"/>
        <!-- icon="pending-deliver" -->
        <van-cell v-else title="上门服务时间段"  value="任意时间段"/>
        <van-cell title="回收地址" icon="location" :value="data.area"/>
        <van-cell title="联系电话" icon="phone" :value="data.mobilePhone"/>
        <van-cell title="回收人员" icon="contact" v-if="data.recyleUserLogo">
          <img :src="data.recyleUserLogo" class="logo"/>{{data.recyleUserNickname}}
        </van-cell>
        <van-cell title="回收时间" v-if="data.recyleTime" icon="pending-deliver" :value="formatTime(data.recyleTime)"/>
      </van-cell-group>
    </div>
    <!-- 按钮区域 TODO -->
    <!-- 回收人员：接收下单，取消接收  接受下单：已发布状态  取消接收： 回收人员自己+待回收状态 -->

    <!-- 业主：取消发布  业主自己+已发布状态-->
    <div slot="btn"></div>
  </div>
</template>
<script>
import {dateFomatter} from '@/utils/formatter'
import {RECYLE_TYPE, RECYLE_STATUS, RECYLE_AMOUNT} from '@/utils/constant'
import areaList from '@/utils/area'
import { ImagePreview } from 'vant'
export default {
  data () {
    return {
      params: {
        id: this.$route.params.id,
        bizType: this.$route.params.biztype
      },
      data: {}
    }
  },
  methods: {
    formatTime (time) {
      return dateFomatter(new Date(time), 'yyyy-MM-dd hh:mm:ss')
    },
    formatType (type) {
      let stt = RECYLE_TYPE.filter(item => item.id === +type)
      return stt.length ? stt[0].label : '--'
    },
    formatStatus (status) {
      let stt = RECYLE_STATUS.filter(item => item.id === +status)
      return stt.length ? stt[0].label : '--'
    },
    formatAmount (amount) {
      let stt = RECYLE_AMOUNT.filter(item => item.id === +amount)
      return stt.length ? stt[0].label : '--'
    },
    async getDetail () {
      await this.$ajax('recyleDetail', this.params).then(res => {
        this.data = res.data
        this.data.resImages = this.data.resImages.split(',')
        let area = []
        let provice = areaList.province_list[this.data.addr.provinceId]
        let city = areaList.city_list[this.data.addr.cityId]
        let areaId = areaList.county_list[this.data.addr.areaId]
        area.push(provice)
        provice !== city && area.push(city)
        area.push(areaId)
        area.push(this.data.addr.addrDetail)
        this.data.area = area.join('')
      })
    },
    onClickLeft () {
      this.$router.go(-1)
    },
    /**
     * 预览照片
     */
    handlePreviw (images) {
      ImagePreview({
        images: images,
        startPosition: 1,
        onClose() {
          // do something
        }
      });
    }
   },
  created () {
    this.getDetail()
  }
}
</script>
<style lang="less">
#detail-container {
  font-size: 16px;
  color: #333;
  .van-nav-bar .van-nav-bar__left {
    left: 0;
  }
  .detail-content {
    margin-top: 20px;
    .user-title {
      font-size: 14px;
      padding-bottom: 8px;
      color: #666;
      .status {
        text-align:right;
        color: #999;
        &::before {
          display: inline-block;
          content: "";
          width: 6px;
          height: 6px;
          border-radius: 50%;
          background-color: #999;
          margin-right: 2px;
        }
        &.status1 {
          color: rgba(228, 49, 21, 0.53);
          &::before {
           background-color: rgba(228, 49, 21, 0.53) 
          }
        }
        &.status2 {
          color: rgba(68, 187, 0, 0.77);
          &::before {
           background-color: rgba(68, 187, 0, 0.77);
          }
        }
      }
    }
  }
  .logo {
    width: 10px;
    height: 10px;
    border-radius: 50%;
  }
  .van-row {
    margin: 5px 0;
  }
  .resImage {
    width: 40px;
    height: 40px;
  }
  .van-cell {
    padding-left: 0;
    padding-right: 0;
  }
  
}
</style>
