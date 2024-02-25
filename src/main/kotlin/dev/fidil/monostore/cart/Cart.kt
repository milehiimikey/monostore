package dev.fidil.monostore.cart

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import java.util.UUID

@Aggregate
class Cart() {
    @AggregateIdentifier
    private lateinit var cartId: UUID
}