/**
 * Copyright 2006-2015 handu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handu.open.dubbo.monitor.config;

import jetbrick.template.web.springmvc.JetTemplateViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Jinkai.Ma
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public JetTemplateViewResolver viewResolver(){
        JetTemplateViewResolver jetTemplateViewResolver = new JetTemplateViewResolver();
        jetTemplateViewResolver.setOrder(1);
        jetTemplateViewResolver.setContentType("text/html; charset=utf-8");
        jetTemplateViewResolver.setSuffix(".html");
        jetTemplateViewResolver.setConfigLocation("/WEB-INF/jetbrick-template.properties");
        return jetTemplateViewResolver;
    }

//    @Bean
//    public ServletContextTemplateResolver templateResolver(){
//        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
//        servletContextTemplateResolver.setPrefix("/WEB-INF/views/");
//        servletContextTemplateResolver.setSuffix(".html");
//        servletContextTemplateResolver.setTemplateMode("HTML5");
//        servletContextTemplateResolver.setOrder(1);
//        servletContextTemplateResolver.setCharacterEncoding("UTF-8");
//        return servletContextTemplateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine(){
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.addDialect(new LayoutDialect());
//        return templateEngine;
//    }
//
//    @Bean
//    public ThymeleafViewResolver viewResolver(){
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("UTF-8");
//        return viewResolver;
//    }

}