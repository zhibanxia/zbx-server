<template>
  <!-- <router-view></router-view> -->
  <transition name="fade" mode="out-in">
    <!-- :exclude="/essential-container|nocache/" -->
    <!-- <keep-alive> -->
      <router-view></router-view>
    <!-- </keep-alive> -->
  </transition>
  <!-- <transition name="fade" mode="out-in">
    <router-view></router-view>
  </transition> -->
</template>

<script>
export default {
  name: 'essential-container'
};
</script>
