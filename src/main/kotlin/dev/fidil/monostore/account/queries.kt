package dev.fidil.monostore.account

import java.util.*

data class GetAccount(val id: UUID)

data class GetAccounts(val filter: String?)