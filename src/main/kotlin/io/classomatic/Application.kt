package io.classomatic

import io.classomatic.model.test
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
}
