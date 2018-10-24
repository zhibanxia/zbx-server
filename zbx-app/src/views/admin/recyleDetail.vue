/*
 * @Author: wangmangmang 
 * @Date: 2018-10-07 11:16:29 
 * @Last Modified by: wangmangmang
 * @Last Modified time: 2018-10-19 11:16:52
 * 回收人员进入详情页
 */

<template>
  <div id="recyler-detail">
    <detail :data="data" @backup="handlerBack"></detail>
  </div>
</template>
<script>
import Detail from './../components/Detail'
import areaList from '@/utils/area'
export default {
  components: {
    Detail
  },
  data () {
    return {
      params: {
        id: this.$route.params.id
      },
      data: {}
    }
  },
  watch: {
    '$route' (to, from) {
      this.params.id = this.$route.params.id
      // this.params.biztype = +this.$route.params.biztype
    }
  },
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
    /**
     * 点击返回
     */
    handlerBack () {
      this.$router.push(`/admin`)
    }
  },
  created () {
    this.getDetail()
  }
}
</script>
