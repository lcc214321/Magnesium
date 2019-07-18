<style scoped>
.layout {
  background: #f5f7f9;
  position: relative;
  overflow: hidden;
}
.layout-logo {
  width: 30px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
}
.layout-logo-title {
  float: left;
  margin-left: 30px;
}
.logo-title {
  font-size: 23px;
  font-weight: bold;
  color: #fff;
}
.layout-nav {
  /* background-color: brown; */
  width: 200px;
  margin: 0 auto;
  margin-right: 20px;
  position: initial;
  right: 0;
}
.nav-username {
  font-size: 15px;
  color: #fff;
  /* font-weight: bold; */
}
.nav-logout {
  margin-left: 25px;
  color: #fff;
  font-size: 13px;
  text-decoration: underline;
}
.nav-logout:hover {
  color: rgb(55, 166, 231);
}
.layout-con {
  height: 100%;
  width: 100%;
}
.menu-item span {
  display: inline-block;
  overflow: hidden;
  width: 69px;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: bottom;
  transition: width 0.2s ease 0.2s;
}
.menu-item i {
  transform: translateX(0px);
  transition: font-size 0.2s ease, transform 0.2s ease;
  vertical-align: middle;
  font-size: 16px;
}
.collapsed-menu span {
  width: 0px;
  transition: width 0.2s ease;
}
.collapsed-menu i {
  transform: translateX(5px);
  transition: font-size 0.2s ease 0.2s, transform 0.2s ease 0.2s;
  vertical-align: middle;
  font-size: 22px;
}
.layout-logo {
  width: 30px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
}
.logout-icon {
  margin-right: 1px;
}
</style>
<template>
  <div class="layout">
    <Layout :style="{minHeight: '100vh'}">
      <Header>
        <Menu mode="horizontal"
          theme="dark"
          active-name="1">
          <div class="layout-logo"
            :style="{backgroundImage: 'url(' + logoImg + ')' }"></div>
          <div class="layout-logo-title"><span class="logo-title">云管控台</span></div>
          <div class="layout-nav">
            <span class="nav-username">{{this.$store.state.uagCurrentLoginUserInfo.uagUserNickName}}</span>
            <a class="nav-logout"
              @click="doLogOut">
              <Icon type="md-exit"
                class="logout-icon"></Icon>退出登录
            </a>

          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger
          :style="{background: '#fff'}">
          <Menu :active-name="menuActiveName"
            ref="side_menu"
            theme="light"
            @on-select="routerFrame"
            width="auto">
            <MenuItem :name="item.path"
              @click="routerFrame(item.path)"
              v-for="(item,index) in menuList"
              :key="index">
            <Icon :type="item.icon"></Icon>
            {{item.name}}
            </MenuItem>
          </Menu>
        </Sider>

        <Content>
          <div style="height: 600px">
            <router-view></router-view>
          </div>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>

<script>
import logoImgResource from '@/assets/img/logo.png'

export default {
  data() {
    return {
      menuList: [],
      logoImg: logoImgResource,
    }
  },
  computed: {
    menuActiveName: function() {
      return this.$route.path
    }
  },
  created: function() {},
  mounted: function() {
    this.initMenu()
    this.utils.frameUtil.reSizeParentFrameSize(this.$store)
  },
  methods: {
    initMenu: async function() {
      this.menuList = this.utils.routerUtil.getMenuListFromLocalRouterConfig()
      this.$nextTick(() => {
        this.$refs.side_menu.updateOpened()
        this.$refs.side_menu.updateActiveName()
      })
    },
    routerFrame: function(path) {
      this.$router.push(path)
    },
    doLogOut() {
      this.utils.netUtil.post(this.API_PTAH.logout, {}, resp => {
        if (resp.data.datas) {
          location.reload()
        }
      })
    }
  }
}
</script>