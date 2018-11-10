export const RECYLE_TYPE = [
  {label: '纸板', id: 1},
  {label: '塑料瓶', id: 2},
  {label: '纸板、塑料瓶', id: 3}
]
export const RECYLE_STATUS = [
  {label: '已发布', id: 1},
  {label: '待回收', id: 2},
  {label: '已回收', id: 3},
  {label: '已取消', id: 4}
]
export const RECYLE_AMOUNT = [
  {label: '< 0.5kg 免费回收', id: 1},
  {label: '< 1kg', id: 2},
  {label: '< 3kg', id: 3},
  {label: '< 5kg', id: 4},
  {label: '> 5kg', id: 5}
]
export const USER_STATUS = [
  {label: '正常', id: 1},
  {label: '待提交审核', id: 2},
  {label: '审核不通过，请重新提交审核', id: 3},
  {label: '审核中', id: 4},
  {label: '被禁用', id: 5},
  {label: '已注销', id: 6}
]
export const USER_STATUS4ADMIN = [
  {label: '正常', id: 1},
  {label: '待完善资料', id: 2},
  {label: '审核不通过', id: 3},
  {label: '审核中', id: 4},
  {label: '被禁用', id: 5},
  {label: '已注销', id: 6}
]
export const TAKE_GARBAGE = [
  {label: '是', id: true},
  {label: '否', id: false}
]

export const DEFAULT_ADDR = {
  areaId: '330102',
  cityId: '330100',
  provinceId: '330000'
}
