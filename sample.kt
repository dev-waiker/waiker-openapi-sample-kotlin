import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate

class OpenApiSample {
    fun main(args: Array<String>) {
        val userKey = "발급 받은 User Key"
        val secretKey = "발급 받은 Secret Key"

        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)

        val jwtToken: String = JWT.create()
            .withClaim("userKey", accessKey)
            .sign(algorithm)
        
        val template = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Waiker-Product-Key", "발급받은 웨이커 프로덕트 키")
        headers.set("Authorization", "Bearer ${jwtToken}")

        runCatching {
            template.getForObject(
                "https://oapi.waiker.ai/v2/center/ai-news",
                Map::class.java
            )
        }.onFailure {
            println(it.message)
        }.onSuccess {
            // TODO
        }
    }
}