<template>
<div id="register">
  <!-- <el-row class="title">
    请问您是?
  </el-row>
  <el-row class="user-type">
    <el-col :span="20">回收人员</el-col>
  </el-row>
  <el-row class="user-type">
    <el-col :span="20">业主</el-col>
  </el-row>

  <el-button type="primary">下一步</el-button> -->
  <van-row>
    <van-col span="24" class="title">Hi,{{username}}</van-col>
    </van-row>
    <van-row>
      <van-col span="24" class="title">请选择身份</van-col>
    </van-row>
  <van-row v-for="item in type" :key="item.id" class="user-type"  >
    <van-col span="24" :class="{'active': item.id === selectType}"><div @click="handleClick(item)">{{item.label}}</div></van-col>
  </van-row>
  <van-row>
    <van-col span="24" class="tips"><div v-if="selectName">当前选择: {{selectName}}</div></van-col>
  </van-row>
  <van-button type="primary" size="large" @click="handleNext">下一步</van-button>
</div>
</template>
<script>
import {getCookie} from '@/utils/auth'
export default {
  data () {
    return {
      username: getCookie('username'),
      type: [
        {label: '业主', id: 1},
        {label: '回收人员', id: 2}
      ],
      selectType: '',
      selectName: ''
    }
  },
  methods: {
    handleClick (item) {
      this.selectType = +item.id
      this.selectName = item.label
    },
    handleNext () {
      if (!(this.selectType && this.selectName)) {
        this.$dialog.alert({title: '提示', message: '请选择身份'})
        return false
      }
      this.$router.push('/register/recyler/' + this.selectType)
    }
  }
}
</script>
<style lang="less" scoped>
  #register {
    padding-top: 50px;
    .title {
      line-height: 50px;
      color: #666;
      text-align: center;
      font-size: 40px;
      // margin: 50px 0 0;
      font-weight: 200;
    }
    .user-type {
      color: #333;
      background-color: #fff;
      border: 1px solid #eee;
      border-radius: 5px;
      line-height: 50px;
      font-size: 25px;
      text-align: center;
      margin: 25px 0;
      font-weight: 200;
      & > .active {
        background-color: rgba(18, 140, 17, 0.32);
        border-color: rgba(18, 140, 17, 0.32);
        color: #fff;
      }
    }
    .tips {
      margin: 100px 0 5px 0;
      color: #666;
      text-align: center;
    }
    .van-button {
    }
  }
</style>
