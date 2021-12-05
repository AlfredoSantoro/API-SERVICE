package com.unisa.sesalabapi.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.OffsetDateTime
import java.util.*

/**
 * JwtManager is responsible for creating and verifying the token
 */
object JwtManager
{
    private val logger: Logger = LoggerFactory.getLogger(JwtManager::class.java)
    private const val issuer = "sesalab-api"

    /**
     * Create jwt string.
     *
     * @param subject the subject
     * @return the JWT string
     */
    fun createJwt(subject: String, secret: String, authority: String): String
    {
        this.logger.info("### creating JWT for subject #$subject")
        val builder = JWT.create()
                .withSubject(subject)
                .withIssuer(this.issuer)
                .withIssuedAt(Date.from(OffsetDateTime.now().toInstant()))
                .withExpiresAt(Date.from(OffsetDateTime.now().plusDays(1).toInstant()))
                .withClaim("authority", authority)
        val jwt = builder.sign(Algorithm.HMAC256(secret))
        this.logger.info("### jwt created successfully")
        return jwt
    }

    /**
     * Verify jwt.
     *
     * @param jwt the JWT string
     * @return a verified and decoded JWT
     */
    fun verifyJwt(jwt: String, secret: String): DecodedJWT
    {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt)
    }
}