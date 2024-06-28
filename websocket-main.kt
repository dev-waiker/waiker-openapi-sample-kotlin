package com.example.demo

import com.example.demo.client.StompClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.web.socket.WebSocketHttpHeaders

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)

    val productKey = "***********"
    val authKey = "Bearer *****************"
    val destination = "/news/us"

    val stompClient = StompClient()
    val sessionHandler = StompClient.MyStompSessionHandler(
        productKey = productKey,
        authKey = authKey,
        destination = destination
    )

    val connectHeader = WebSocketHttpHeaders()
    connectHeader["Waiker-Product-Key"] = productKey
    connectHeader["Authorization"] = authKey
    connectHeader["Sec-Websocket-Protocol"] = "v12.stomp, v11.stomp, v10.stomp"
    connectHeader["host"] = "oapi.waiker.ai"
    connectHeader["accept-version"] ="13"
    connectHeader["User-Agent"] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36"

    stompClient.connect("wss://oapi.waiker.ai/v2/center-ws", connectHeader, sessionHandler)
}
