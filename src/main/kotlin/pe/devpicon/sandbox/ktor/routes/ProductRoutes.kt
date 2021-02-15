package pe.devpicon.sandbox.ktor.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import pe.devpicon.sandbox.ktor.model.Product
import pe.devpicon.sandbox.ktor.model.productStorage

fun Route.productRouting() {
    route(path = "/api/product") {
        get {
            if (productStorage.isNotEmpty()) {
                call.respond(productStorage)
            } else {
                call.respondText("No products found", status = HttpStatusCode.NotFound)
            }
        }
        get(path = "{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val product = productStorage.find { it.id == id } ?: return@get call.respondText(
                "No product with id $id",
                status = HttpStatusCode.NotFound
            )

            call.respond(product)
        }
        post {
            val product = call.receive<Product>()
            productStorage.add(product)
            call.respondText("Product stored correctly", status = HttpStatusCode.Accepted)
        }
        delete(path = "{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (productStorage.removeIf { it.id == id }) {
                call.respondText("Product removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.registerProductRoutes() {
    routing {
        productRouting()
    }
}