package dev.fidil.monostore.product

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("products")
class ProductEventHandler(private val productRepository: ProductRepository) {

    @EventHandler
    fun handle(event: ProductCreated) {
        productRepository.save(
            ProductDocument(
                id = event.accountId,
                name = event.name,
                description = event.description,
                price = event.price,
                inStock = event.inStock,
                imageUrl = event.imageUrl,
                state = ProductState.DRAFT
            )
        )
    }

    @EventHandler
    fun handle(event: ProductPublished) {
        productRepository.findById(event.productId).ifPresent {
            it.state = ProductState.PUBLISHED
            productRepository.save(it)
        }
    }

    @EventHandler
    fun handle(event: ProductSoldOut) {
        productRepository.findById(event.productId).ifPresent {
            it.state = ProductState.SOLD_OUT
            productRepository.save(it)
        }
    }
}

@Component
@ProcessingGroup("products")
class ProductQueryHandler(private val productRepository: ProductRepository) {

    @QueryHandler
    fun handle(query: GetProducts): List<ProductView> {
        return productRepository.findByState(query.state)
            .map { productDocument ->
                ProductView(
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