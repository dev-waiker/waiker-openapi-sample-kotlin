# Waiker-openapi-sample-kotlin

## REST API 요청 포맷
모든 웨이커 API 는 https를 통해 요청됩니다.
API를 요청하기 위해서는 인증 정보를 Header에 담아 같이 요청해야 합니다.

## 인증 정보 생성을 위한 사전 작업
인증 정보를 생성하기위해 사전에 웨이커 대시보드에서 생성한 
- **Product Key**
- **UserKey**
- **SecretKey**

위 세가지 정보가 필요합니다.

## 인증 헤더 정보
HTTP Header로 인증정보를 전달하며 필요한 인증 정보는 아래와 같습니다.

| Header | value |
|-------------|-------------|
|Waiker-Product-Key|웨이커에서 발행되는 프로덕트 키|
|Authorization|Bearer {웨이커에서 발행되는 유저키와 시크릿 키를 이용하여 생성한 JWT}|

## JWT 생성 방법
웨이커에서 발행되는 **User Key**를 **Secret Key**로 서명한 JWT입니다.
서명방식은 **HS256**을 사용하며, payload는 아래와 같습니다.
```
{
  "userKey" : "{userKey}"
}
```

## JWT 생성 예시 코드
```kotlin
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
```


