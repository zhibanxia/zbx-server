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

      <van-field
        v-model="area"
        label="所在地区"
        readonly
        required
        autosize
        placeholder="选择 省 / 市 / 区"
        :error-message="errors.area"
        @focus.prevent="areaSelectShow = true"
      />

      <!-- 业主或回收人员住址 -->
      <van-field
        v-model="defaultAddr"
        v-if="type === 1 || type === 2"
        label="住址"
        required
        placeholder="请输入您的小区地址"
        :error-message="errors.defaultAddr"
      >
      </van-field>

      <!-- 回收人员 -->
      <van-field
        v-for="(item, index) in villages"
        :key="index"
        v-model="item.village"
        v-if="type === 2"
        label="服务小区"
        required
        placeholder="请输入小区详细地址"
        :error-message="errors.villages[index]"
        @click-icon="handleAddOrRemoveVillage(item, index)"
      >
        <van-icon name="add" slot="icon" v-if="index === 0"/>
        <van-icon name="delete" slot="icon" v-else/>
      </van-field>

      <van-field
        v-model="form.mobilePhone"
        label="手机号"
        required
        placeholder="请输入手机号"
        :error-message="errors.mobilePhone"
      />
    </van-cell-group>
    <van-row>
      <van-col span="24">
        <van-button type="primary" block @click="submit">提交信息</van-button>
      </van-col>
    </van-row>
    <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
      <van-area :area-list="areaList" @confirm="handleAreaSelect" @cancel="areaSelectShow = false"/>
    </van-popup>
    <van-loading v-if="loading" class="loading" color="white"/>
  </div>
</template>
<script>
import {DEFAULT_ADDR} from '@/utils/constant'
import areaList from '@/utils/area'
const ERROR_MESSAGE = '请填写'
export default {
  data () {
    return {
      type: +this.$route.query.type,
      defaultAddr: '',
      areaCode: {},
      area: '',
      loading: false,
      form: {
        verifyLogo: '',
        focusAddrList: [],
        mobilePhone: '',
        defaultAddr: ''
      },
      errors: {
        area: '',
        mobilePhone: '',
        focusAddrList: [],
        defaultAddr: '',
        villages: []
      },
      areaList: areaList,
      areaSelectShow: false,
      villages: [ // 小区地址
        { village: '' }
      ]
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
        const defaultAddr = res.data.defaultAddr || DEFAULT_ADDR

        let area = []
        let provice = areaList.province_list[defaultAddr.provinceId]
        let city = areaList.city_list[defaultAddr.cityId]
        let areaId = areaList.county_list[defaultAddr.areaId]
        area.push(provice)
        provice !== city && area.push(city)
        area.push(areaId)
        this.area = area.join('/')

        this.defaultAddr = defaultAddr.addrDetail
        this.areaCode = defaultAddr
        const { focusAddrList, mobilePhone, verifyLogo } = res.data
        this.form = { focusAddrList, mobilePhone, verifyLogo, defaultAddr }

        // this.form.focusAddrList =  [this.defaultAddr]
        // this.form.mobilePhone = res.data.mobilePhone
        // this.form.verifyLogo = res.data.verifyLogo
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
    handleAreaSelect (item) {
      let area = []
      let areaCode = []

      item.map(item => {
        areaCode.push(item.code)
        area.indexOf(item.name) === -1 && (area.push(item.name))
      })
      this.area = area.join('/')
      this.areaCode = {
        provinceId: areaCode[0],
        cityId: areaCode[1],
        areaId: areaCode[2]
      }
      this.areaSelectShow = false
    },
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
        this.villages.push({village: '' })
      }
      // 执行删除
      else {
        this.villages.splice(index, 1);
      }
    },
    /**
     * 点击提交信息
     */
    async submit () {
      this.form.focusAddrList = []
      // 省市区 验证
      this.area ? (this.errors.area = null) : (this.errors.area = ERROR_MESSAGE)
      // 手机不为空验证
      this.form.mobilePhone ? (this.errors.mobilePhone = null) : (this.errors.mobilePhone = ERROR_MESSAGE)
      // 手机格式验证
      if (this.form.mobilePhone) {
        !/^\d{11}$/.test(this.form.mobilePhone) ? (this.errors.mobilePhone = '格式不正确') : (this.errors.mobilePhone = null)
      }

      // 默认住址验证
      this.defaultAddr ? (this.errors.defaultAddr = null) : (this.errors.defaultAddr = ERROR_MESSAGE)

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

      // 住址
      this.form.defaultAddr = Object.assign({}, this.areaCode, {addrDetail: this.defaultAddr})

      // 回收人员补充信息
      if (this.type === 2) {
        this.villages.map(item => {
          this.form.focusAddrList.push(Object.assign({}, this.areaCode, {addrId: '', addrDetail: item.village}))
        })
      }

      let params = {
        mobilePhone: this.form.mobilePhone,
        defaultAddr: this.form.defaultAddr
      }
      if (this.type === 2) {
        params.verifyLogo = this.form.verifyLogo
        params.focusAddrList = this.form.focusAddrList
      }

      await this.$ajax('addUserDetail', params)
      await this.$dialog.alert({message: '操作成功'})
      window.location.href = '/'
    }
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
