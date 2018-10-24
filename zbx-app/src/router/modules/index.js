const defaultOrder = [
  'admin',
  'owner',
  'recyler'
]

const defaultOrderFiles = defaultOrder.map(v => `./${v}.js`)

const requireAll = requireContext =>
  [...new Set([...defaultOrderFiles, ...requireContext.keys()])].map(
    requireContext
  )
const req = require.context('./', false, /^\.\/(?!index).+\.js$/)
const modules = requireAll(req)

export default modules.map(v => v.default)
