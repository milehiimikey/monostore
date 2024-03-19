package dev.fidil.monostore.api

import dev.fidil.monostore.account.AccountView
import dev.fidil.monostore.account.GetAccount
import dev.fidil.monostore.product.*
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.util.*
import java.util.concurrent.CompletableFuture

@RestController
class RestViewEndpoint(private val queryGateway: QueryGateway,
                       private val productQueryHandler: ProductQueryHandler,
                       private val reactiveQueryGateway: ReactorQueryGateway
    ) {

    @GetMapping("/accounts/{accountId}")
    fun getAccountById(@PathVariable accountId: UUID): AccountView {
        return queryGateway.query(GetAccount(accountId), AccountView::class.java).get()
    }

    @GetMapping("/products/admin",  produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun getProductsAdmin(
        @RequestParam(required = false) state: ProductState = ProductState.PENDING)
    : Flux<ProductAdminView> {
        return reactiveQueryGateway.subscriptionQueryMany(GetProducts(state),
            ProductAdminView::class.java)
    }

    @GetMapping("/products")
    fun getStoreFrontProducts(): List<StoreFrontView> {
        return productQueryHandler.getStorefrontProducts()
    }

    @GetMapping("/products/{productId}")
    fun getProductById(@PathVariable productId: UUID): StoreFrontView {
        return queryGateway.query(GetProduct(productId), StoreFrontView::class.java).get()
    }
}