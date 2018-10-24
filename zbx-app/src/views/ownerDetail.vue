/*
 * @Author: wangmangmang 
 * @Date: 2018-10-07 11:33:20 
 * @Last Modified by: wangmangmang
 * @Last Modified time: 2018-10-19 12:16:49
 * 业主进入自己的回收列表
 */


<template>
  <div id="owner-detail">
    <detail :data="data" @backup="handlerBack">
      <!-- 处于已发布状态下的回收列表，可以删除，更新，取消 -->
      <!-- 处于待回收的状态，可以更新为已回收 -->
      <van-row style="margin-top:30px" v-if="data.resStatus == 1">
        <van-col span="11">
          <van-button block @click="handleDelete" >删除</van-button>
        </van-col>
        <van-col span="11" offset="2">
          <van-button type="primary" block @click="handleUpdate" >更新</van-button>
        </van-col>
      </van-row>
      <van-row style="margin-top:30px" v-if="data.resStatus == 2">
        <van-col span="24">
          <van-button type="primary" block @click="handleComplete" >回收完成</van-button>
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
        bizType: this.$route.query.biztype
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
    /**
     * 删除
     */
    async handleDelete () {
      await this.$dialog.confirm({message: '您确定删除吗？'})
      let params = {
        id: this.params.id
      }
      await this.$ajax('delete', params).then(() => {
        this.$dialog.alert({message: '删除成功'}).then(() => {
          this.$router.push(`/owner`)
        })
      })
    },
    /**
     * 更新
     */
    handleUpdate () {
      this.$router.push(`/owner/publish/${this.params.id}`)
    },
    /**
     * 点击返回
     */
    handlerBack () {
      this.$router.push(`/owner`)
    },
    /**
     * 回收完成
     */
    async handleComplete () {
      await this.$dialog.confirm({message: '您确定回收完成了吗？'})
      let params = {
        id: this.params.id
      }
      await this.$ajax('completeRecycle', params).then(() => {
        this.$dialog.alert({message: '操作成功'}).then(() => {
          this.$router.push(`/owner`)
        })
      })
    }
  },
  created () {
    this.getDetail()
  }
}
</script>
