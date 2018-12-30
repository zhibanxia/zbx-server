<template>
<div>
   <van-popup v-model="show" :overlay="true"  get-container="#app" :lazy-render="false" :overlay-style="{'backgroundColor': '#fff'}">
    <div class="search-wrap">
      <van-nav-bar title="小区搜索" left-text="返回" right-text="" left-arrow @click-left="onCancel" />
      <div class="top">
        <div class="area" @click="areaSelectShow = true">
          <p v-if="areatxt">{{areatxt}}<van-icon name="arrow-down" /></p>
          <p v-else>请选择<van-icon name="arrow-down" /></p>
        </div>
        <van-search
          id="search"
          ref="search"
          v-model="seaval"
          placeholder="请输入小区名字"
          show-action
          v-focus
          @search="onSearch">
            <div slot="action" @click="onSearch">搜索</div>
        </van-search>
      </div>
      <div class="content">
        <ul class="search-list" v-if="list.length > 0">
          <li v-for="item in list" :key="item.addrDetail" @click="selectComplexHandle(item)">
            <van-icon name="location" />
            <div class="addr">
              <p>{{item.complexName}}</p>
              <p>{{item.addrDetail}}</p>
            </div>
          </li>
        </ul>
        <p v-else class="nodata">暂无数据</p>
      </div>
     </div>
    
  </van-popup>
  <!-- 根据id 展示默认值 -->
  <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
    <van-area :area-list="areaList" :value="areaObj.areaId" @confirm="handleAreaSelect" @cancel="areaSelectShow = false"/>
  </van-popup>
</div>
 
</template>
<script>
import areaList from '@/utils/area'
import {DEFAULT_ADDR } from '@/utils/constant'
export default {
  props: {
    show: {
      type: Boolean
    },
    provinceId: {
      type: String
    },
    cityId: {
      type: String
    },
    areaId: {
      type: String
    }
  },
  directives: {
    focus: {
      // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  watch: {
    'show' (val) {
      val && this.$nextTick(() => {
        this.areaObj = Object.assign({}, {provinceId: this.provinceId, cityId: this.cityId, areaId: this.areaId})
        if (!this.areaObj.areaId) {
          this.areaObj = DEFAULT_ADDR
        }
        this.seaval = ''
        this.list = []
        // 根据areaId 得出areatxt
        this.areatxt = this.areaList.county_list[this.areaObj.areaId]
        this.onSearch()
        document.getElementById('search').focus()
        // setTimeout(() => {
        //   document.getElementById('search').focus()
        // }, 180)
      })
    }
  },
  data () {
    return {
      areatxt: '', // 选择地区的名称
      seaval: '',  // 查找内容
      list: [],  // 查找结果
      areaSelectShow: false, // 地区选择弹出层展示
      areaList: areaList,  // 地区列表
      areaObj: {} // 地区对象
    }
  },
  methods: {
   
    async onSearch () {
      if (!this.areaObj.areaId) {
        this.$toast('请选择地区')
        return false
      }
      let params = {
        complexName: this.seaval,
        provinceId: this.areaObj.provinceId,
        cityId: this.areaObj.cityId,
        areaId: this.areaObj.areaId
      }
      await this.$ajax('searchByComplexName', params).then(res => {
        this.list = res.data || []
      })
    },
    onCancel () {
      this.$emit('update:show', false)
    },
    // 选择小区
    selectComplexHandle (item) {
      // let complexId = item.complexId
      this.$emit('select', item)
      this.$emit('update:show', false)
    },

    /**
     * 处理地址选择器 确认后的回调
     */
    handleAreaSelect (item) {
      let area = []
      let areaCode = []
      item.map(item => {
        areaCode.push(item.code)
        area.push(item.name)
      })
      this.areatxt = area[2] // 市级名称
      this.areaObj = {
        provinceId: areaCode[0],
        cityId: areaCode[1],
        areaId: areaCode[2]
      }
      this.areaSelectShow = false
      this.onSearch()
    },
  }
}
</script>
<style lang="less" scoped>
.van-nav-bar__left {
  left: 0!important;
}
.van-popup {
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.search-wrap {
  color: #333;
  position: relative;
  width: 100%;
  height: 100%;
  overflow: scroll;
  & > .top {
    // position: fixed;
    // top: 0;
    // left: 0;
    position: relative;
    padding: 0 10px;
    height: 44px;
    background: rgb(242, 242, 242);
    z-index: 2;
    & > .area {
      display: inline-block;
      position: absolute;
      left: 10px;
      top: 0;
      width: 60px;
      padding-right: 3px;
      & > p {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        line-height: 44px;
        margin: 0;
      }
      // & > i {
      //   position: absolute;
      //   right: 3px;
      //   top: 50%;
      //   margin-top: -8px;
      // }
    }
    & > .van-search {
      padding-left: 60px;
      background: none;
    }
  }
  & > .content {
    // padding-top: 44px;
    li {
      height: 60px;
      
      display: flex;
      align-items: center;
      margin: 0 10px;
      justify-content: center;
      & > i {
        flex: 1;
        color: #666;
      }
      & > .addr {
        flex: 9;
        overflow: hidden;
        & > p {
          margin: 0;
          text-overflow: ellipsis;
          white-space: nowrap;
          overflow: hidden;
          &:nth-of-type(2) {
            font-size: 10px;
            color: #999;
          }
        }
      }
      border-bottom: 1px solid #f9f9f9;
      // &:after {
      //   content: ' ';
      //   position: absolute;
      //   pointer-events: none;
      //   box-sizing: border-box;
      //   top: -50%;
      //   left: -50%;
      //   right: -50%;
      //   bottom: -50%;
      //   -webkit-transform: scale(0.5);
      //   transform: scale(0.5);
      //   border: 0 solid #ebedf0;
      // }
    }
  }
}
.nodata {
  font-size: 20px;
  line-height: 200px;
  text-align: center;
  color: #666;
}
</style>


