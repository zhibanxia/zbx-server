/*
 * @Author: wangmangmang 
 * @Date: 2018-10-07 11:16:29 
 * @Last Modified by: wangmangmang
 * @Last Modified time: 2018-10-28 19:15:28
 * 回收人员进入详情页
 */

<template>
  <div id="recyler-detail">
    <detail :data="data" @backup="handlerBack">
      <!-- 处于已发布状态下的待回收列表，可以确认回收 -->
      <van-row v-if="data.resStatus == 1 && params.biztype === 1" style="margin-top:30px">
        <van-col span="24">
          <van-button type="primary" block @click="submit">确认回收</van-button>
        </van-col>
      </van-row>
    </detail>
  </div>
</template>
<script>
import Detail from './components/Detail'
import areaList from '@/utils/area'
export default {
  components: {
    Detail
  },
  data () {
    return {
      params: {
        id: this.$route.query.id,
        biztype: +this.$route.query.biztype
      },
      data: {}
    }
  },
  // watch: {
  //   '$route' (to, from) {
  //     this.params.id = this.$route.query.id
  //     this.params.biztype = +this.$route.query.biztype
  //   }
  // },
  methods: {
    async getDetail () {
      await this.$ajax('recyleDetail', this.params).then(res => {
        this.data = res.data
        this.data.resImages = this.data.resImages.split(',')
        let area = []
        let provice = areaList.province_list[this.data.addr.provinceId]
        let city = areaList.city_list[this.data.addr.cityId]
        let areaId = areaList.county_list[this.data.addr.areaId]
        area.push(provice)
        provice !== city && area.push(city)
        area.push(areaId)
        area.push(this.data.addr.addrDetail)
        this.data.area = area.join('')
      })
    },
    async submit () {
      await this.$dialog.confirm({message: '您确认回收吗？'})
      let params = {
        id: this.params.id
      }
      await this.$ajax('confirmRecycle', params, {showSuccessMsg: true}).then(() => {
        this.$dialog.alert({message: '确认成功'}).then(() => {
          // 有新的回收了
          this.$router.push(`/recyler?update=true`)
        })
      })
    },
    /**
     * 点击返回
     */
    handlerBack () {
      this.$router.push(`/recyler?bizType=${this.params.biztype}`)
      // this.$router.go(-1)
    }
  },
  created () {
    this.getDetail()
  }
}
</script>
