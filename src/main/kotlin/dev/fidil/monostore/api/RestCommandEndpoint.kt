package dev.fidil.monostore.api

import dev.fidil.monostore.account.CreateAccount
import dev.fidil.monostore.product.AddProductToStock
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
class RestCommandEndpoint(private val commandGateway: CommandGateway) {

    @PostMapping("/accounts/create")
    fun createAccount(@RequestBody account: CreateAccount) {
        return commandGateway.sendAndWait(account)
    }

    @PostMapping("/products/create")
    fun createProduct(@RequestBody product: AddProductToStock) {
        return commandGateway.sendAndWait(product)
    }
}