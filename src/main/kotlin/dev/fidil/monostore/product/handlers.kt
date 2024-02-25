package dev.fidil.monostore.product

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("products")
class ProductEventHandler(private val productRepository: ProductRepository, private val queryUpdateEmitter: QueryUpdateEmitter) {

    @EventHandler
    fun handle(event: ProductAddedToStock) {
        productRepository.save(
            ProductDocument(
                id = event.accountId,
                name = event.name,
                description = event.description,
                price = event.price,
                inStock = event.inStock,
                imageUrl = event.imageUrl,
                state = ProductState.PENDING
            )
        )
    }
}

@Component
@ProcessingGroup("products")
class ProductQueryHandler(private val productRepository: ProductRepository) {

    @QueryHandler
    fun handle(query: GetProducts): List<ProductAdminView> {
        return productRepository.findByState(query.state)
            .map { productDocument ->
                ProductAdminView(
                    id = productDocument.id,
                    name = productDocument.name,
                    description = productDocument.description,
                    price = productDocument.price,
                    inStock = productDocument.inStock,
                    imageUrl = productDocument.imageUrl,
                    state = productDocument.state
                )
            }
    }
}