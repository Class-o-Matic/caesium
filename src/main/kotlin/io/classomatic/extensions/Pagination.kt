package io.classomatic.extensions

import com.mongodb.client.MongoCollection
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResult<T>(
    val page: Int,
    val limit: Int,
    val count: Int,
    val value: List<T> = emptyList()
)

inline fun <reified T> MongoCollection<T>.getPaginated(
    page: Int,
    limit: Int
): PaginatedResult<T> {
    val list = find()
        .skip((page - 1) * limit)
        .limit(limit)
        .partial(true)
        .toList()

    return PaginatedResult(
        page, limit, list.count(), list
    )
}
