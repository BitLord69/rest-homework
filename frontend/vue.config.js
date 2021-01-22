module.exports = {
    devServer: {
      proxy: {
        '^/rest': {
          target: 'http://localhost:5001/',
          changeOrigin: true
        },
      }
    }
  }