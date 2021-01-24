module.exports = {
    devServer: {
      proxy: {
        '^/rest': {
          target: 'http://localhost:5001/',
          changeOrigin: true,
        },
        '^/login': {
          target: 'http://localhost:5001/',
          changeOrigin: true,
          // pathRewrite: { "^/login/": "/login/" }, 
          logLevel: "debug",
        },
        '^/logout': {
          target: 'http://localhost:5001/',
          changeOrigin: true,
          logLevel: "debug",
        },
        '^/api': {
          target: 'http://localhost:5001/',
          changeOrigin: true,
          // pathRewrite: { "^/api/": "/api/" },
          logLevel: "debug",
        },
      }
    }
  }