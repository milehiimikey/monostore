package dev.fidil.monostore.product

import org.springframework.data.domain.Pageable
import java.util.UUID

data class GetProducts(val state: ProductState)
data class GetProduct(val id: UUID)

data class GetStoreFrontProducts(val pageable: Pageable)