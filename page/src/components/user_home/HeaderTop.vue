<template>
  <!-- 头部一 -->
  <div class="header-top">
    <div class="container">
      <div class="row">
        <div class="col-md-4">
          <div class="header-top-welcome">
            <p>欢迎来到老乡鸡销售平台!</p>
          </div>
        </div>

        <div class="col-md-5">
          <ul class="nav nav-pills header-top-nav">
            <li><a href="#/user/home">首页</a></li>
            <li><a href="#">会员</a></li>
            <li><a href="#">iOS</a></li>
            <li><a href="#">Android</a></li>
            <li><a href="#">联系我们</a></li>
            <li><a href="#">帮助</a></li>
          </ul>
        </div>

        <div class="col-md-2">
          <button type="button" class="btn btn-default btn-sm language">
            <span class="glyphicon glyphicon-globe"></span>
            <select>
              <option value="简体中文">
                <a href="#index.html">简体中文</a>
              </option>
              <option value="English"><a href="index2.html">English</a></option>
            </select>
          </button>
        </div>

        <div class="col-md-1">
          <button type="button" class="btn btn-default btn-sm language">
            <span class="glyphicon glyphicon-usd"></span>
            <select>
              <option value="人民币">人民币</option>
              <option value="dollor">Doller</option>
            </select>
          </button>
        </div>
      </div>
    </div>
    <!-- 头部一结束 -->
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
/* 顶部  头部一*/
.header-top {
  background-color: #119744;
}

.header-top-welcome p {
  color: #fff;
  font-size: 14px;
  letter-spacing: 0.3px;
  padding-left: 20px;
}

.header-top-nav {
  margin-left: -20px;
  padding: 5px 0;
}

.header-top-nav a:hover {
  color: #119744;
}

.header-top-nav a {
  color: white;
}

.language {
  margin-top: 8px;
  background-color: #119744;
  border: #119744;
}

.language:hover {
    background-color: #e6e6e6;
}

.language span {
  margin-top: 3px;
  font-size: 16px;
  color: #fff;
  vertical-align: auto;
}

.language:hover span {
  color: #119744;
}

.nav > li > a:focus {
    background-color: #119744;
    color: #e6e6e6;
}

/* 头部一结束 */
</style>
