<template>
<div id="owner-container">
  <van-nav-bar title="我的发布" right-text="发布回收" @click-right="onClickRight"/>
  <van-pull-refresh v-model="loading" @refresh="onRefresh" class="recyler-content" v-if="data.length">
    <recyle-list :data="data" :biztype="params.bizType" @click="handleClick"></recyle-list>
    <p class="loadmore" @click="getList" v-if="!finished">点击加载更多</p>
    <p v-else>没有更多了</p>
  </van-pull-refresh>
  <empty v-else></empty>
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
      loading: false,
      finished: false,
      params: {
        page: 1,
        size: 10,
        bizType: 3
      }
    }
  },
  created () {
    this.getList()
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
