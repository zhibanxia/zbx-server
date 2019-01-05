<template>
<div>
   <van-popup v-model="show" :close-on-click-overlay="false" :overlay="true" position="right" get-container="#app" :lazy-render="false" @click-overlay="cancel" :overlay-style="{'backgroundColor': 'rgba(0,0,0,0.8)'}">
    <div class="usersearch-wrap">
      <div class="search-block">
        <van-row>
          <van-col span="24">用户类型</van-col>
        </van-row>
        <van-row>
          <van-col><van-button @click="toggerField('userType',1)" size="small" :class="{'search-checked' : form.userType === 1}">业主</van-button></van-col>
          <van-col offset="1"><van-button @click="toggerField('userType',2)" size="small" :class="{'search-checked' : form.userType === 2}">回收员</van-button></van-col>
        </van-row>
      </div>
      <div class="search-block">
        <van-row>
          <van-col span="24">用户状态</van-col>
        </van-row>
        <van-row>
          <van-col><van-button @click="toggerField('userStatus',1)" size="small" :class="{'search-checked' : form.userStatus === 1}">正常</van-button></van-col>
          <van-col offset="1"><van-button @click="toggerField('userStatus',2)" size="small" :class="{'search-checked' : form.userStatus === 2}">待提交审核</van-button></van-col>
          <van-col offset="1"><van-button @click="toggerField('userStatus',3)" size="small" :class="{'search-checked' : form.userStatus === 3}">审核不通过</van-button></van-col>
        </van-row>
        <van-row>
          <van-col><van-button @click="toggerField('userStatus',4)" size="small" :class="{'search-checked' : form.userStatus === 4}">审核中</van-button></van-col>
          <van-col offset="1"><van-button @click="toggerField('userStatus',5)" size="small" :class="{'search-checked' : form.userStatus === 5}">禁用</van-button></van-col>
        </van-row>
      </div>

      <div class="search-block">
        <van-row>
          <van-col span="24">搜索类型</van-col>
        </van-row>
        <van-row>
          <van-col><van-button @click="toggerField('searchType',1)" size="small" :class="{'search-checked' : form.searchType === 1}">昵称</van-button></van-col>
          <van-col offset="1"><van-button @click="toggerField('searchType',2)" size="small" :class="{'search-checked' : form.searchType === 2}">手机号</van-button></van-col>
        </van-row>
      </div>

      <div class="search-block"  v-if="form.searchType">
        <van-row>
          <van-col>搜索内容</van-col>
        </van-row>
        <van-row>
          <van-col span="20"><van-field id="searchContent" v-model="form.searchContent" placeholder="请输入内容" /></van-col>
        </van-row>
      </div>
    </div>
    <div class="btn">
      <van-row>
        <van-col span="12"><van-button @click="reset" size="small">重置</van-button></van-col>
        <van-col span="12"><van-button @click="sure" type="warning" size="small">确定</van-button></van-col>
      </van-row>
    </div>
  </van-popup>
</div>
 
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean
    },
    search: {
      type: Object
    }
  },
  watch: {
    'show' (val) {
      val && this.$nextTick(() => {
        this.form = Object.assign({}, this.form, this.search)
      })
    }
  },
  data() {
    return {
      form: {
        userType: '',
        userStatus: '',
        searchType: '',
        searchContent: ''
      }
    }
  },
  methods: {
    toggerField(field, val) {
      if (this.form[field] === val) {
        // this.form = Object.assign({}, this.form, {field: ''})
        this.form[field] = ''
        return false
      }
      // this.form = Object.assign({}, this.form, {field: +val})
      this.form[field] = +val
    },
    reset() {
      this.form = {
        userType: '',
        userStatus: '',
        searchType: '',
        searchContent: ''
      }
    },
    cancel() {
      this.$emit('update:show', false)
    },
    sure() {
      if (!this.form.searchType) {
        this.form.searchContent = ''
      }
      if (Boolean(this.form.searchContent) !== Boolean(this.form.searchType)) {
        this.$toast('请输入搜索内容')
        document.getElementById('searchContent').focus()
        return false
      }
      this.cancel()
      this.$emit('search', this.form)
    }
  }
}
</script>
<style lang="less" scoped>
.usersearch-wrap {
  width: 100%;
  height: 100%;
  position: relative;
  margin: 10px;
  padding-top: 1px;
  .search-checked {
    border: 1px solid #f44;
    color: #f44;
  }
  .search-block {
    margin: 20px 0;
    .van-row {
      line-height: 30px;
      .van-col {
        margin-top: 5px;
        margin-bottom: 0;
      }
    }
    .van-field {
      border: 1px solid #f8f8f8;
      border-radius: 5px;
      line-height: 30px;
      padding: 0 4px;
    }
  }
  
}
.van-popup {
  height: 100%;
  width: 80%;
  overflow: hidden;
  .btn {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 50px;
    line-height: 50px;
    .van-button {
      width: 100%;
      height: 100%;
      line-height: 50px;
    }
  }
}
</style>
