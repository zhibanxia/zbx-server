<template>
  <div id="register-recyler">
    <!-- <van-row>
      <van-col span="24" class="title">Hi,{{username}}</van-col>
    </van-row> -->
    <van-row>
      <van-col span="24" class="title">请补充以下信息</van-col>
    </van-row>
    
    <van-cell-group class="content">
      <van-cell title="头像" class="van-field photo">
        <template>
          <div style="text-align: left;"><img :src="form.avatar"/></div>
        </template>
        <template slot="right-icon">
          <van-uploader :after-read="onRead">
            <van-icon name="photograph" />
          </van-uploader>
        </template>
      </van-cell>
      <van-field
        v-model="form.area"
        label="地区"
        required
        placeholder="选择 省 / 市 / 区"
        :error-message="errors.area"
        @focus="areaSelectShow = true"
      />
      <!-- 回收人员 -->
      <van-field
        v-for="(item, index) in villages"
        :key="index"
        v-model="item.village"
        v-if="form.type === 2"
        label="服务小区"
        required
        placeholder="请输入小区详细地址"
        :error-message="item.errorMessage"
        @click-icon="handleAddOrRemoveVillage(item, index)"
      >
        <van-icon name="add" slot="icon" v-if="index === 0"/>
        <van-icon name="delete" slot="icon" v-else/>
      </van-field>
      <!-- 业主 -->
      <van-field
        v-model="form.onwervillage"
        v-if="form.type === 1"
        label="小区地址"
        required
        placeholder="请输入您的小区地址"
        :error-message="errors.onwervillage"
      >
      </van-field>

      <van-field
        v-model="form.phone"
        label="手机号"
        required
        placeholder="请输入手机号"
        :error-message="errors.phone"
      />
      <van-field
        v-model="form.sms"
        center
        clearable
        required
        label="短信验证码"
        placeholder="请输入短信验证码"
        :error-message="errors.sms"
      >
      <van-button slot="button" size="small" type="primary" @click="sendsms" v-if="canSendsms">发送验证码</van-button>
      <van-button slot="button" size="small" disabled v-else>{{sendSmsWaitTime}}s后重试</van-button>
      </van-field>
    </van-cell-group>
    <van-row>
      <van-col span="24">
        <van-button type="primary" block @click="submit">提交信息</van-button>
      </van-col>
    </van-row>
    <van-row style="margin-top: 20px;">
      <van-col span="24">
        <van-button type="info" block @click="goPreStep">返回上一步</van-button>
      </van-col>
    </van-row>
    
    <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
      <van-area :area-list="areaList" value="110101" @confirm="handleAreaSelect" @cancel="areaSelectShow = false"/>
    </van-popup>
  </div>
</template>
<script>
import {getCookie} from '@/utils/auth'
import areaList from '@/utils/area'
const sendSmsWaitTime = 10
// const timeId = null
const ERROR_MESSAGE = '请填写'
export default {
  watch: {
    sendSmsWaitTime (val) {
      if (!val) {
        this.canSendsms = true
        this.sendSmsWaitTime = sendSmsWaitTime
        clearInterval(this.timeId)
      }
    }
  },
  data () {
    return {
      
      username: getCookie('username'),
      form: {
        type: +this.$route.params.id,
        avatar: getCookie('avatar'),
        area: '',
        villages: [],
        phone: '',
        sms: '',
        onwervillage: ''
      },
      errors: {
        area: '',
        phone: '',
        villages: [],
        sms: '',
        onwervillage: ''
      },
      areaList: areaList,
      areaSelectShow: false,
      villages: [  // 小区地址
        {village: '' }
      ],
      canSendsms: true, // 是否可以发送验证码
      sendSmsWaitTime: sendSmsWaitTime, // 发送验证码等待时间
      timeId: null
    }
  },
  methods: {
    /**
     * 上传头像，需要调用接口上传
     */
    async onRead (file) {
      // TODO 调用接口
      console.log(file)
      this.form.avatar = file.content
      // await this.$ajax('upload')
    },
    /**
     * 处理地址选择器 确认后的回调
     */
    handleAreaSelect (item) {
      debugger
      let area = []
      item.map(item => {
        area.indexOf(item.name) === -1 && (area.push(item.name))
      })
      this.form.area = area.join('/')
      this.areaSelectShow = false
    },
    /**
     * 服务小区，最多新增5个，除第一个外，其他right-icon 删除操作
     */
    handleAddOrRemoveVillage (item, index) {
      // 第一个，执行添加操作
      if (index === 0) {
        if (this.villages.length >= 5) {
          this.$dialog.alert({ title: '提醒', message: '服务小区最多添加5个'})
          return false
        }
        this.villages.push({village: '' })
      }
      // 执行删除
      else {
        this.villages.splice(index, 1);
      }
    },
    /**
     * 上一步
     */
    goPreStep () {
      this.$router.go(-1)
    },
    /**
     * 发送验证码
     */
    sendsms () {
      // TODO 调用接口
      this.$dialog.alert({message: '发送成功'})
      this.canSendsms = false
      this.timeId = setInterval(() => {
        this.sendSmsWaitTime--
      }, 1000)
    },
    /**
     * 点击提交信息
     */
    submit () {
      // 回收人员注册
      let params = {}
      this.form.area ? (this.errors.area = null) : (this.errors.area = ERROR_MESSAGE)
      !/^\d{11}$/.test(this.form.phone) ? (this.errors.phone = '格式不正确') : (this.errors.phone = null)
      this.form.phone ? (this.errors.phone = null) : (this.errors.phone = ERROR_MESSAGE)
      if (this.form.type === 2) {
        // 验证
        this.errors.villages.length = 0
        this.villages.map(item => {
          item.village ? (item.errorMessage = null) : (item.errorMessage = ERROR_MESSAGE)
          this.errors.villages.push(item.errorMessage)
        }) 
        this.form.sms ? (this.errors.sms = null) : (this.errors.sms = ERROR_MESSAGE)
        let hasError = true
        Object.keys(this.errors).map((key) => {
          hasError = this.errors[key] && this.errors[key].toString()
          if (hasError) return false
        })
        if (hasError) {
          return false
        }
        let params = Object.assign({}, this.form)
        params.villages = params.villages.join(',')
        console.log(params)
      } 
      // 业主
      else if (this.form.type === 1) {
        this.form.onwervillage ? (this.errors.onwervillage = null) : (this.errors.onwervillage = ERROR_MESSAGE)
        let params = Object.assign({}, this.form)
        console.log(params)
      }
      

      // TOOD 调用接口
      
    }
  }
}
</script>
<style lang="less" scoped>
#register-recyler {
  padding-top: 50px;
  .title {
    line-height: 50px;
    color: #666;
    text-align: center;
    font-size: 40px;
    // margin: 50px 0 0;
    font-weight: 200;
  }
  .content {
    margin: 50px 0;
    .van-cell {
      line-height: 30px;
    }
  }
  .avator {
    padding: 10px 15px;
    height: 50px;
    line-height: 50px;
    vertical-align: middle;
    & > .van-col {
      line-height: inherit;
      height: inherit;
      .van-icon {
        font-size: 20px;
      }
      & > img {
        
      }
    }
  }
  .photo {
    text-align: left;
    line-height: 50px;
    height: 70px;
    .van-icon {
      color: #999;
    }
    img {
      width: 50px;
      height: 50px;
      line-height: 50px;
    }
    
  }
}
</style>
