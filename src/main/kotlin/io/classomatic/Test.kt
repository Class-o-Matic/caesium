package io.classomatic

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Location
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Uid
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale.GREGORIAN
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion.VERSION_2_0
import net.fortuna.ical4j.util.UidGenerator
import java.util.*

fun main() {
    val calendar = Calendar()
        .withProperty(ProdId("-//Margaux Raeymaeckers//iCal4j 1.0//EN"))
        .withProperty(VERSION_2_0)
        .withProperty(GREGORIAN)
        .fluentTarget

    val uidGenerator = UidGenerator { Uid(UUID.randomUUID().toString()) }

    val event = VEvent(LocalDateTime(2023, 10, 18, 0, 0).toJavaLocalDateTime(), "A test event")
        .withProperty(uidGenerator.generateUid())
        .withProperty(Location("Local 10"))
        .getFluentTarget<VEvent>()

    calendar.withComponent(event)

    val out = CalendarOutputter()
    out.output(calendar, System.out)
}