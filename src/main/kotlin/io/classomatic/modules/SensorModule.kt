package io.classomatic.modules

import io.classomatic.model.Sensor
import io.ktor.server.application.*

@Suppress("unused")
fun Application.sensorModule() {
    return basicModelModule<Sensor>()
}
