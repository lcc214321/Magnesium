module.exports = {
  //打包输出路径
  outputDir: '../../resources/static',
  devServer: {
    //前端项目启动地址
    host: '127.0.0.1',
    port: 9000,
    https: false,
    proxy: {
      '/local-api': {
        //测试只改这一个地方为API的域名地址即可
        target: 'http://localhost:8076',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/local-api': ''
        }
      }
    }
  },
  configureWebpack: {
    performance: {
      hints: false
    }
  }
}
