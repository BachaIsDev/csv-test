package com.rnt.test_passing.config;

import java.util.Locale;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class AppConfig {
  @Bean
  public ConversionServiceFactoryBean conversionService(Set<Converter<?, ?>> converters) {
    var conversionServiceFactoryBean = new ConversionServiceFactoryBean();
    conversionServiceFactoryBean.setConverters(converters);
    return conversionServiceFactoryBean;
  }
  @Bean(name="messageSource")
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultLocale(new Locale("ru", "Russia"));
    messageSource.setBasename("classpath:messages/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

}
