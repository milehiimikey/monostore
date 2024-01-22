package dev.fidil.monostore.account

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

@Document
data class AccountDocument(
    val id: UUID,
    val name: String,
    var email: String,
)

interface AccountRepository : MongoRepository<AccountDocument, UUID> {
}