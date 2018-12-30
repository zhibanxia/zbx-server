<template>
<div id="recyler-wrap">
<van-nav-bar :title="active === 0 ? '回收列表' : '我的回收'" />
<van-pull-refresh v-model="loading" @refresh="onRefresh" class="recyler-content" v-if="data.length">
  <!-- <van-list v-model="loading" :finished="finished" @load="getList"> -->
    <recyle-list :data="data" :biztype="params.bizType" @click="handleClick"></recyle-list>
    <p class="loadmore" @click="getList" v-if="!finished">点击加载更多</p>
    <p class="loadmore" v-else>没有更多了</p>
  <!-- </van-list> -->
</van-pull-refresh>
<empty v-else></empty>
<van-tabbar v-model="active" @change="handleTabbar">
  <van-tabbar-item icon="wap-home">回收列表</van-tabbar-item>
  <van-tabbar-item icon="records" :dot="isupdate">我的回收</van-tabbar-item>
  <van-tabbar-item icon="contact">个人中心</van-tabbar-item>
</van-tabbar>
<van-loading v-if="loading" class="loading" color="white"/>
</div>
</template>
<script>
import RecyleList from './components/RecyleList'
import Empty from './components/Empty'
export default {
  components: {
    RecyleList,
    Empty
  },
  data () {
    return {
      data: [],
      active: +this.$route.query.bizType === 2 ? 1 : 0,
      loading: false,
      finished: false,
      params: {
        page: 1,
        size: 10,
        bizType: 1
      },
      isupdate: !!this.$route.query.update
    }
  },
  created () {
    this.getList()
  },
  methods: {
    // 回收人员查询所有的列表
    async getList () {
      this.loading = true
      await this.$ajax('recyleList', this.params).then(res => {
        // 数据请求完成
        if (res.data.length === 0) {
          this.finished = true
        }
        this.params.page === 1 ? this.data = res.data : this.data = this.data.concat(res.data)
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
      this.getList()
    },
    handleTabbar (index) {
      if (index === 0) {
       this.params.bizType = 1
       this.params.page = 1
       this.getList() 
      }else if (index === 1) {
        // 查看回收人员的历史列表
        this.params.bizType = 2
        this.params.page = 1
        this.isupdate = false
        this.getList() 
      }
      else {
        window.location.href = '/#/usercenter/recylerarea'
      }
    },
    /**
     * 点击进入详情页
     */
    handleClick (item) {
      this.$router.push(`/recyler/detail/?id=${item.id}&biztype=${this.params.bizType}`)
    }
  }
}
</script>
<style lang="less" scoped>
#recyler-wrap {
  padding-bottom: 50px;
  .loadmore {
    padding: 0;
    line-height: 20px;
    text-align: center;
    color: #666;
  }
  .loading {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 50px;
    height: 50px;
    margin: -25px 0 0 -25px;
    padding: 10px;
    border-radius: 3px;
    background-color: rgba(0, 0, 0, .5);
  }
}
</style>

