package dev.fidil.monostore.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.zalando.jackson.datatype.money.MoneyModule


@Configuration
class JacksonConfiguration {

    @Bean
    fun moneyModule(): Module {
        return MoneyModule().withMoney()
    }

//    @Bean
//    @Primary
//    fun axonJacksonSerializer(objectMapper: ObjectMapper): Serializer =
//        JacksonSerializer.builder()
//            .objectMapper(objectMapper)
//            .defaultTyping()
//            .build()

}