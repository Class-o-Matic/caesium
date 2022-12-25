package io.classomatic.plugins

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import dev.morphia.Morphia
import io.ktor.server.application.*
import org.bson.codecs.configuration.CodecRegistries
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun Application.configureDb() {
    val dbUsername = environment.config.property("mongodb.username").getString()
    val dbPassword = environment.config.property("mongodb.password").getString()
    val dbHost = environment.config.property("mongodb.host").getString()
    val dbPort = environment.config.property("mongodb.port").getString()

    val conString = "mongodb://$dbUsername:$dbPassword@$dbHost:$dbPort"

    koin {
        var dbModule = module {
            single { createMongo(conString) }
            single { (realm: String) -> Morphia.createDatastore(get(), realm) }
        }

        modules(dbModule)
    }
}

fun createMongo(conString: String): MongoClient {
    val codecRegistry = CodecRegistries.fromRegistries(
        CodecRegistries.fromCodecs(
        ),
        MongoClientSettings.getDefaultCodecRegistry()
    )

    val settings = MongoClientSettings.builder()
        .codecRegistry(codecRegistry)
        .applyConnectionString(ConnectionString(conString))
        .build()

    return MongoClients.create(settings)
}
