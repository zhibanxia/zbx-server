import getImgInfo from './getImgInfo';
import { decimalsToFractional } from './formatter';

export const requireRule = { required: true, message: '请填写', trigger: ['blur', 'change'] };
export const arrayRequireRule = { required: true, type: 'array', min: 1, message: '请选择一项', trigger: 'change' };
export const intRule = { pattern: /^-?\d*$/, message: '请输入整数', trigger: ['blur', 'change'] };
export const positiveIntRule = { pattern: /^\d*$/, message: '请输入正整数', trigger: ['blur', 'change'] };
export const idsRule = { pattern: /^\d+(,\d+)*$/, message: 'ID需为整数，多个ID请用,隔开', trigger: ['blur', 'change'] };

export const intRuleFn = (min, max) => ({
  validator: (r, val, cb) => {
    if (val) {
      if (!intRule.pattern.test(val)) return cb(new Error('请输入整数'));
      const value = Number(val);
      if (min && value < min) return cb(new Error(`请输入不小于${min}的整数`));
      if (max && value > max) return cb(new Error(`请输入不大于${max}的整数`));
    }
    cb();
  },
  trigger: ['blur', 'change']
});
export const decimalWithRangeRuleFn = (min, max, fixed = 2) => {
  const pattern = new RegExp(`^[0-9]+(\\.[0-9]{0,${fixed}})?$`);
  return {
    pattern,
    validator: (r, val, cb) => {
      if (val) {
        if (!pattern.test(val)) return cb(new Error(`请输入${fixed}位以内小数或整数`));
        const value = Number(val);
        if (min && value < min) return cb(new Error(`输入不能小于${min}`));
        if (max && value > max) return cb(new Error(`输入不能大于${max}`));
      }
      cb();
    },
    trigger: ['blur', 'change']
  };
};

export const decimalRuleFn = (fixed = 2) => ({
  pattern: new RegExp(`^[0-9]+(\\.[0-9]{0,${fixed}})?$`),
  message: `请输入${fixed}位小数`,
  trigger: ['blur', 'change']
});

export const REG_PHONE = /^\d{0,11}$/;
export const REG_EMAIL = /^(([^<>()\]\\.,;:\s@"]+(\.[^<>()\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
export const REG_URL = /^((https?|ftp):\/\/)?([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
// export const REG_URL = /^((https?|ftp):\/\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?$/;

/* url */
export const validateURL = (textval) => REG_URL.test(textval);

/* 小写字母 */
export const validateLowerCase = (str) => /^[a-z]+$/.test(str);

/* 大写字母 */
export const validateUpperCase = (str) => /^[A-Z]+$/.test(str);

/* 大小写字母 */
export const validateAlphabets = (str) => /^[A-Za-z]+$/.test(str);

/* 邮箱 */
export const validateEmail = (email) => REG_EMAIL.test(email);

/* 手机号 */
export const validatePhone = (phone) => REG_PHONE.test(phone);

/* 校验图片尺寸 */
export const validateImgSize = (file, limit = {}) => {
  return new Promise((resolve, reject) => {
    getImgInfo(file).then(({ width, height, size, type }) => {
      if (limit.width && width !== limit.width) {
        return reject(`图片宽度不是${limit.width}px`);
      }
      if (limit.height && height !== limit.height) {
        return reject(`图片高度不是${limit.height}px`);
      }
      if (limit.maxWidth && width > limit.maxWidth) {
        return reject(`图片宽度大于${limit.maxWidth}px`);
      }
      if (limit.maxHeight && height > limit.maxHeight) {
        return reject(`图片高度大于${limit.maxHeight}px`);
      }
      if (limit.maxSize && size > limit.maxSize) {
        const { maxSize } = limit;
        return reject(`图片大小大于${maxSize >= 1024 ? parseInt(maxSize / 1024) + 'MB' : maxSize + 'KB'}`);
      }
      if (limit.type) {
        const limitType = typeof limit.type === 'string' ? [limit.type] : limit.type;
        const checkType = limitType.map(v => v.toUpperCase());

        if (!checkType.includes(type.toUpperCase())) {
          return reject(`图片只支持 ${limitType.join('/')} 格式`);
        }
      }
      const { rate = '' } = limit;
      switch (typeof rate) {
        case 'number':
          if (rate <= 1 && width / height !== rate) {
            return reject(rate === 1 ? '图片只支持正方形' : `图片只支持宽纵比${decimalsToFractional(rate, ' : ')}的图片`);
          }
          break;
        case 'object':
          if (!rate.length) break;

          const validRate = rate.filter(([w, h]) => w && h);
          let fitable = !validRate.length;
          validRate.forEach(([w, h]) => {
            if (w === width && h === height) {
              fitable = true;
            }
          });
          if (!fitable) {
            return reject(`图片只支持尺寸 ${
              validRate.map(([w, h]) => `${w} * ${h}`).join(',')
            }的图片`);
          }
          break;
        default:
      }
      return resolve();
    });
  });
};
