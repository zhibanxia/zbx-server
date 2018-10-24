<template>
  <div class="recyle-list">
   <div class="recyle-item" v-for="item in data" :key="'item'+item.id" @click="handleClick(item)">
      <van-row>
      <van-col span="24">
        <van-row class="item-title">
          <!-- <van-col>
            <img :src="item.logo" class="logo"/>
            <span>{{item.nickname}}</span>  
          </van-col> -->
          <van-col offset="1">
            {{item.publishTime}}
          </van-col>
        </van-row>
        <van-row class="item-content">
          <van-col span="4" ><img :src="resImage(item)" class="resImage"/></van-col>
          <van-col span="16">
            <van-row><span>类型：{{formatType(item.resType)}}</span></van-row>
            <van-row><span>数量：{{formatAmount(item.resAmount)}}</span></van-row>
            <van-row v-if="item.completeRecycleTime"><span>回收时间：{{item.completeRecycleTime}}</span></van-row>
          </van-col>
          <van-col span="4" class="status" :class="'status' + item.resStatus">{{formatStatus(item.resStatus)}}</van-col>
        </van-row>
      </van-col>
    </van-row>
   </div>
  </div>
</template>
<script>
import {dateFomatter} from '@/utils/formatter'
import {RECYLE_TYPE, RECYLE_STATUS, RECYLE_AMOUNT} from '@/utils/constant'

export default {
  props: {
    data: {
      type: Array
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
    handleClick (item) {
      // this.$router.push(`/detail/${item.id}/${biztype}`)
      this.$emit('click', item)
    },
    resImage (item) {
      if (item.resImages) {
        return item.resImages.split(',')[0]
      }
    }
  }
}
</script>
<style lang="less" scoped>
.recyle-list {
  color: #333;
  .recyle-item {
    padding: 10px 0 20px 0;
    border-bottom: 1px solid #f8f8f8;
    &:last-child {
      border-bottom: none;
    }
    .item-title {
      padding-bottom: 8px;
      color: #666;
    }
    .item-content {
      display: flex;
      justify-content: center;
      align-items: center;
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
  .resImage {
    width: 40px;
    height: 40px;
  }
}
</style>
