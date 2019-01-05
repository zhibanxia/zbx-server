<template>
  <div id="user-recyler">
    <van-row>
      <van-col span="24" class="title">我的关注</van-col>
    </van-row>
    <van-cell-group class="content">
      <!-- 回收人员 -->
      <van-field
        v-for="(item, index) in villages"
        :key="index"
        v-model="item.village"
        label="服务小区"
        required
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

      <van-cell title="电话通知订单"  :value="formatVoiceNotifyFlag(form.voiceNotifyFlag)" @click="voiceNotifyFlagPopShow = true"/>
      <van-cell title="微信通知订单"  :value="formatWxNotifyFlag(form.wxNotifyFlag)" @click="wxNotifyFlagPopShow = true"/>
   </van-cell-group>
    <van-row>
      <van-col span="24">
        <van-button type="primary" block @click="submit">修改信息</van-button>
      </van-col>
      <van-col span="24" style="margin-top:20px;">
        <van-button block @click="onClickLeft">返回</van-button>
      </van-col>
    </van-row>
  
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
import {VOICE_NOTIFYFLAG} from '@/utils/constant'
const ERROR_MESSAGE = '请填写'
export default {
  data () {
    return {
      form: {
        focusAddrList: [],
        // 电话通知订单
        voiceNotifyFlag: 1,
        // 微信通知订单
        wxNotifyFlag: 1
      },
      errors: {
        focusAddrList: [],
        villages: []
      },
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
      wxNotifyFlags:VOICE_NOTIFYFLAG,
      wxNotifyFlagPopShow: false
    }
  },
  created () {
    this.getRecylerInfo()
  },
  methods: {
    getRecylerInfo () {
      this.$ajax('getRecylerInfo').then(res => {
        const defaultAddr = res.data.defaultAddr || {}
        // if (!defaultAddr.complexVo) {
        //   defaultAddr.complexVo = {}
        // }
        if (!res.data.focusAddrList) {
          res.data.focusAddrList = []
        }
    
        const { focusAddrList, mobilePhone, verifyLogo, wxNotifyFlag, voiceNotifyFlag } = res.data
        
        this.form = Object.assign({}, this.form, {focusAddrList, mobilePhone, verifyLogo, defaultAddr, wxNotifyFlag, voiceNotifyFlag })
        // 关注小区 过滤 具体地址和complexId
        if (focusAddrList && focusAddrList.length) {
          this.villages = focusAddrList.map(v => ({ village: v.complexVo.addrDetail + v.complexVo.complexName, complexVo: v.complexVo }))
        }
      })
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
        this.villages.push({})
      }
      // 执行删除
      else {
        this.villages.splice(index, 1)
      }
    },
    onClickLeft () {
      this.$router.go(-1)
    },
    /**
     * 点击提交信息
     */
    async submit () {
      
      // 回收人员，关注小区验证
      this.errors.villages.length = 0
      this.villages.map(item => {
        item.village ? (item.errorMessage = null) : (item.errorMessage = ERROR_MESSAGE)
        this.errors.villages.push(item.errorMessage)
      })
      // 错误信息提示
      let hasError = []
      Object.keys(this.errors).map((key) => {
        let err = this.errors[key] && (this.errors[key] instanceof Array && this.errors[key].join(''))
        err && hasError.push(err)
      })
      if (hasError.length) {
        return false
      }
      
      // let params = {
      //   mobilePhone: this.form.mobilePhone,
      //   defaultAddr: {
      //     doorInfo: this.form.defaultAddr.doorInfo,
      //     complexId: this.form.defaultAddr.complexId
      //   }
      // }
      // let params = object.assign({}, this.form)
      // if (this.type === 2) {
      //   params.verifyLogo = this.form.verifyLogo
      //   let _focusAdrs = []
      //   this.form.focusAddrList.map(item => {
      //     _focusAdrs.push({complexId: item.complexId})
      //   })
      //   params.focusAddrList = _focusAdrs
      //   params.voiceNotifyFlag = this.form.voiceNotifyFlag
      // }

      await this.$ajax('addUserDetail', this.form)
      await this.$dialog.alert({message: '操作成功'})
      window.location.href = '/'
    },
   
    // 关注小区点击进行搜索
    clickFocusAddrHandle (item, index) {
      let focusAddr = item.complexVo || {}
      this.searchDialog = Object.assign({}, {show: false}, focusAddr)
      this.searchDialog.type = 'focus'
      this.searchDialog.index = index
      this.searchDialog.show = true
    },
    // 小区搜索结果
    searchHandle (item) {
      // 关注小区
      let index = this.searchDialog.index
      this.villages[index] = {
        village: item.addrDetail + item.complexName,
        complexVo: item
      }
      this.form.focusAddrList[index] = {complexId: item.id}
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
    }
  }
}
</script>
<style lang="less" scoped>
#user-recyler {
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
