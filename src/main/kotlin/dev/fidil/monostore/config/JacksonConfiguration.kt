package dev.fidil.monostore.config

import com.fasterxml.jackson.databind.Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.jackson.datatype.money.MoneyModule

@Configuration
class JacksonConfiguration {

    @Bean
    fun moneyModule(): Module {
        return MoneyModule().withMoney()
    }

}