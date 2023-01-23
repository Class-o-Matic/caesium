package io.classomatic.model

import kotlinx.serialization.Serializable
import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Name
import dev.morphia.annotations.Reference
import io.classomatic.model.serializers.DateSerializer
import io.classomatic.serialization.BasicObjectIdSerializer
import org.bson.types.ObjectId
import java.util.*

@Entity("Reservations")
@Serializable
data class Reservation(
    @Id
    @Serializable(with = BasicObjectIdSerializer::class)
    @Name("_id")
    override val _id: ObjectId = ObjectId.get(),

    @Name("cours")
    val cours: String,

    @Name("debut")
    @Serializable(with = DateSerializer::class)
    val debut: Date,

    @Name("fin")
    @Serializable(with = DateSerializer::class)
    val fin: Date,

    @Name("prof")
    val prof: String,

    @Name("classroom")
    @Reference(idOnly = true)
    val classroom: Classroom
): IdObject
