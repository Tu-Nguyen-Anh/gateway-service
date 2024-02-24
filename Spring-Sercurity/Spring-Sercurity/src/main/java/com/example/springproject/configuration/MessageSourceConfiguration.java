package com.example.springproject.configuration;

import com.example.springproject.constant.CommonConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static com.example.springproject.constant.CommonConstants.MESSAGE_SOURCE;


/**
 * This class config a MessageSource bean
 */
@Configuration
public class MessageSourceConfiguration {


  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_SOURCE);
    messageSource.setDefaultEncoding(CommonConstants.ENCODING_UTF_8);
    return messageSource;
  }

}
