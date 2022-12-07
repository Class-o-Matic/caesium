package io.classomatic.model

import io.classomatic.serialization.BasicObjectIdSerializer
import io.classomatic.serialization.PublicKeySerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.security.PublicKey

@Serializable
data class Raspberry(
    @Serializable(with = BasicObjectIdSerializer::class)
    override val _id: ObjectId?,
    val hostname: String,
    @Serializable(with = PublicKeySerializer::class)
    val publicKey: PublicKey,

    val classroom: Classroom,
    val sensors: List<Sensor>
): IdObject