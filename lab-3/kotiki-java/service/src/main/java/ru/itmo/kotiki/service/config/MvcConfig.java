package ru.itmo.kotiki.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home.html");
        registry.addViewController("/").setViewName("home.html");

        registry.addViewController("/cats/").setViewName("cats.html");
        registry.addViewController("/friends/").setViewName("cat-friends.html");
        registry.addViewController("/owners/").setViewName("owner.html");
        registry.addViewController("/admin/").setViewName("admin.html");

        registry.addViewController("/login").setViewName("login.html");
        registry.addViewController("/403").setViewName("403.html");
    }

}