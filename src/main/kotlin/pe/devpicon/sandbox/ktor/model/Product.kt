package pe.devpicon.sandbox.ktor.model

import kotlinx.serialization.Serializable

val productStorage = mutableListOf<Product>()

@Serializable
data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val model: String
)
