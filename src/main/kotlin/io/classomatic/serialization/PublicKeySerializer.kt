package io.classomatic.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

object PublicKeySerializer: KSerializer<PublicKey> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PublicKey", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): PublicKey {
        val stringKey = decoder.decodeString()
        val byteKey: ByteArray = Base64.getDecoder()
            .decode(stringKey)
        val keySpec = X509EncodedKeySpec(byteKey)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    override fun serialize(encoder: Encoder, value: PublicKey) {
        val byteKey = value.encoded
        val stringKey = Base64.getEncoder().encodeToString(byteKey)
        return encoder.encodeString(stringKey)
    }
}