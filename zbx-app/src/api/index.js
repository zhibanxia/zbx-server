import urlConstants from './url'
import request from './request'
import { Message } from 'element-ui'
import { changeToFormData } from './requestUtil'
import axios from 'axios'

const requests = {
  get(requestType, data, config) {
    return request('get', urlConstants.get[requestType], data, config)
  },
  patch(requestType, data, config) {
    return request('patch', urlConstants.patch[requestType], data, config)
  },
  delete(requestType, data, config) {
    return request('delete', requestType, {}, config)
  },
  post(requestType, data, config) {
    return request('post', urlConstants.post[requestType], data, config)
  },
  upload(files, config = {}) {
    if (files.length === 0 || files.length > 1) {
      return Message.error('暂时只支持上传一个文件')
    }

    const file = files[0]

    const defaultUrl = 'http://172.16.80.91:3000/api/upload4Sword'
    return request('post', config.url || defaultUrl, changeToFormData({ file }), config)
  },
  ajax(requestType, data, config = {}) {
    const isGetRequest = urlConstants.get[requestType]
    return request(config.method || (isGetRequest ? 'get' : 'post'), isGetRequest || urlConstants.post[requestType], data, config)
  }
}

export default {
  ...requests,
  install(Vue) {
    Vue.prototype.$axios = axios
    Vue.prototype.$ajax = requests.ajax
    Vue.prototype.$ajax.get = requests.get
    Vue.prototype.$ajax.post = requests.post
    Vue.prototype.$ajax.patch = requests.patch
    Vue.prototype.$ajax.delete = requests.delete
    Vue.prototype.$ajax.upload = requests.upload
  }
}
