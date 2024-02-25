package dev.fidil.monostore.api

import dev.fidil.monostore.account.AccountView
import dev.fidil.monostore.account.GetAccount
import dev.fidil.monostore.product.*
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.CompletableFuture

@RestController
class RestViewEndpoint(private val queryGateway: QueryGateway) {

    @GetMapping("/accounts/{accountId}")
    fun getAccountById(@PathVariable accountId: UUID): AccountView {
        return queryGateway.query(GetAccount(accountId), AccountView::class.java).get()
    }

    @GetMapping("/products/admin")
    fun getProductsAdmin(@RequestParam(required = false) state: ProductState = ProductState.PENDING): CompletableFuture<List<ProductAdminView>> {
        return queryGateway.query(GetProducts(state), ResponseTypes.multipleInstancesOf(ProductAdminView::class.java))
    }
}