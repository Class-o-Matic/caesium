package io.classomatic.plugins

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.koin
import org.litote.kmongo.reactivestreams.KMongo

fun Application.configureDb() {
    val dbUsername = environment.config.property("mongodb.username").getString()
    val dbPassword = environment.config.property("mongodb.password").getString()
    val dbHost = environment.config.property("mongodb.host").getString()
    val dbPort = environment.config.property("mongodb.port").getString()

    val conString = "mongodb://$dbUsername:$dbPassword@$dbHost:$dbPort"

    koin {
        var dbModule = module {
            single { KMongo.createClient(conString) }
        }

        modules(dbModule)
    }
}