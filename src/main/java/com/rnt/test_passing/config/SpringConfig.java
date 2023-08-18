package com.rnt.test_passing.config;

import com.rnt.test_passing.util.DescriptorHelper;
import com.rnt.test_passing.util.impl.DescriptorHelperHandler;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SpringConfig {
  @Bean
  public ConversionServiceFactoryBean conversionService(Set<Converter<?, ?>> converters) {
    var conversionServiceFactoryBean = new ConversionServiceFactoryBean();
    conversionServiceFactoryBean.setConverters(converters);
    return conversionServiceFactoryBean;
  }

  @Bean
  public DescriptorHelperHandler descriptorHelperHandler(Set<DescriptorHelper> helpers) {
    var handler = new DescriptorHelperHandler();
    handler.setHelpers(helpers);
    return handler;
  }
}
