package dev.fidil.monostore.product

import org.javamoney.moneta.Money
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

data class ProductAdminView(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState
)

data class StoreFrontView(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Money,
    val imageUrl: String,
    val inStock: Boolean
)

data class StoreFrontPage(val page: Page<StoreFrontView>)