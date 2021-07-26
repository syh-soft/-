package com.example.day12.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration implements WebMvcConfigurer {
    private static final List<ResponseMessage> RESPONSE_MESSAGES = Lists.newArrayList((new ResponseMessageBuilder()).code(200).message("成功").build(), (new ResponseMessageBuilder()).code(404).message("您访问的资源不存在").build(), (new ResponseMessageBuilder()).code(401).message("您没有登录或token已过期").build(), (new ResponseMessageBuilder()).code(403).message("您无权访问该资源").build(), (new ResponseMessageBuilder()).code(500).message("系统内部异常").build());

    public SwaggerConfiguration() {
    }

    @Bean
    public Docket airBottleDocket() {
        return (new Docket(DocumentationType.SWAGGER_2)).groupName("day12").apiInfo(this.getApiInfo()).useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, RESPONSE_MESSAGES).globalResponseMessage(RequestMethod.POST, RESPONSE_MESSAGES).globalResponseMessage(RequestMethod.PUT, RESPONSE_MESSAGES).globalResponseMessage(RequestMethod.DELETE, RESPONSE_MESSAGES).select().apis(RequestHandlerSelectors.basePackage("com.example.day12")).paths(PathSelectors.any()).build();
    }


    private ApiInfo getApiInfo() {
        return (new ApiInfoBuilder()).title("演示工程V1.0.0").version("1.0.0").build();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }
}
