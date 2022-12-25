package io.classomatic.plugins

import com.mongodb.client.MongoCollection
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.query.DefaultQueryFactory
import dev.morphia.query.Query
import dev.morphia.query.QueryFactory
import io.classomatic.extensions.getCollection
import io.classomatic.module
import io.ktor.server.application.*
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

inline fun <reified T> createMockedCollection(): MongoCollection<T> {
    val mockedCollection = mockk<MongoCollection<T>>()
}

inline fun <reified T> Application.configureMockedDb(generationFunction: () -> T) {
    // Mock the database
    val mockedDatastore = mockk<Datastore>()

    every { mockedDatastore.getCollection<T>() } returns
    every { mockedDatastore.find(T::class.java) } returns DefaultQueryFactory().createQuery(mockedDatastore, T::class.java)

    koin {
        val mockedDbModule = module {
            single { (realm: String) -> mockedDatastore }
        }
    }
}
