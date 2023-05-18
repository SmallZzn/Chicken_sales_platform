import Vue from "vue";
import VueRouter from "vue-router";
import MyLogin from "../components/MyLogin.vue";
import Register from "../components/Register.vue";
import AdminHome from "../components/AdminHome.vue";
import MyEmployees from "../components/menus/MyEmployees.vue";
import MyUsers from "../components/menus/MyUsers.vue";
import MyGoods from "../components/menus/MyGoods.vue";
import MyRights from "../components/menus/MyRights.vue";
import MyOrders from "../components/menus/MyOrders.vue";
import MySetting from "../components/menus/MySettings.vue";
import MyUserDetail from "../components/user/MyUserDetail.vue";

import UserHomeMain from "../components/user_home/UserHomeMain.vue";
import UserHome from "../components/UserHome.vue";
import GoodsCart from "../components/cart/GoodsCart.vue";
import Payment from "../components/payment/Payment.vue";
import OrderDetail from "@/components/order/OrderDetail.vue";
import SearchGoodsShow from "../components/goods/SearchGoodsShow.vue";
import GoodsDetail from "../components/goods/GoodsDetail.vue";

import UserMeView from "../views/UserMeView.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", redirect: "/user " },
  { path: "/login", component: MyLogin },
  { path: "/register", component: Register },

  {
    path: "/admin",
    redirect: "/admin/users",
    component: AdminHome,
    children: [
      { path: "employees", component: MyEmployees },
      { path: "users", component: MyUsers },
      { path: "rights", component: MyRights },
      { path: "goods", component: MyGoods },
      { path: "orders", component: MyOrders },
      { path: "settings", component: MySetting },
      { path: "users/:id", component: MyUserDetail, props: true },
    ],
  },
  {
    path: "/user",
    redirect: "/user/home",
    component: UserHome,
    children: [
      { path: "home", component: UserHomeMain },
      { path: "cart", component: GoodsCart },
      { path: "pay", name: "Payment", component: Payment },
      { path: "order/:orderId", component: OrderDetail },
      {
        path: "search/:productName",
        name: "SearchGoods",
        component: SearchGoodsShow,
      },
      { path: "goods/:id", name: "GoodsDetail", component: GoodsDetail },
    ],
  },
  { path: "/user/me", name: "UserMe", component: UserMeView },
];

const router = new VueRouter({
  routes,
});

// router.beforeEach((to, from, next) => {
//   if (to.path === '/login') return next()
//   const token = localStorage.getItem('token1')
//   if (!token) {
//     next('/login')
//   }
//   next()
// })

export default router;
