package io.classomatic.extensions

import com.mongodb.client.MongoCollection
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.annotations.Reference

inline fun <reified T> Datastore.getCollection(): MongoCollection<T> {
    return getCollection(T::class.java)
}
