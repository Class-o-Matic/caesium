package io.classomatic.serialization

import com.github.jershell.kbson.BsonEncoder
import com.github.jershell.kbson.FlexibleDecoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId

object BasicObjectIdSerializer: KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor
        = PrimitiveSerialDescriptor("ObjectId", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ObjectId {
        return when (decoder) {
            is FlexibleDecoder -> {
                return decoder.reader.readObjectId()
            }
            else -> {
                return ObjectId(decoder.decodeString())
            }
        }
    }

    override fun serialize(encoder: Encoder, value: ObjectId) {
        return when (encoder) {
            is BsonEncoder -> {
                return encoder.encodeObjectId(value)
            }
            else -> {
                return encoder.encodeString(value.toHexString())
            }
        }
    }
}