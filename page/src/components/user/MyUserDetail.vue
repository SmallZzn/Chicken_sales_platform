<template>
  <div>
    <button type="button" class="btn btn-light btn-sm" @click="goBack">
      后退
    </button>
    <h4 class="text-center">用户详情--{{ id }}</h4>
  </div>
</template>

<script>
export default {
  name: "MyUserDetail",
  props: ["id"],
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style lang="less" scoped></style>
