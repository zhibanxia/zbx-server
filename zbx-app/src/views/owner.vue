<template>
<div id="owner-container">
  <van-nav-bar title="我的发布" right-text="发布回收" @click-right="onClickRight"/>
  <van-pull-refresh v-model="loading" @refresh="onRefresh" class="recyler-content">
  <van-list v-model="loading" :finished="finished" @load="getList">
    <recyle-list :data="data" :biztype="params.bizType" @click="handleClick"></recyle-list>
  </van-list>
</van-pull-refresh>
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
      loading: false,
      finished: false,
      params: {
        page: 1,
        size: 10,
        bizType: 3
      }
    }
  },
  methods: {
    onClickRight () {
      this.$router.push(`/owner/publish`)
    },
    // 回收人员查询所有的列表
    async getList () {
      this.loading = true
      await this.$ajax('recyleList', this.params).then(res => {
        // 数据请求完成
        this.finished = true
        this.params.page === 1 ? this.data = res.data : this.data.list = this.data.list.concat(res.data.list)
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
    /**
     * 点击进入详情页
     */
    handleClick (item) {
      this.$router.push(`/owner/detail/?id=${item.id}&biztype=${this.params.bizType}`)
    }
  }
}
</script>
<style lang="less">
#owner-container {
  .van-nav-bar {
    .van-nav-bar__text {
      padding: 0;
    }
  }
  
}
</style>
