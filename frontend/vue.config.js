module.exports = {
    devServer: {
      proxy: {
        '^/rest': {
          target: 'http://localhost:8070/',
          changeOrigin: true
        },
      }
    }
  }