package dev.fidil.monostore.account

import java.util.*

data class AccountView(
    val id: UUID,
    val name: String,
    val email: String,
)