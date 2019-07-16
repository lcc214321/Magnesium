module.exports = {
    //打包输出路径
    outputDir: '../../resources/static',
    devServer: {
        //前端项目启动地址
        host: '127.0.0.1',
        port: 9099,
        https: false,
        proxy: {
            '/local-api': {
                //测试只改这一个地方为API的域名地址即可
                target: 'http://localhost:8177',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/local-api': ''
                }
            }
            }
        },

}