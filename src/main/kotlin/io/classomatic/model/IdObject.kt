package io.classomatic.model

import com.github.jershell.kbson.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

interface IdObject {
    @Serializable(with = ObjectIdSerializer::class)
    val _id: ObjectId?
}