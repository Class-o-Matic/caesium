package io.classomatic.modules

import com.mongodb.reactivestreams.client.MongoClient
import io.classomatic.extensions.PaginatedResult
import io.classomatic.extensions.getPaginated
import io.classomatic.model.Classroom
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.getCollection


@Suppress("unused")
fun Application.classroomModule() {
    val mongoClient by inject<MongoClient>()

    routing {
        authenticate("rfid-auth") {
            get("/api/classroom/{id}") {
                val id = call.parameters["id"]
                val jwtPrincipal = call.principal<JWTPrincipal>()
                val realm = jwtPrincipal?.get("azp")

                if (realm == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val database = mongoClient.getDatabase(realm)
                    val collection = database.getCollection<Classroom>()
                    val classroom = collection.find(Classroom::_id eq ObjectId(id))

                    call.respond<Classroom>(classroom.awaitFirst())
                }
            }

            get("/api/classroom") {
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 25
                val jwtPrincipal = call.principal<JWTPrincipal>()
                val realm = jwtPrincipal?.get("azp")

                if (realm == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val database = mongoClient.getDatabase(realm)
                    val collection = database.getCollection<Classroom>()

                    val paginatedSerializer = PaginatedResult.serializer(Classroom.serializer())
                    val serializedValue = Json.encodeToString(paginatedSerializer, collection.getPaginated(page, limit))

                    call.respond(serializedValue)
                }
            }

            post("/api/classroom") {
                val newClassroom = call.receive<Classroom>()
                val jwtPrincipal = call.principal<JWTPrincipal>()
                val realm = jwtPrincipal?.get("azp")

                if (realm == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val database = mongoClient.getDatabase(realm)
                    val collection = database.getCollection<Classroom>()

                    collection.insertOne(newClassroom)
                        .awaitFirst()

                    call.respond(HttpStatusCode.OK)
                }
            }
//            TODO("Complete the update part of the CRUD operation, especially, find a way to update with KMongo")
//            patch("/api/classroom/{id}") {
//                val updateClassroom = call.receive<Classroom>()
//                val id = call.parameters["id"]
//                val jwtPrincipal = call.principal<JWTPrincipal>()
//                val realm = jwtPrincipal?.get("azp")
//
//                if (realm == null) {
//                    call.respond(HttpStatusCode.BadRequest)
//                } else {
//                    val database = mongoClient.getDatabase(realm)
//                    val collection = database.getCollection<Classroom>()
//
//                    collection.findOneAndUpdate(Classroom::_id eq ObjectId(id), updateClassroom)
//
//                    call.respond(HttpStatusCode.OK)
//                }
//            }

            delete("/api/classroom/{id}") {
                val id = call.parameters["id"]
                val jwtPrincipal = call.principal<JWTPrincipal>()
                val realm = jwtPrincipal?.get("azp")

                if (realm == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val database = mongoClient.getDatabase(realm)
                    val collection = database.getCollection<Classroom>()

                    collection.deleteOne(Classroom::_id eq ObjectId(id))
                        .awaitFirst()

                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
