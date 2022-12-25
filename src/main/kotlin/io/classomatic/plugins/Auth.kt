package io.classomatic.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.net.URL

fun Application.configureAuth() {
    val issuerURL = environment.config.property("ktor.auth.jwt.issuer").getString()
    val issuer = URL(issuerURL)

    val jwksProvider = JwkProviderBuilder(issuer)
        .build()

    authentication {
        jwt("rfid-auth") {
            verifier(jwksProvider) {
                withIssuer()
            }

            validate {
                JWTPrincipal(it.payload)
            }
        }
    }
}