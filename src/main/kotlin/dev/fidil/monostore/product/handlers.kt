package dev.fidil.monostore.product

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

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

        queryUpdateEmitter.emit(GetProducts::class.java,
            { query -> query.state == event.state },
            ProductAdminView(event.accountId, event.name, event.description, event.price, event.inStock, event.imageUrl, event.state))
    }

    @EventHandler
    fun handle(event: ProductActivated) {
        val product = productRepository.findById(event.productId)
        product.ifPresentOrElse(
            {it.state = ProductState.ACTIVE
                productRepository.save(it)
                queryUpdateEmitter.emit(GetStoreFrontProducts::class.java,
                    { _ -> it.state == ProductState.ACTIVE },
                    product)
            },
            { throw IllegalArgumentException("Product with id ${event.productId} not found") }
        )
    }
}

@Component
class ProductQueryHandler(private val productRepository: ProductRepository) {

    @QueryHandler
    fun handle(query: GetProducts): Flux<ProductAdminView> {
        val products = productRepository.findByState(query.state)
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
        return Flux.fromIterable(products)
    }

    @QueryHandler
    fun handle(query: GetProduct): StoreFrontView {
        return productRepository.findById(query.id).orElseThrow {
            throw IllegalArgumentException("Product with id ${query.id} not found")
        }.let { productDocument ->
            StoreFrontView(
                id = productDocument.id,
                name = productDocument.name,
                description = productDocument.description,
                price = productDocument.price,
                imageUrl = productDocument.imageUrl,
                inStock = productDocument.inStock > 0
            )
        }
    }

    fun getStorefrontProducts(): List<StoreFrontView> {
        return productRepository.findAll()
            .map { productDocument ->
                StoreFrontView(
                    id = productDocument.id,
                    name = productDocument.name,
                    description = productDocument.description,
                    price = productDocument.price,
                    imageUrl = productDocument.imageUrl,
                    inStock = productDocument.inStock > 0
                )
            }
    }
}