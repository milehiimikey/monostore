package dev.fidil.monostore.product

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.javamoney.moneta.CurrencyUnitBuilder
import org.javamoney.moneta.Money
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class Product() {
    @AggregateIdentifier
    private lateinit var id: UUID
    private lateinit var name: String
    private lateinit var description: String
    private var price: Money = Money.zero(CurrencyUnitBuilder.of("USD", "default").build())
    private lateinit var state: ProductState
    private lateinit var imageUrl: String
    private var inStock by Delegates.notNull<Int>()

    @CommandHandler
    constructor(command: AddProductToStock) : this() {
        AggregateLifecycle.apply(
            ProductAddedToStock(
                UUID.randomUUID(),
                command.name,
                command.description,
                command.price,
                command.inStock,
                command.imageUrl,
                command.state!!
            )
        )
    }

    @EventSourcingHandler
    fun on(event: ProductAddedToStock) {
        id = event.accountId
        name = event.name
        description = event.description
        price = event.price
        inStock = event.inStock
        imageUrl = event.imageUrl
        state = event.state
    }

}