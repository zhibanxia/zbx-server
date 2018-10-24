import qs from 'qs';

export const isEqual = (x, y) => {
  if (x === y) {
    return true;
  } else if (typeof x === 'object' && typeof y === 'object') {
    return Object.keys(x).every(key => x[key] === y[key]);
  }
  return false;
};

/**
 * 判断缓存是否命中
 * @param  {[Number|Boolean]} ts  [为 true 则默认获取缓存数据，无视缓存时间，为 number 则代表缓存时间间隔，0 等同于 true]
 * @param  {[Object]} cacheData   [对应的请求已缓存的数据]
 * @param  {[Object]} requestData [请求的参数]
 * @return {[Boolean]}            [true 代表缓存命中，false 未命中]
 */
export const hitCache = ({ts, dataInCache, params}) => {
  if (!dataInCache || !(ts === true || ts === 0 || (Date.now() - dataInCache.ts < ts))) return false;

  return isEqual(dataInCache.params, params);
};

export const changeToFormData = (data = {}) => {
  const formData = new FormData();
  Object.entries(data).forEach(([key, value]) => formData.append(key, value));
  return formData;
};

export const formatRequestData = (data = {}, method, config) => {
  if (config.dataType === 'params') return {};
  
  if (method === 'get' || config.dataType === 'query') {
    const params = {};
    Object.entries(data).forEach(([key, val]) => {
      if (typeof val !== 'object') {
        params[key] = val;
      }
    });
    return { params };
  }

  if (config.dataType === 'form') {
    config.axiosConfig = {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    };
    return qs.stringify(data);
  }

  if (config.dataType === 'json' && method === 'delete') {
    return { data };
  }

  return data;
};
