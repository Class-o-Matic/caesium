package io.classomatic

import io.classomatic.modules.classroomModule
import io.classomatic.modules.reservationModule
import io.classomatic.modules.sensorModule
import io.classomatic.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.yaml references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureServices()

    configureAuth()
    configureDb()

    classroomModule()
    sensorModule()
    reservationModule()
}
