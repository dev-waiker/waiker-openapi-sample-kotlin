import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.*
import org.springframework.stereotype.Component
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.lang.reflect.Type

@Component
class StompClient {

    private lateinit var stompClient: WebSocketStompClient

    init {
        val websocketClient = StandardWebSocketClient()
        stompClient = WebSocketStompClient(websocketClient)
        stompClient.messageConverter = MappingJackson2MessageConverter()
    }

    fun connect(url: String, sessionHandler: StompSessionHandler): StompSession {
        val connectFuture = stompClient.connect(url, sessionHandler)
        return connectFuture.get()
    }

    class MyStompSessionHandler(
        private val productKey: String,
        private val authKey: String,
        private val destination: String
    ) : StompSessionHandlerAdapter() {

        override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
            println("Connected to STOMP server")
            val headers = StompHeaders()
            headers["destination"] = destination
            headers["Waiker-Product-Key"] = productKey
            headers["Authorization"] = authKey
            session.subscribe(headers, this)
        }

        override fun getPayloadType(headers: StompHeaders): Type {
            return String::class.java
        }

        override fun handleFrame(headers: StompHeaders, payload: Any?) {
            println("Received: $payload")
        }

        override fun handleTransportError(session: StompSession, exception: Throwable) {
            println("Transport error: ${exception.message}")
        }

        override fun handleException(
            session: StompSession,
            command: StompCommand?,
            headers: StompHeaders,
            payload: ByteArray,
            exception: Throwable
        ) {
            println("Error: ${exception.message}")
        }
    }
}
