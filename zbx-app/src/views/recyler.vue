<template>
<div id="recyler-wrap">
<van-nav-bar :title="active === 0 ? '回收列表' : '我的回收'" />
<van-pull-refresh v-model="loading" @refresh="onRefresh" class="recyler-content">
  <van-list v-model="loading" :finished="finished" @load="getList">
    <recyle-list :data="data" :biztype="params.bizType" @click="handleClick"></recyle-list>
  </van-list>
</van-pull-refresh>
<van-tabbar v-model="active" @change="handleTabbar">
  <van-tabbar-item icon="wap-home">回收列表</van-tabbar-item>
  <van-tabbar-item icon="records" :dot="isupdate">我的回收</van-tabbar-item>
</van-tabbar>
</div>
</template>
<script>
import RecyleList from './components/RecyleList'
export default {
  components: {
    RecyleList
  },
  data () {
    return {
      data: [],
      active: 0,
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
  watch: {
    // '$route' (to, from) {  
    //   if (from.fullPath.indexOf('recyler/detail') !== -1) {
    //     if (to.query.update) {
    //       this.isupdate = true
    //     }
    //   }
    // }
  },
  methods: {
    // 回收人员查询所有的列表
    async getList () {
      this.loading = true
      await this.$ajax('recyleList', this.params).then(res => {
        // 数据请求完成
        this.finished = true
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
      }else {
        // 查看回收人员的历史列表
        this.params.bizType = 2
        this.params.page = 1
        this.isupdate = false
      }
      this.getList() 
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
}
</style>

