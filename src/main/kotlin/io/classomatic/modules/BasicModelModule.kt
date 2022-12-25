package io.classomatic.modules

import dev.morphia.Datastore
import io.classomatic.extensions.getCollection
import io.classomatic.extensions.getPaginated
import io.classomatic.model.IdObject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.litote.kmongo.deleteOne
import org.litote.kmongo.find
import org.litote.kmongo.findOneAndReplace

inline fun <reified T: IdObject> Application.basicModelModule(canBeListed: Boolean = true) {
    val routeName = T::class.simpleName?.lowercase()
    val realm = environment.config.property("classomatic.realm").getString()

    val getDatastore: (String) -> Datastore = { realm ->
        val datastore by inject<Datastore> { parametersOf(realm) }
        datastore
    }

    routing {
        get("/api/$routeName/{id}") {
            val id = call.parameters["id"]

            val datastore = getDatastore(realm)
            val collection = datastore.getCollection<T>()
            val value = collection.find("{\"_id\": {\"\$oid\": \"$id\"}}")
                .first()

            call.respond<T>(value)
        }

        if (canBeListed) {
            get("/api/$routeName") {
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 25

                if (realm == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val datastore = getDatastore(realm)
                    val collection = datastore.getCollection<T>()
                    val paginatedValue = collection.getPaginated(page, limit)

                    call.respond(paginatedValue)
                }
            }
        }

        authenticate("rfid-auth") {
            post("/api/$routeName") {
                val newValue = call.receive<T>()

                val datastore = getDatastore(realm)
                val collection = datastore.getCollection<T>()

                collection.insertOne(newValue)

                call.respond(HttpStatusCode.OK)
            }

            patch("/api/classroom/{id}") {
                val updateModel = call.receive<T>()
                val id = call.parameters["id"]

                val datastore = getDatastore(realm)
                val collection = datastore.getCollection<T>()

                collection.findOneAndReplace("{\"_id\": {\"\$oid\": \"$id\"}}", updateModel)

                call.respond(HttpStatusCode.OK)
            }

            delete("/api/$routeName/{id}") {
                val id = call.parameters["id"]

                val datastore = getDatastore(realm)
                val collection = datastore.getCollection<T>()

                collection.deleteOne("{\"_id\": {\"\$oid\": \"$id\"}}")

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}