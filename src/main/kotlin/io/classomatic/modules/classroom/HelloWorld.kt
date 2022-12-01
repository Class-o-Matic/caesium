package io.classomatic.modules.classroom

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Application.classroomModule() {
    routing {
        get("/") {
            call.respondText("Hello world !")
        }
    }
}