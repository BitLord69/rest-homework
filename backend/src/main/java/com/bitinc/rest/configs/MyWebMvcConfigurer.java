package com.bitinc.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class MyWebMvcConfigurer implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    System.out.println("Adding cors in MyWebMvcConfigurer.....");
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:8080")
        .allowedMethods("GET", "POST", "PUT", "DELETE");
  }

//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/**")
//        .addResourceLocations("file:src/main/resources/static/")
//        .setCacheControl(CacheControl.noCache())
//        .resourceChain(false)
//        .addResolver(new PathResourceResolver() {
//          @Override
//          protected Resource getResource(String resourcePath,
//                                         Resource location) throws IOException {
//            Resource requestedResource = location.createRelative(resourcePath);
//            return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//                : new ClassPathResource("/static/index.html");
//          }
//        });
//  }
//
//  @Override
//  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
//  }
//
//  @Bean
//  public DataSource dataSource() {
//    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//    dataSourceBuilder.driverClassName("org.sqlite.JDBC");
//    dataSourceBuilder.url("jdbc:sqlite:MY_AWESOME_DATABASE.db");
//    return dataSourceBuilder.build();
//  }
}
