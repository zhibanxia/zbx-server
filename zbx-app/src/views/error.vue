<template>
  <div id="error">
    <p class="desc" v-if="statusTxt && status">账号{{statusTxt}}</p>
    <p class="desc" v-else>啊哦，出错了</p>
    <!-- 审核不通过 -->
    <van-row v-if="status === 3">
      <van-col span="10" offset="7">
        <van-button size="normal" block @click="handleClick">重新提交</van-button>
      </van-col>
    </van-row>
    <!-- 回收人员，没有提交信息 -->
    <van-row v-if="status === 2 && type === 2">
      <van-col span="10" offset="7">
        <van-button size="normal" block @click="handleClick">完善信息</van-button>
      </van-col>
    </van-row>
  </div>
</template>
<script>
import {USER_STATUS} from '@/utils/constant'
export default {
  data () {
    return {
      status: +this.$route.query.status,
      type: +this.$route.query.type
    }
  },
  computed: {
    statusTxt () {
      let st = USER_STATUS.filter(item => item.id === this.status)
      if (st.length) {
        return st[0].label
      }
      return null
    }
  },
  methods: {
    handleClick () {
      this.$router.push(`/register?type=${this.type}`)
    }
  }
}
</script>
<style lang="less" scoped>
#error {
  padding-top: 155px;
  .desc {
    background: url('data:image/png;base64,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');
    background-size: 80px 80px;
    background-position: center center;
    background-repeat: no-repeat;
    padding-top: 120px;
    font-size: 14px;
    color: #999;
    text-align: center;
    line-height: 30px;
  }
}
</style>
