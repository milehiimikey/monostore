package dev.fidil.monostore.account

import java.util.*

data class AccountCreated(
    val accountId: UUID,
    val name: String,
    val email: String,
)
