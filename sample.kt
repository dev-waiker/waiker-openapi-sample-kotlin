import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.databind.ObjectMapper

class OpenApiSample {
    fun main(args: Array<String>) {
        val userKey = "발급 받은 User Key"
        val secretKey = "발급 받은 Secret Key"

        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)

        val jwtToken: String = JWT.create()
            .withClaim("userKey", accessKey)
            .sign(algorithm)

        val objectMapper = ObjectMapper()        
        val template = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Waiker-Product-Key", "발급받은 웨이커 프로덕트 키")
        headers.set("Authorization", "Bearer ${jwtToken}")

        runCatching {
            template.getForObject(
                "https://oapi.waiker.ai/v2/center/ai-news",
                APIPage::class.java
            )
        }.onFailure {
            println(it.message)
        }.onSuccess {
            it?.let { res ->
                val data = res.data.map { item ->
                    objectMapper.convertValue(item, AiNewsResponse::class.java)
                }

                println(data.toString())
            }
        }
    }
}

data class APIPage<T>(
    val data: List<T>,
    val page: Int?,
    val size: Int?,
    val totalPage: Int,
    val totalCount: Long,
    val code: String,
    val status: Int,
    val message: String,
    val timestamp: String
)

class AiNewsResponse(
    val id: Long,
    val originTitle: String,
    val originBody: String,
    val titleKo: String?,
    val bodyKo: String?,
    val originPress: String?,
    val waikerTags: List<String>?,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    val publishedDt: ZonedDateTime,
    val landingUrl: String
)