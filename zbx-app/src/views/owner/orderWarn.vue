<template>
  <div id="owner-warn">
    <van-nav-bar title="超时订单" right-text="我的发布" left-arrow @click-right="rightHandle" />
    <div v-if="infos.complexName" class="warn-title"><p>您在【<b>{{infos.complexName}}</b>】提交的回收订单较长时间没有被回收，我们帮您推荐了一些回收师傅，您可以电话联系。</p></div>
    <!-- <van-notice-bar :wrapable="true" :scrollable="false">
      您在【{{infos.complexName}}】提交的回收请求较长时间没有被回收，我们帮您推荐了一些回收师傅，您可以电话联系。
    </van-notice-bar> -->
    <van-row style="font-weight:bold;">
      <p v-for="item in header" :key="item">{{item}}</p>
    </van-row>
    <van-row v-for="(item, index) in infos.hsuRecommList" :key="index" >
      <p>{{item.contactName}}</p>
      <p>{{item.serviceDesc}}</p>
      <p>
        <a :href="'tel:'+item.contactPhone" class="btn">拨打</a>
        <!-- <van-button plain type="primary" size="mini"><a :href="item.contactPhone">拨打</a></van-button> -->
      </p>
    </van-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      infos: {},
      recId: this.$route.params.id,
      header: ['联系人', '服务描述', '联系电话']
    }
  },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      await this.$ajax('/rest/recycle/hsu_recomm', {recId: this.recId}).then(res => {
        this.infos = res.data;
        console.log(this.infos);
      });
    },
    rightHandle() {
      this.$router.push(`/owner`);
    }
  }
}
</script>
<style lang="less">
#owner-warn {
  font-size: 16px;
  color: #333;
  .warn-title {
    background: #f5f5f5;
    padding: 5px;
    margin: 10px 0;
  }
  .van-row {
    display: flex;
    align-content: center;
    justify-content: center;
    & > p {
      flex: 1;
      margin: 0 10px;
      display: flex;
      align-items: center;
      &:nth-of-type(1) {
        justify-content: flex-start;
      }
      &:nth-of-type(2) {
        flex: 2;
        text-align: center;
      }
      &:nth-of-type(3) {
        text-align: right;
        justify-content: flex-end;
      }
      .btn {
        border: 1px solid #07c160;
        padding: 2px 5px;
        border-radius: 2px;
        color: #07c160;
      }
    }
    padding: 10px 0;
    line-height: 20px;
    border-bottom: 1px solid #f5f5f5;
  }
}
</style>

