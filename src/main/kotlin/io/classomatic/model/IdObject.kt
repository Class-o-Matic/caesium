package io.classomatic.model

import org.bson.types.ObjectId

interface IdObject {
    val _id: ObjectId
}