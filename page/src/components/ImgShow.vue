<template>
  <el-image
    :src="getImgUrl(imgName)"
    alt="Product Image"
    style="width: 100%; height: 100%; object-fit: cover"
  >
  </el-image>
</template>

<script>


export default {
  props: {
    imgName: {
        type: String,
    }
  },
  methods: {
    getImgUrl(img) {
      return `http://localhost:9999/file/download?fileName=${img}`;
    },
  },
};
</script>

<style></style>
