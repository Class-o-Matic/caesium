package io.classomatic.model

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Name
import dev.morphia.annotations.Reference
import io.classomatic.serialization.BasicObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Entity("Classrooms")
@Serializable
data class Classroom(
    @Id
    @Serializable(with = BasicObjectIdSerializer::class)
    @Name("_id")
    override val _id: ObjectId = ObjectId.get(),

    @Name("name")
    val name: String? = null,

    @Name("raspberry")
    @Reference(idOnly = true)
    val sensors: List<Sensor> = emptyList()
): IdObject
