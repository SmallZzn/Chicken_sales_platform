<template>
  <div>
    <div
      class="radio address-radio"
      v-for="address in addressList"
      :key="address.addressId"
    >
      <label>
        <input
          type="radio"
          name="optionsRadios"
          :id="address.addressId"
          :value="address"
          v-model="pickedAddress"
        />
        {{ getAddressStr(address) }}
        {{ address.receiver }} {{ address.phone }}
      </label>
      <span
        v-show="address.isdefault === 1"
        class="label label-success"
        style="margin-left: 10px"
        >默认</span
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pickedAddress: "",
      addressList: [
        {
          addressId: 3,
          userId: 2,
          receiver: "收件人",
          sex: "男",
          phone: "12363256213",
          province: "广东省",
          city: "汕尾市",
          region: "陆丰村",
          detail: "仲恺1",
          isdefault: 0,
        },
      ],
    };
  },
  computed: {},
  watch: {
    pickedAddress: function (newVal) {
      console.log(newVal);
      this.$emit("pickAddress", newVal.addressId);
    },
  },
  created: function () {
    this.requestAddress();
    this.selectDefaultAddress();
  },
  methods: {
    // 请求数据
    requestAddress() {
      this.$api.address.selectAllAddress().then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          this.addressList = res.data;
          // 选择默认地址
          this.selectDefaultAddress();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    getAddressStr(item) {
      return `${item.province}${item.city} ${item.region}${item.detail}`;
    },
    // 选择默认地址
    selectDefaultAddress() {
      let defaultAddress = this.addressList.filter(
        (address) => address.isdefault === 1
      );
      this.pickedAddress = defaultAddress[0];
    },
  },
};
</script>

<style scoped></style>
