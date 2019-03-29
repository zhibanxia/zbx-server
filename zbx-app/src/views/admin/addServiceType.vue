/*
 * @Author: wangmangmang 
 * @Date: 2019-03-23 19:03:25 
 * @Last Modified by: wangmangmang
 * @Last Modified time: 2019-03-23 21:11:46
 * @desc: 添加服务类型
 */
<template>
  <div id="publish-container">
    <van-nav-bar :title="title" left-text="返回" right-text="" left-arrow @click-left="onClickLeft" />
    <div class="detail-content">
      <van-cell-group>
        <!-- 服务类型 -->
        <van-cell title="服务类型" >
          
        </van-cell>
        <van-radio-group v-model="form.type">
          <van-radio :name="item.id" v-for="item in servicestype" :key="item.id">{{item.label}}</van-radio>
        </van-radio-group>

      <!-- 服务地区 -->
       <van-cell title="服务地区" >
          <van-icon name="add" slot="right-icon" @click="areaSelectShow = true"/>
       </van-cell>
      <van-field
        v-for="(item, index) in form.serviceAreas"
        :key="index"
        :value="item"
        class="blur"
        label="服务地区"
        required
        readonly
        rows="1"
        type="textarea"
        autosize
        @click-icon="removeVillage(item, index)"
      >
        <van-icon name="delete" slot="icon"/>
      </van-field>

      <!-- 信息头像 -->
      <van-cell title="信息头像" class="van-field van-cell photo">
        <div style="text-align: left;" ><img v-if="form.iconImg" :src="form.iconImg"/></div>
        <template slot="right-icon">
          <van-uploader :after-read="updateIconImg">
            <van-icon name="photograph"/>
          </van-uploader>
        </template>
      </van-cell>
      <!--  -->
      

      <!-- 图片 -->
      <van-cell title="服务详情图片" >
        <van-uploader :after-read="updateDetailImg" slot="right-icon" v-if="!form.detailImgs || form.detailImgs.length < 3">
          <van-button type="default" size="small">上传</van-button>
        </van-uploader>
      </van-cell>
      <van-cell v-if="form.detailImgs && form.detailImgs.length">
        <div class="resImage" v-for="(item, index) in form.detailImgs" :key="index">
          <img :src="item"/>
          <van-icon name="delete" slot="icon" @click="delDetailImg(index)"/>
        </div>
        
      </van-cell>
      

      <!-- 服务介绍 -->
      <van-cell title="服务介绍" class="van-field van-cell photo">
      </van-cell>
      <rich-editor v-model="form.serviceDesc"
        placeholder="请输入服务介绍"
        :value="form.serviceDesc"
        id="rich-editor-wrapper"
        :toolbarOptions="[['bold', 'italic', 'underline', 'strike'], [{ list: 'ordered' }, { list: 'bullet' }]]"
      >
      </rich-editor>
      <van-field
          v-model="form.contactPhone"
          label="联系电话"
          required
          placeholder="请输入联系电话"
        />
        <van-field
          v-model="form.contactName"
          label="联系人"
          type="textarea"
          placeholder="请输入联系人"
          rows="1"
          autosize
        />
        <!-- 服务星级 -->
        <van-cell title="服务星级" >
        </van-cell>
        <van-radio-group v-model="form.servStarValue">
          <van-radio :name="item.id" v-for="item in servicesstar" :key="item.id">{{item.label}}</van-radio>
        </van-radio-group>
      </van-cell-group>
      <van-row>
        <van-col span="24">
          <van-button type="primary" block @click="submit">保存</van-button>
        </van-col>
      </van-row>
      
    </div>
    <van-loading v-if="loading" class="loading" color="white"/>
    <van-popup v-model="areaSelectShow" position="bottom" :overlay="true">
      <van-area :area-list="areaList" value="110101" @confirm="handleAreaSelect" @cancel="areaSelectShow = false"/>
    </van-popup>
  </div>
</template>
<script>
import { SERVICES_TYPE, SERVICES_STAR} from '@/utils/constant';
import RichEditor from '@/components/RichEditor';

import areaList from '@/utils/area'
export default {
  components: {
    RichEditor
  },
  data() {
    return {
      title: '添加服务地区',
      servicestype: SERVICES_TYPE,
      servicesstar: SERVICES_STAR,
      form: {
        serviceAreas: [],
        detailImgs: [],
        serviceDesc: '2222'
      },
      areaSelectShow: false,
      areaList: areaList,
      loading: false
    }
  },
  methods: {
    onClickLeft () {
      this.$router.go(-1)
    },
    /**
     * 服务小区，最多新增5个，除第一个外，其他right-icon 删除操作
     */
    removeVillage (item, index) {
      this.form.serviceAreas.splice(index, 1)
    },
    /**
     * 处理地址选择器 确认后的回调
     */
    handleAreaSelect (item) {
      let area = []
      item.map(item => {
        area.indexOf(item.name) === -1 && (area.push(item.name))
      })
      this.form.serviceAreas.push(area.join('')) 
      this.areaSelectShow = false
    },
    /**
     * 上传详情
     */
    async updateDetailImg (file) {
      this.loading = true
      let params = {
        imgBase64: file.content,
        bizType: 2
      }
      await this.$ajax('upload', params).then((res) => {
        this.form.detailImgs.push(res.data)
      }).finally(() => {
        this.loading = false
      })
    },
    // 删除详情图片
    delDetailImg(index) {
      this.form.detailImgs.splice(index, 1)
    },
    // 上传头像
    async updateIconImg (file) {
      this.loading = true
      let params = {
        imgBase64: file.content,
        bizType: 2
      }
      await this.$ajax('upload', params).then((res) => {
        this.form.iconImg = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    submit() {
      console.log(this.form);
    }
  }
}
</script>
