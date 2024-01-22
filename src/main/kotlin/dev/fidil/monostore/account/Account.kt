package dev.fidil.monostore.account

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import java.util.*
import org.axonframework.modelling.command.AggregateLifecycle.apply

@Aggregate
class Account() {
    @AggregateIdentifier
    private lateinit var id: UUID
    private lateinit var name: String
    private lateinit var email: String

    @CommandHandler
    constructor(command: CreateAccount) : this() {
        apply(
            AccountCreated(
                UUID.randomUUID(),
                command.name,
                command.email
            )
        )
    }

    @CommandHandler
    fun handle(command: UpdateEmailAddress) {
        apply(
            EmailAddressUpdated(
                command.accountId,
                command.email
            )
        )
    }

    @EventSourcingHandler
    fun on(event: AccountCreated) {
        id = event.accountId
        name = event.name
        email = event.email
    }

    @EventSourcingHandler
    fun on(event: EmailAddressUpdated) {
        email = event.email
    }
}