package io.classomatic.modules

import io.classomatic.model.Reservation
import io.ktor.server.application.*

fun Application.reservationModule() {
    return basicModelModule<Reservation>()
}
