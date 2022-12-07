package io.classomatic.model

import io.classomatic.serialization.BasicObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Sensor(
    @Serializable(with = BasicObjectIdSerializer::class)
    override val _id: ObjectId?,
    val name: String,
    val threshold: Float
): IdObject
