<template>
  <div id="publish-container">
    <van-nav-bar :title="title" left-text="返回" right-text="" left-arrow @click-left="onClickLeft" />
    <div class="detail-content">
      <van-cell-group>
        <!-- icon="cash-on-deliver"  -->
        <van-cell title="回收类型" :value="formatType(form.resType)" @click="resTypesPopShow = true">
        </van-cell>
        <!-- icon="exchange" -->
        <van-cell title="数量"  :value="formatAmount(form.resAmount) " @click="amountPopShow = true"/>
        <!-- icon="photo" -->
        <van-cell title="照片" >
          <van-uploader :after-read="onRead" slot="right-icon" v-if="!resImages || resImages.length < 3">
            <van-button type="default" size="small">上传</van-button>
          </van-uploader>

        </van-cell>

        <van-cell v-if="resImages && resImages.length">
          <div class="resImage" v-for="(item, index) in resImages" :key="index"><img :src="item"/></div>
          <van-button slot="right-icon" @click="handlePreviw(resImages)" type="default" size="small">预览</van-button>
        </van-cell>

        <!-- <van-field
          v-model="area"
          label="所在地区"
          required
          placeholder="选择 省 / 市 / 区"
          @focus.prevent="areaSelectShow = true"
          readonly
          :error-message="errors.area"
          autosize
        /> -->
        <van-field
        v-model="defaultAddrTxt"
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
        v-model="form.addr.doorInfo"
        label="门牌号"
        required
        rows="1"
        type="textarea"
        autosize
        placeholder="请输入您的小区门牌号"
        :error-message="errors.doorInfo"
      >
      </van-field>
      <!-- <van-field
          v-model="defaultAddr"
          label="住址"
          required
          placeholder="请输入您的小区地址"
          autosize
          :error-message="errors.defaultAddr"
      >
      </van-field> -->
        <!-- icon="goods-collect" -->
        <van-cell title="是否帮忙带扔垃圾"  :value="formatTakegarbage(form.takeGarbageFlag)" @click="takeGarbagePopShow = true"/>
        <!-- icon="pending-deliver" -->
        <van-cell title="上门服务时间段起点"  :value="form.doorServStartTime || '任意时间段'" @click="doorServStartTimePopShow = true"/>
        <!-- icon="pending-deliver" -->
        <van-cell title="上门服务时间段止点"  :value="form.doorServEndTime || '任意时间段'" @click="doorServEndTimePopShow = true"/>
        <van-field
          v-model="form.mobilePhone"
          label="手机号"
          required
          placeholder="请输入手机号"
          :error-message="errors.mobilePhone"
        />

        <van-field
          v-model="form.resNote"
          label="备注"
          type="textarea"
          placeholder="请输入备注"
          rows="2"
          autosize
        />
      </van-cell-group>
      <van-row>
        <van-col span="24">
          <van-button type="primary" block @click="submit">{{btnTxt}}</van-button>
        </van-col>
      </van-row>
      <van-popup v-model="resTypesPopShow" position="bottom">
        <van-picker :columns="resTypes" value-key="label" @confirm="handleRestypeConfirm" show-toolbar title="回收类型" @cancel="resTypesPopShow = false"/>
      </van-popup>
      <van-popup v-model="amountPopShow" position="bottom">
        <van-picker :columns="amounts"  value-key="label" @confirm="handleAmountConfirm" show-toolbar title="数量" @cancel="amountPopShow = false"/>
      </van-popup>
      <van-popup v-model="takeGarbagePopShow" position="bottom">
        <van-picker :columns="takeGarbages" value-key="label" @confirm="handleTakeGarbageConfirm" show-toolbar title="带扔垃圾" @cancel="takeGarbagePopShow = false"/>
      </van-popup>

      <van-popup v-model="doorServStartTimePopShow" position="bottom">
        <van-datetime-picker
          v-model="startDate"
          type="datetime"
          :min-date="new Date()"
          :formatter = "formatter"
          @confirm = "handleDoorServStartTimeConfirm"
          @cancel="doorServStartTimePopShow = false"
        />
      </van-popup>

      <van-popup v-model="doorServEndTimePopShow" position="bottom">
        <van-datetime-picker
          v-model="endDate"
          type="datetime"
          :min-date="startDate"
          :formatter = "formatter"
          @confirm = "handledoorServEndTimeConfirm"
          @cancel="doorServEndTimePopShow = false"
        />
      </van-popup>
      <!-- <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
        <van-area :area-list="areaList" :value="selectArea" @confirm="handleAreaSelect" @cancel="handleCancel"/>
      </van-popup> -->
    </div>
    <van-loading v-if="loading" class="loading" color="white"/>
    <search :show.sync="searchDialog.show" :province-id="searchDialog.provinceId" :city-id="searchDialog.cityId" :area-id="searchDialog.areaId" @select="searchHandle"></search>
  </div>
</template>
<script>
import {RECYLE_TYPE, RECYLE_AMOUNT, TAKE_GARBAGE } from '@/utils/constant'
import { ImagePreview } from 'vant'
import {dateFomatter} from '@/utils/formatter'
import areaList from '@/utils/area'
const ERROR_MESSAGE = '请填写'
export default {
  data () {
    return {
      resTypes: RECYLE_TYPE,
      amounts: RECYLE_AMOUNT,
      takeGarbages: TAKE_GARBAGE,
      resTypesPopShow: false,
      amountPopShow: false,
      takeGarbagePopShow: false,
      doorServStartTimePopShow: false,
      doorServEndTimePopShow: false,
      form: {addr: {}},
      startDate: new Date(),
      endDate: new Date(),
      errors: {
        defaultAddrTxt: '',
        mobilePhone: '',
        area: '',
        doorInfo: ''
      },
      areaSelectShow: false,
      areaList: areaList,
      id: this.$route.params.id,
      area: '',
      selectArea: '',
      defaultAddrTxt: '',
      loading: false,
      resImages: [],
      searchDialog: {show: false}
    }
  },
  computed: {
    title () {
      return this.id ? '更新回收' : '发布回收'
    },
    btnTxt () {
      return this.id ? '更新' : '发布'
    }
  },
  watch: {
    '$route' (to, from) {
      this.id = this.$route.params.id
    }
  },
  created () {
    this.getDetail()
  },
  methods: {
    async getDetail () {
      // 获取发布信息
      if (this.id) {
        let params = {
          id: this.id,
          bizType: 3
        }
        await this.$ajax('recyleDetail', params).then(res => {
          this.form = res.data
          // 图片
          this.resImages = this.form.resImages.split(',')
          // 地区
          this.form.addr = res.data.addr
          // 详细地址
          if (this.form.addr.complexVo) {
            this.defaultAddrTxt = this.form.addr.complexVo.addrDetail + this.form.addr.complexVo.complexName
          }
        })
        return
      }
      // 获取业主信息
      await this.$ajax('getYezhuUserInfo').then(res => {
        const defaultAddr = res.data.defaultAddr || {}
        // 电话
        this.form.mobilePhone = res.data.mobilePhone
        this.form.addr = defaultAddr
        this.form.takeGarbageFlag = false
        // 详细地址
        if (defaultAddr.complexVo) {
          this.defaultAddrTxt = defaultAddr.complexVo.addrDetail + defaultAddr.complexVo.complexName
        }
      })
    },
    handleRestypeConfirm (item) {
      this.form.resType = item.id
      this.resTypesPopShow = false
    },
    formatType (type) {
      let stt = RECYLE_TYPE.filter(item => item.id === +type)
      return stt.length ? stt[0].label : '请选择'
    },
    handleAmountConfirm (item) {
      this.form.resAmount = item.id
      this.amountPopShow = false
    },
    formatAmount (amount) {
      let stt = RECYLE_AMOUNT.filter(item => item.id === +amount)
      return stt.length ? stt[0].label : '请选择'
    },
    handleTakeGarbageConfirm (item) {
      this.form.takeGarbageFlag = item.id
      this.takeGarbagePopShow = false
    },
    formatTakegarbage (istake) {
      let stt = TAKE_GARBAGE.filter(item => item.id === istake)
      return stt.length ? stt[0].label : '请选择'
    },
    handleDoorServStartTimeConfirm (value) {
      this.form.doorServStartTime = dateFomatter(this.startDate, 'yyyy-MM-dd hh:mm:ss')
      this.doorServStartTimePopShow = false
    },
    handledoorServEndTimeConfirm (value) {
      this.form.doorServEndTime = dateFomatter(this.endDate, 'yyyy-MM-dd hh:mm:ss')
      this.doorServEndTimePopShow = false
    },
    formatter (type, value) {
      if (type === 'year') {
        return `${value}年`
      } else if (type === 'month') {
        return `${value}月`
      }
      return value
    },

    /**
     * 上传头像，需要调用接口上传
     */
    async onRead (file) {
      this.loading = true
      let params = {
        imgBase64: file.content,
        bizType: 2
      }
      this.resImages = this.resImages || []
      await this.$ajax('upload', params).then((res) => {
        this.resImages.push(res.data)
      }).finally(() => {
        this.loading = false
      })
    },
    onClickLeft () {
      this.$router.go(-1)
    },
    /**
     * 预览照片
     */
    handlePreviw (images) {
      if (!images || !images.length) {
        this.$dialog.alert({message: '您还未上传照片'})
        return false
      }
      ImagePreview({
        images: images,
        startPosition: 1,
        onClose() {
          // do something
        }
      });
    },
    /**
     * 处理地址选择器 确认后的回调
     */
    /** handleAreaSelect (item) {
      let area = []
      let areaCode = []

      item.map(item => {
        areaCode.push(item.code)
        area.indexOf(item.name) === -1 && (area.push(item.name))
      })
      // 所在地区选择
      this.area = area.join('/')
      // 地址code
      this.form.addr = Object.assign({}, this.form.addr, {
        provinceId: areaCode[0],
        cityId: areaCode[1],
        areaId: areaCode[2]
      })
      this.areaSelectShow = false
      console.log(this.form)
    },*/
    async submit () {
      
      // 手机不为空验证
      this.form.mobilePhone ? (this.errors.mobilePhone = null) : (this.errors.mobilePhone = ERROR_MESSAGE)
      // 手机格式验证
      if (this.form.mobilePhone) {
        !/^\d{11}$/.test(this.form.mobilePhone) ? (this.errors.mobilePhone = '格式不正确') : (this.errors.mobilePhone = null)
      }
      // 默认住址验证
      this.defaultAddrTxt ? (this.errors.defaultAddrTxt = null) : (this.errors.defaultAddrTxt = ERROR_MESSAGE)
      // 错误信息提示
      let hasError = []
      Object.keys(this.errors).map((key) => {
        let err = this.errors[key] && this.errors[key].toString()
        err && hasError.push(err)
      })
      if (hasError.length) {
        return false
      }

      // 弹窗提示
      if (!this.form.resType) {
        this.$dialog.alert({message: '请选择回收类型'})
        return false
      }
      if (!this.form.resAmount) {
        this.$dialog.alert({message: '请选择回收数量'})
        return false
      }
      if (!this.resImages || !this.resImages.length) {
        this.$dialog.alert({message: '请上传回收照片'})
        return false
      }
      if (typeof this.form.takeGarbageFlag !== 'boolean') {
        this.$dialog.alert({message: '请选择是否带仍垃圾'})
        return false
      }
      if (this.form.doorServStartTime && this.form.doorServEndTime) {
        var serviceTime = new Date(this.form.doorServEndTime).getTime() - new Date(this.form.doorServStartTime).getTime()
        if (serviceTime <= 0) {
          this.$dialog.alert({message: '上门服务时间选择不合理，请重新选择'})
          return false
        }
      }
      this.form.resImages = this.resImages.join(',')
      let addr = {
        complexId: this.form.addr.complexId,
        doorInfo: this.form.addr.doorInfo
      }
      // 更新操作，addr 多了参数 addrId
      if (this.id) {
        addr.addrId = this.form.addr.addrId
      }
      this.form.addr = addr
      console.log(this.form)

      let url = this.id ? 'updateRecyle' : 'createRecyle'
      await this.$ajax(url, this.form, {showSuccessMsg: true})
      this.$router.push(`/owner`)
    },
    // handleCancel () {
    //   this.areaSelectShow = false
    // },
    // 默认地址小区点击进行搜索
    clickDefaultAddrHandle () {
      this.searchDialog = Object.assign({}, {show: false}, this.form.addr.complexVo || {})
      this.searchDialog.show = true
    },
    // 小区搜索结果
    searchHandle (item) {
      // 住址
      this.form.addr = Object.assign({}, this.form.addr, {complexVo: item, complexId: item.id})
      this.defaultAddrTxt = item.addrDetail + item.complexName
    }
  }
}
</script>
<style lang="less" scoped>
#publish-container {
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
  .resImage {
    width: 40px;
    height: 40px;
    display: inline-block;
    margin-right: 10px;
    & > img {
      width: 100%;
      height: 100%;
      vertical-align: middle;
    }
  }
}
</style>
