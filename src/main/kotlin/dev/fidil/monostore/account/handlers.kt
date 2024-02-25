package dev.fidil.monostore.account

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("accounts")
class AccountEventHandler(private val repository: AccountRepository) {

    @EventHandler
    fun on(event: AccountCreated) {
        repository.save(AccountDocument(event.accountId, event.name, event.email))
    }

}

@Component
@ProcessingGroup("accounts")
class AccountQueryHandler(private val repository: AccountRepository) {

    @QueryHandler
    fun handle(query: GetAccount): AccountView {
        return repository.findById(query.id).map {
            AccountView(it.id, it.name, it.email)
        }.orElseThrow()
    }
}