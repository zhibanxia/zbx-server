<template>
  <div id="user-container">
    <van-nav-bar title="用户详情" left-text="返回" right-text="" left-arrow @click-left="onClickLeft" />
    <div class="detail-content">
      <van-cell-group>
        <van-cell title="昵称"  :value="data.wxNickName"/>
        <van-cell title="微信头像">
          <img :src="data.wxLogo" class="logo" @click="handlePreviw([data.wxLogo])"/>
        </van-cell>
        <!-- 回收 -->
        <van-cell title="上传头像" v-if="usertype === 2">
          <img :src="data.verifyLogo" class="logo" @click="handlePreviw([data.verifyLogo])"/>
          <!-- <van-button slot="right-icon" @click="handlePreviw([data.verifyLogo])" type="default" size="small">预览</van-button> -->
        </van-cell>
        
        <van-cell title="联系电话"  :value="data.mobilePhone"/>
        <!-- 业主 -->
        <van-cell title="业主住址" icon="location" :value="formatArea(data.defaultAddr)" v-if="usertype === 1 && data.defaultAddr"/>

        <!-- 回收 -->
        
        <!-- <div  v-if="usertype === 2" class="focus-items"> -->
          <h2 class="focus-items" v-if="usertype === 2">关注小区</h2>
          <van-cell v-if="usertype === 2" title="小区地址"  :value="formatArea(item)" v-for="(item, index) in data.focusAddrList" :key="'focus'+index"/>
        <!-- </div> -->
        <!-- <van-cell title="关注小区" icon="location" v-if="usertype === 2">
          
        </van-cell> -->

        <van-cell title="用户状态" :value="formatStatus(data.status)"/>
      </van-cell-group>
    </div>
    
    <van-row style="margin-top:30px">
      <!-- 回收人员待审核状态 -->
      <van-col span="24" v-if="usertype === 2 && data.status === 4">
        <van-button block @click="handleRecyler(true)">审核通过</van-button>
        <van-button type="primary" block @click="handleRecyler(false)" style="margin-top:20px;">审核不通过</van-button>
      </van-col>
      <!-- 其他情况：禁用与恢复 -->
      <van-col span="24" v-if="(data.status !== 6 && data.status !== 5) || data.status === 5">
        <van-button type="primary" block @click="handleYezhu(data.status)" v-if="data.status !== 6 && data.status !== 5">禁用账号</van-button>
        <van-button type="primary" block @click="handleYezhu(1)" v-if="data.status === 5">恢复账号</van-button>
      </van-col>
    </van-row>
  </div>
</template>
<script>
import {USER_STATUS4ADMIN} from '@/utils/constant'
import areaList from '@/utils/area'
import { ImagePreview } from 'vant'
export default {
  data () {
    return {
      id: this.$route.params.id,
      usertype: +this.$route.params.userType,
      data: {
        status: +this.$route.params.userStatus
      }
    }
  },
  watch: {
    '$route' (to, from) {
      if (to.params.id && to.params.userType && to.params.userStatus) {
        this.id = this.$route.params.id
        this.usertype = +this.$route.params.userType
        this.data.status = +this.$route.params.userStatus
        this.getUserDetail()
      }
    }
  },
  created () {
    this.getUserDetail()
  },
  methods: {
    async getUserDetail () {
      let url = this.usertype === 1 ? 'getOwnerInfo' : this.usertype === 2 ? 'getRecylerInfo' : ''
      if (!url) {
        window.location.href = '/#/error'
        return
      }
      await this.$ajax(url, {id: this.id}).then(res => {
        this.data = Object.assign({}, this.data, res.data)
      })
    },
    formatStatus (status) {
      let stt = USER_STATUS4ADMIN.filter(item => item.id === +status)
      return stt.length ? stt[0].label : '--'
    },
    formatArea (item) {
      let area = []
      let provice = areaList.province_list[item.provinceId]
      let city = areaList.city_list[item.cityId]
      let areaId = areaList.county_list[item.areaId]
      area.push(provice)
      provice !== city && area.push(city)
      area.push(areaId)
      area.push(item.addrDetail)
      return area.join('')
    },
    onClickLeft () {
      this.$router.push(`/admin/?user=1&timestamp=${new Date().getTime()}`)
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
    },
    /**
     * 审核回收人员
     */
    async handleRecyler (flag) {
      await this.$dialog.confirm({message: `确定审核${flag ? '通过' : '不通过'}吗？`})
      await this.$ajax('verifyHuishou', {id: this.id, verifyResult: flag})
      await this.$dialog.alert({message: `操作成功`})
      this.$router.push(`/admin/?user=1&timestamp=${new Date().getTime()}`)
    },
    /**
     * 账号 禁用 恢复
     */
    async handleYezhu (status) {
      await this.$dialog.confirm({message: `确定${status === 5 ? '恢复' : '禁用'}吗？`})
      await this.$ajax('modifyUserStatus', {id: this.id, status: status})
      await this.$dialog.alert({message: `操作成功`})
      this.$router.push(`/admin/?user=1&timestamp=${new Date().getTime()}`)
    }
   }
}
</script>
<style lang="less">
#user-container {
  font-size: 16px;
  color: #333;
  .van-nav-bar .van-nav-bar__left {
    left: 0;
  }
  .van-cell__value {
    flex: 4;
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
    .focus-items {
      // h2 {
        margin: 0;
        font-weight: 400;
        font-size: 14px;
        color: rgba(69,90,100,.6);
        padding: 40px 15px 15px 0;
      // }
    }
    .van-cell {
      // height: 60px;
      // line-height: 60px;
    }
  }
  .logo {
    width: 40px;
    height: 40px;
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
