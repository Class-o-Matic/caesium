package io.classomatic.modules

import io.classomatic.model.Classroom
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Application.classroomModule() {
    return basicModelModule<Classroom>()
}
