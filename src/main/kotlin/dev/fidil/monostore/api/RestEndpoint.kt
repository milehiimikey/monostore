package dev.fidil.monostore.api

import dev.fidil.monostore.account.AccountView
import dev.fidil.monostore.account.CreateAccount
import dev.fidil.monostore.account.GetAccount
import dev.fidil.monostore.account.GetAccounts
import dev.fidil.monostore.product.CreateProduct
import dev.fidil.monostore.product.GetProducts
import dev.fidil.monostore.product.ProductState
import dev.fidil.monostore.product.ProductView
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.*
import java.util.concurrent.CompletableFuture

@RestController
class RestEndpoint(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {

    @PostMapping("/accounts")
    fun createAccount(@RequestBody account: CreateAccount) {
        return commandGateway.sendAndWait(account)
    }

    @PostMapping("/products")
    fun createProduct(@RequestBody product: CreateProduct) {
        return commandGateway.sendAndWait(product)
    }

    @GetMapping("/accounts")
    fun getAccounts(@RequestParam(required = false) filter: String? = null): List<AccountView> {
        return queryGateway.query(GetAccounts(filter), ResponseTypes.multipleInstancesOf(AccountView::class.java)).get()
    }

    @GetMapping("/accounts/{accountId}")
    fun getAccountById(@PathVariable accountId: UUID): AccountView {
        return queryGateway.query(GetAccount(accountId), AccountView::class.java).get()
    }

    @GetMapping("/products")
    fun getProducts(@RequestParam(required = false) state: ProductState = ProductState.PUBLISHED): Flux<ProductView> {
        return Flux.fromIterable(queryGateway.query(GetProducts(state), ResponseTypes.multipleInstancesOf(ProductView::class.java)).get())
    }

    @GetMapping("/products/future")
    fun getProductsFuture(@RequestParam(required = false) state: ProductState = ProductState.PUBLISHED): CompletableFuture<List<ProductView>> {
        return queryGateway.query(GetProducts(state), ResponseTypes.multipleInstancesOf(ProductView::class.java))
    }
}