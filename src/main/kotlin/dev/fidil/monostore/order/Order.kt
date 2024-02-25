package dev.fidil.monostore.order

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import java.util.UUID

@Aggregate
class Order() {
    @AggregateIdentifier
    private lateinit var orderId: UUID
}