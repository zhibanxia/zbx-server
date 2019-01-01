<template>
  <div id="register-recyler">
    <van-row>
      <van-col span="24" class="title">请补充以下信息</van-col>
    </van-row>
    <van-cell-group class="content">
      <!-- 回收人员 -->
      <van-cell title="头像" class="van-field van-cell photo" v-if="type === 2">
        <div style="text-align: left;" ><img v-if="form.verifyLogo" :src="form.verifyLogo"/></div>
        <template slot="right-icon">
          <van-uploader :after-read="onRead">
            <van-icon name="photograph"/>
          </van-uploader>
        </template>
      </van-cell>

      <!-- <van-field
        v-model="area"
        label="所在地区"
        readonly
        required
        autosize
        placeholder="选择 省 / 市 / 区"
        :error-message="errors.area"
        @focus.prevent="areaSelectShow = true"
      /> -->

      <!-- 业主或回收人员住址 -->
      <van-field
        v-model="defaultAddrTxt"
        v-if="type === 1"
        label="住址"
        required
        readonly
        rows="1"
        type="textarea"
        autosize
        @focus.prevent="clickDefaultAddrHandle"
        placeholder="请输入您的小区地址"
        :error-message="errors.defaultAddrTxt"
      >
      </van-field>

      <van-field
        v-model="form.defaultAddr.doorInfo"
        v-if="type === 1"
        label="门牌号"
        required
        rows="1"
        type="textarea"
        autosize
        placeholder="请输入您的小区门牌号"
        :error-message="errors.doorInfo"
      >
      </van-field>

      <!-- 回收人员 -->
      <van-field
        v-for="(item, index) in villages"
        :key="index"
        v-model="item.village"
        class="blur"
        v-if="type === 2"
        label="服务小区"
        required
        readonly
        rows="1"
        type="textarea"
        autosize
        placeholder="请输入小区详细地址"
        :error-message="errors.villages[index]"
        @click-icon="handleAddOrRemoveVillage(item, index)"
        @focus.prevent="clickFocusAddrHandle(item, index)"
      >
        <van-icon name="add" slot="icon" v-if="index === 0"/>
        <van-icon name="delete" slot="icon" v-else/>
      </van-field>

      <van-field
        class="blur"
        v-model="form.mobilePhone"
        label="手机号"
        required
        placeholder="请输入手机号"
        :error-message="errors.mobilePhone"
      />

      <van-cell v-if="type === 2" title="电话通知订单"  :value="formatVoiceNotifyFlag(form.voiceNotifyFlag)" @click="voiceNotifyFlagPopShow = true"/>
      <van-cell v-if="type === 2" title="微信通知订单"  :value="formatWxNotifyFlag(form.wxNotifyFlag)" @click="wxNotifyFlagPopShow = true"/>
      
   </van-cell-group>
    <van-row>
      <van-col span="24">
        <van-button type="primary" block @click="submit">提交信息</van-button>
      </van-col>
    </van-row>
    <!-- <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
      <van-area :area-list="areaList" @confirm="handleAreaSelect" @cancel="areaSelectShow = false"/>
    </van-popup> -->
    <van-loading v-if="loading" class="loading" color="white"/>
    <search :show.sync="searchDialog.show" :province-id="searchDialog.provinceId" :city-id="searchDialog.cityId" :area-id="searchDialog.areaId" @select="searchHandle"></search>
    <!-- 电话通知订单 -->
    <van-popup v-model="voiceNotifyFlagPopShow" position="bottom">
      <van-picker :columns="[{values: voiceNotifyFlags, defaultIndex: form.voiceNotifyFlag ? 0: 1}]" value-key="label" @confirm="handleVoiceNotifyFlagConfirm" show-toolbar title="电话通知订单" @cancel="voiceNotifyFlagPopShow = false"/>
    </van-popup>
    <!-- 微信通知订单 -->
    <van-popup v-model="wxNotifyFlagPopShow" position="bottom">
      <van-picker :columns="[{values: wxNotifyFlags, defaultIndex: form.wxNotifyFlag ? 0 : 1}]" value-key="label" @confirm="handleWxNotifyFlagConfirm" show-toolbar title="微信通知订单" @cancel="wxNotifyFlagPopShow = false"/>
    </van-popup>
  </div>
</template>
<script>
import {DEFAULT_ADDR, VOICE_NOTIFYFLAG} from '@/utils/constant'
import areaList from '@/utils/area'
const ERROR_MESSAGE = '请填写'
export default {
  data () {
    return {
      type: +this.$route.query.type,
      defaultAddrTxt: '',
      areaCode: {},
      area: '',
      loading: false,
      form: {
        verifyLogo: '',
        focusAddrList: [],
        mobilePhone: '',
        // 默认地址
        defaultAddr: {
          complexId: '',
          doorInfo: ''
        },
        // 电话通知订单
        voiceNotifyFlag: 1,
        // 微信通知订单
        wxNotifyFlag: 1
      },
      errors: {
        area: '',
        mobilePhone: '',
        focusAddrList: [],
        defaultAddrTxt: '',
        villages: [],
        doorInfo: ''
      },
      areaList: areaList,
      areaSelectShow: false,
      villages: [ // 小区地址
        { village: '' }
      ],
      // 查询框
      searchDialog: {
        show: false,
        provinceId: '',
        cityId: '',
        areaId: ''
      },
      // 电话通知订单
      voiceNotifyFlags: VOICE_NOTIFYFLAG,
      voiceNotifyFlagPopShow: false,
      // 微信通知订单
      wxNotifyFlags: VOICE_NOTIFYFLAG,
      wxNotifyFlagPopShow: false
    }
  },
  created () {
    // console.log(this.$route.query.type)
    this.getType()
    this.getRecylerInfo()
  },
  methods: {
    getRecylerInfo () {
      this.$ajax('getRecylerInfo').then(res => {
        const defaultAddr = res.data.defaultAddr || {}
        if (!defaultAddr.complexVo) {
          defaultAddr.complexVo = {}
        }
        if (!res.data.focusAddrList) {
          res.data.focusAddrList = []
        }
        const { focusAddrList, mobilePhone, verifyLogo, wxNotifyFlag, voiceNotifyFlag } = res.data
        
        this.form = Object.assign({}, this.form, {focusAddrList, mobilePhone, verifyLogo, defaultAddr, wxNotifyFlag, voiceNotifyFlag })
        
        // 住址 回填 具体地址
        this.defaultAddrTxt = defaultAddr.complexVo.addrDetail ? defaultAddr.complexVo.addrDetail + defaultAddr.doorInfo : ''
        // 关注小区 过滤 具体地址和complexId
        if (focusAddrList && focusAddrList.length) {
          this.villages = focusAddrList.map(v => ({ village: v.complexVo.addrDetail + v.complexVo.complexName, complexVo: v.complexVo }))
        }
      })
    },
    getType () {
      this.$ajax('getUserType').then(res => {
        let userInfo = res.data
        let type = userInfo.userType
        this.type = type
      })
    },
    /**
     * 上传头像，需要调用接口上传
     */
    async onRead (file) {
      this.loading = true
      let params = {
        imgBase64: file.content,
        bizType: 1
      }
      await this.$ajax('upload', params).then((res) => {
        this.form.verifyLogo = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    /**
     * 处理地址选择器 确认后的回调
     */
    // handleAreaSelect (item) {
    //   let area = []
    //   let areaCode = []

    //   item.map(item => {
    //     areaCode.push(item.code)
    //     area.indexOf(item.name) === -1 && (area.push(item.name))
    //   })
    //   this.area = area.join('/')
    //   this.areaCode = {
    //     provinceId: areaCode[0],
    //     cityId: areaCode[1],
    //     areaId: areaCode[2]
    //   }
    //   this.areaSelectShow = false
    // },
    /**
     * 服务小区，最多新增5个，除第一个外，其他right-icon 删除操作
     */
    handleAddOrRemoveVillage (item, index) {
      // 第一个，执行添加操作
      if (index === 0) {
        if (this.villages.length >= 3) {
          this.$dialog.alert({ title: '提醒', message: '服务小区最多添加3个'})
          return false
        }
        this.villages.push({})
      }
      // 执行删除
      else {
        this.villages.splice(index, 1)
      }
    },
    /**
     * 点击提交信息
     */
    async submit () {
      // 手机不为空验证
      this.form.mobilePhone ? (this.errors.mobilePhone = null) : (this.errors.mobilePhone = ERROR_MESSAGE)
      // 手机格式验证
      if (this.form.mobilePhone) {
        !/^\d{11}$/.test(this.form.mobilePhone) ? (this.errors.mobilePhone = '格式不正确') : (this.errors.mobilePhone = null)
      }

      // 默认住址验证
      this.defaultAddrTxt ? (this.errors.defaultAddrTxt = null) : (this.errors.defaultAddrTxt = ERROR_MESSAGE)

      // 门牌号验证
      this.form.defaultAddr.doorInfo ? (this.errors.doorInfo = null) : (this.errors.doorInfo = ERROR_MESSAGE)

      // 回收人员，关注小区验证
      if (this.type === 2) {
        // 验证
        this.errors.villages.length = 0
        this.villages.map(item => {
          item.village ? (item.errorMessage = null) : (item.errorMessage = ERROR_MESSAGE)
          this.errors.villages.push(item.errorMessage)
        })
      }
      // 错误信息提示
      let hasError = []
      Object.keys(this.errors).map((key) => {
        let err = this.errors[key] && this.errors[key].join('')
        err && hasError.push(err)
      })
      if (hasError.length) {
        return false
      }
      
      let params = {
        mobilePhone: this.form.mobilePhone,
        defaultAddr: {
          doorInfo: this.form.defaultAddr.doorInfo,
          complexId: this.form.defaultAddr.complexId
        }
      }
      if (this.type === 2) {
        params.verifyLogo = this.form.verifyLogo
        let _focusAdrs = []
        this.form.focusAddrList.map(item => {
          _focusAdrs.push({complexId: item.complexId})
        })
        params.focusAddrList = _focusAdrs
        params.voiceNotifyFlag = this.form.voiceNotifyFlag
        params.wxNotifyFlag = this.form.wxNotifyFlag
      }

      await this.$ajax('addUserDetail', params)
      await this.$dialog.alert({message: '操作成功'})
      window.location.href = '/'
    },
    // 默认地址小区点击进行搜索
    clickDefaultAddrHandle () {
      this.searchDialog = Object.assign({}, {show: false}, this.form.defaultAddr.complexVo || {})
      this.searchDialog.show = true
      this.searchDialog.type = 'default'
      // document.getElementsByClassName('blur').blur()
      // document.getElementById('search').focus()
    },
    // 关注小区点击进行搜索
    clickFocusAddrHandle (item, index) {
      let focusAddr = item.complexVo || {}
      this.searchDialog = Object.assign({}, {show: false}, focusAddr)
      this.searchDialog.type = 'focus'
      this.searchDialog.index = index
      this.searchDialog.show = true
      // document.getElementsByClassName('blur').blur()
      // document.getElementById('search').focus()
    },
    // 小区搜索结果
    searchHandle (item) {
      // 住址
      if (this.searchDialog.type === 'default') {
        this.form.defaultAddr = Object.assign({}, this.form.defaultAddr, {complexVo: item, complexId: item.id})
        this.defaultAddrTxt = item.addrDetail + item.complexName
      }
      // 关注小区
      else if (this.searchDialog.type === 'focus') {
        let index = this.searchDialog.index
        this.villages[index] = {
          village: item.addrDetail + item.complexName,
          complexVo: item
        }
        this.form.focusAddrList[index] = {complexId: item.id}
      }
    },
    formatVoiceNotifyFlag (val) {
      let stt = VOICE_NOTIFYFLAG.filter(item => item.id === val)
      return stt.length ? stt[0].label : '请选择'
    },
    handleVoiceNotifyFlagConfirm (item) {
      this.form.voiceNotifyFlag = item[0].id
      this.voiceNotifyFlagPopShow = false
    },
    formatWxNotifyFlag (val) {
      let stt = VOICE_NOTIFYFLAG.filter(item => item.id === val)
      return stt.length ? stt[0].label : '请选择'
    },
    handleWxNotifyFlagConfirm (item) {
      this.form.wxNotifyFlag = item[0].id
      this.wxNotifyFlagPopShow = false
    },
  }
}
</script>
<style lang="less" scoped>
#register-recyler {
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
