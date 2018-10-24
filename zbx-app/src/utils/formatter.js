
export const statusFormatter = (statusMapper, statusKey = 'status') => status => {
  const result = statusMapper.find(v => v[statusKey].toString() === status.toString());
  return result ? (result.text || result.label) : status;
};

export const moneyTableFomatter = (row, col) => row[col.property] / 100;

export const dateTableFomatter = (row, col) => row[col.property] || '-';

export const tableFomatter = (row, col) => row[col.property] || '-';

export const unitFormatter = (unit = '', deStr = '-') => (row, col) => {
  const val = row[col.property];
  return val ? val + unit : deStr;
};

export const moneyFormatter = (money, fixed = 2) => money ? (money / 100).toFixed(2) : '-';
/**
 * 时间格式化
 * @param date    接收可以被new Date()方法转换的内容
 * @param format  字符串，需要的格式例如：'yyyy-MM-dd hh:mm:ss'
 * @returns {String}
 */
export const dateFomatter = (date, format = 'yyyy-MM-dd') => {
  if (!date) return '-';
  date = new Date(date);
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
    'q+': Math.floor((date.getMonth() + 3) / 3),
    'S': date.getMilliseconds()
  };
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  for (let k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
    }
  }
  return format;
};

export const decimalsToFractional = (decimals, split = '/') => {
  const formatDecimals = decimals.toFixed(2);
  let denominator = 100; // 初始化分母
  let numerator = formatDecimals * 100; // 初始化分子
  let bigger = 0;
  function recursion() {
    bigger = denominator > numerator ? denominator : numerator;
    for (let i = bigger; i > 1; i--) {
      if (Number.isInteger(numerator / i) && Number.isInteger(denominator / i)) {
        numerator = numerator / i;
        denominator = denominator / i;
        recursion();
      }
    }
  }
  recursion();
  return `${numerator}${split}${denominator}`;
};
