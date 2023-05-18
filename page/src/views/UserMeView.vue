<template>
  <div>
    <header-top></header-top>
    <user-me></user-me>
    <visual></visual>
    <home-footer></home-footer>
  </div>
</template>

<script>
import UserMe from "@/components/UserMe.vue";
import Visual from "@/components/user_home/Visual.vue";
import HomeFooter from "@/components/user_home/HomeFooter.vue";
import HeaderTop from "@/components/user_home/HeaderTop.vue";

export default {
  components: { HeaderTop, UserMe, Visual, HomeFooter },
};
</script>

<style></style>
