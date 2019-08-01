package com.advance.reptile.web.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhouh on 2019/8/1.
 */
@Configuration
public class ConverterConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new LocalDateConverter());//无效
        registry.addConverter(new DateConverter());

    }
}
