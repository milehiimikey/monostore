package dev.fidil.monostore.account

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateAccount(
    val name: String,
    val email: String
)

data class UpdateEmailAddress(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val email: String,
)