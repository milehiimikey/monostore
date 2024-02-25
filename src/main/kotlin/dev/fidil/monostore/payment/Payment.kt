package dev.fidil.monostore.payment

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import java.util.UUID

@Aggregate
class Payment() {
    @AggregateIdentifier
    private lateinit var paymentId: UUID
}