<template>
<div id="admin-wrap">
<van-nav-bar :title="active === 0 ? '回收列表' : '用户列表'" />
<van-pull-refresh v-model="loading" @refresh="onRefresh" class="admin-content">
  <!-- 回收列表 -->
  <van-list v-model="loading" :finished="finished" @load="getRecyleList" v-if="active === 0">
    <recyle-list :data="data" @click="handleRecylerClick"></recyle-list>
  </van-list>
  <!-- 用户列表 -->
  <van-list v-else :finished="finished" @load="getAllUser">
    <div v-for="(item, index) in data" :key="index" @click="handleUserClick(item)">
      <van-row class="user-item">
        <van-col span="24">
          <!-- <van-row class="item-title">
            <van-col>
              <img :src="item.wxLogo" class="logo"/>
              <span>{{item.wxNickName}}</span>  
            </van-col>
          </van-row> -->
          <van-row class="item-content">
            <van-col span="4"><img :src="item.wxLogo" class="resImage"/></van-col>
            <van-col span="12">
              <van-row><span>昵称：{{item.wxNickName}}</span></van-row>
              <van-row><span>类型：{{formatType(item.userType)}}</span></van-row>
              
              <!-- <van-row v-if="item.completeRecycleTime"><span>回收时间：{{item.completeRecycleTime}}</span></van-row> -->
            </van-col>
            <van-col span="8" class="status" :class="'status' + item.userStatus">{{formatStatus(item.userStatus)}}</van-col>
          </van-row>
        </van-col>
      </van-row>
      </div>
  </van-list>
</van-pull-refresh>
<van-tabbar v-model="active" @change="handleTabbar">
  <van-tabbar-item icon="wap-home">回收列表</van-tabbar-item>
  <van-tabbar-item icon="records">用户列表</van-tabbar-item>
</van-tabbar>
</div>
</template>
<script>
import RecyleList from './../components/RecyleList'
import {USER_STATUS4ADMIN} from '@/utils/constant'
export default {
  components: {
    RecyleList
  },
  data () {
    return {
      data: [],
      active: this.$route.query.user ? 1 : 0,
      loading: false,
      finished: false,
      params: {
        page: 1,
        size: 10
      }
    }
  },
  watch: {
    // '$route' (to) {
    //   debugger
    //   if (to.query.user) {
    //     console.log(1)
    //     this.active = 1
    //     // this.handleTabbar(this.active)
    //   }
    // }
  },
  created () {
    this.handleTabbar(this.active)
  },
  methods: {
    /**
     * 回收列表
     */
    async getRecyleList () {
      if (this.loading) return
      this.loading = true
      let params = {
        status: '',
        page: this.params.page,
        size: this.params.size
      }
      await this.$ajax('recylerListByAdmin', params).then(res => {
        this.finished = true
        this.params.page === 1 ? this.data = res.data : this.data = this.data.concat(res.data)
        this.params.page++
      }).finally(() => {
        this.loading = false
      })
    },
    /**
     * 用户列表
     */
    async getAllUser () {
      if (this.loading) return
      this.loading = true
      await this.$ajax('getAllUser', this.params).then(res => {
        this.finished = true
        this.params.page === 1 ? this.data = res.data.list : this.data = this.data.concat(res.data.list)
        this.params.page++
      }).finally(() => {
        this.loading = false
      })
    },
    /**
     * 上拉刷新
     */
    onRefresh () {
      this.params.page = 1
      if (this.active === 0) {
        this.getRecyleList()
        return
      }
      this.getAllUser()
    },
    handleTabbar (index) {
      this.active = index
      this.params.page = 1
      if (index === 0) {
        this.getRecyleList()
        return
      }
      this.getAllUser()
    },
    /**
     * 点击进入回收详情页
     */
    handleRecylerClick (item) {
      this.$router.push(`/admin/recyledetail/${item.id}`)
    },
    /**
     * 点击进入用户详情页
     */
    handleUserClick (item) {
      this.$router.push(`/admin/userdetail/${item.id}/${item.userType}/${item.userStatus}`)
    },
    formatType (type) {
      return `${type === 1 ? '业主' : type === 2 ? '回收人员' : '管理员'}`
    },
    formatStatus (status) {
      let stt = USER_STATUS4ADMIN.filter(item => item.id === +status)
      return stt.length ? stt[0].label : '--'
    },
  }
}
</script>
<style lang="less" scoped>
.user-item {
  padding: 20px 0;
  border-bottom: 1px solid #f8f8f8;
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
</style>
