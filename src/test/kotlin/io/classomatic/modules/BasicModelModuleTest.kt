package io.classomatic.modules;

import com.github.javafaker.Faker
import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Name
import io.classomatic.model.IdObject
import io.classomatic.plugins.configureMockedDb
import io.classomatic.plugins.configureServices
import io.ktor.client.call.*

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.bson.types.ObjectId
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import java.util.*
import kotlin.test.Test

@Entity("Persons")
data class Person(
    @Id
    @Name("_id")
    override val _id: ObjectId,

    @Name("name")
    val name: String,

    @Name("age")
    val age: Int,
): IdObject

class BasicModelModuleTest {
    private val faker = Faker(Locale.FRENCH)

    private fun Application.basicConfig() {
        configureServices()
        basicModelModule<Person>()
    }

    private fun generateData(amount: Int): List<Person> {
        val persons = mutableListOf<Person>()

        for (i in 0..amount) {
            persons.add(Person(ObjectId(), faker.leagueOfLegends().champion(), faker.number().numberBetween(0, 100)))
        }

        return persons
    }

    @Test
    fun testGetApi() = testApplication {
        application {
            basicConfig()
        }
        client.get("/api/person").apply {
            val list = this.body<List<Person>>()
            assertThat(list.count(), CoreMatchers.equalTo(10))
        }
    }

    @Test
    fun testPostApi() = testApplication {
        // Create comparative fake value
        val fakePerson = generateData(1).first()

        application {
            basicConfig()
        }
        client.post("/api/person") {
            this.setBody(fakePerson)
        }.apply {
            val person = this.body<Person>()

            // Compare the values
            assertThat(person.name, CoreMatchers.equalTo(fakePerson.name))
        }
    }

    @Test
    fun testDeleteApiId() = testApplication {
        application {
            basicConfig()
        }
        client.delete("/api/person/{id}") {

        }.apply {

        }
    }

    @Test
    fun testGetApiId() = testApplication {
        application {
            basicConfig()
        }
        client.get("/api/person/{id}").apply {
            TODO("Please write your test here")
        }
    }
}