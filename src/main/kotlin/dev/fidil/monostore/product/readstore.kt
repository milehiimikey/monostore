package dev.fidil.monostore.product

import org.javamoney.moneta.Money
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

@Document
data class ProductDocument(
    val id: UUID,
    var name: String,
    var description: String,
    var price: Money,
    var inStock: Int,
    var imageUrl: String,
    var state: ProductState
)

interface ProductRepository : MongoRepository<ProductDocument, UUID> {
    fun findByState(state: ProductState): List<ProductDocument>
}