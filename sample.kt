import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

class OpenApiSample {
    fun main(args: Array<String>) {
        val userKey = "발급 받은 User Key"
        val secretKey = "발급 받은 Secret Key"

        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)

        val jwtToken: String = JWT.create()
            .withClaim("userKey", accessKey)
            .sign(algorithm)

        println("Bearer $jwtToken")
    }
}