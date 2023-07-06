<template>
  <div>
    <el-select
      ref="selectLabel"
      :value="selectValue"
      filterable
      :placeholder="placeholder"
      @visible-change="onVisible"
      @change="onChange"
    >
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
        @click.native="selectLabel = item.label"
      >
      </el-option>
    </el-select>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  // v-model绑定value值
  model: {
    prop: "modelValue",
    event: "valueChange",
  },
  props: {
    modelValue: Number, //绑定分类的ID值
    placeholder: String,
    initSelectLabel: String,
  },
  //监视modelValue的变化
  watch: {
    modelValue: function (val) {
      this.selectValue = val;
    },
  },
  data() {
    return {
      selectLabel: this.initSelectLabel,
      options: [],
      selectValue: "",
    };
  },
  methods: {
    handleData(target = []) {
      let data = [];
      target.forEach((val, index) => {
        let { categoryId: value, name: label } = val;
        data[index] = { value, label };
      });
      // console.log(data)
      return data;
    },
    //选择改变时，传出val
    onChange(val) {
      this.$emit("valueChange", val);
    },
    onVisible(visible) {
      //保存当前选择的Label
      this.selectLabel = this.$refs.selectLabel.selected.label;
      // console.log(this.selectLabel)
      //当显示下拉菜单时，发送请求
      if (visible) {
        request({
          method: "get",
          url: "/category/selectAllCategory",
        }).then((res) => {
          console.log(res);
          const response = res.data;
          if (response.code === 200) {
            //将返回的数据挂到选项上
            this.options = this.handleData(response.data);
            console.log(response.data)
          } else {
            this.$message.error(response.msg);
          }
        });
      } else {
        //未选择时，使其等于initSelectLabel
        if (!this.selectLabel) {
          this.selectLabel = this.initSelectLabel;
          // console.log(this.selectLabel)
        }
        //向父组件传送值selectLabel
        this.$emit("onSelectLabel", this.selectLabel);
      }
    },
  },
};
</script>

<style></style>
