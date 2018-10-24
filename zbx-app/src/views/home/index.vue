<template>
  <div></div>
</template>
<script>
export default {
  data () {
    return {}
  },
  created () {
    // this.getType()
    window.location.href = '/'
  },
  methods: {
    getType () {
      this.$ajax('getUserType').then(res => {
        let userInfo = res.data
        let type = userInfo.userType
        let status = userInfo.userStatus
        debugger
        if (status !== 1) {
          this.$router.push(`/error?status=${status}&type=${type}`)
        }
        if (type === 1) {
          this.$router.push('/owner')
        }
        if (type === 2) {
          this.$router.push('/recyler')
        }
        if (type === 3) {
          this.$router.push('/admin')
        }
      })
    }
  }
}
</script>
