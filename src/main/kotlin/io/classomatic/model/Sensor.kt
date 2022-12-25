package io.classomatic.model

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Name
import io.classomatic.serialization.BasicObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
@Entity("Sensors")
data class Sensor(
    @Id
    @Name("_id")
    @Serializable(with = BasicObjectIdSerializer::class)
    override val _id: ObjectId = ObjectId.get(),

    @Name("name")
    val name: String? = null,

    @Name("threshold")
    val threshold: Float? = null
): IdObject
