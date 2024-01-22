package dev.fidil.monostore.config

import org.javamoney.moneta.Money
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfiguration {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(mutableListOf(MoneyReadConverter(), MoneyWriteConverter()))
    }

}

@ReadingConverter
class MoneyReadConverter : Converter<String, Money> {
    override fun convert(moneyString: String): Money {
        return Money.parse(moneyString)
    }
}

@WritingConverter
class MoneyWriteConverter : Converter<Money, String> {
    override fun convert(money: Money): String {
        return money.toString()
    }
}