package io.classomatic.model

import io.classomatic.serialization.BasicObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Classroom(
    @Serializable(with = BasicObjectIdSerializer::class)
    override val _id: ObjectId? = null,
    val name: String
): IdObject
