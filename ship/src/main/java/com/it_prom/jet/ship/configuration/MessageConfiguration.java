package com.it_prom.jet.ship.configuration;

import com.it_prom.jet.common.procossor.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {
    @Bean
    public MessageConverter converter(){
        return new MessageConverter();
    }
}
