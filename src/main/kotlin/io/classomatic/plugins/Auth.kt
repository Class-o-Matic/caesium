package io.classomatic.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.net.URL

fun Application.configureAuth() {
    // TODO: Replace string by properties' url
    val issuer = URL("http://localhost:18080/realms/ktor/protocol/openid-connect/certs")

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