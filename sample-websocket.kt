class ClientWebSocketStompConfig {
    fun webSocketStompClient(
        websocketStompClient: WebSocketStompClient,
        stompSessionHandler: StompSessionHandler
    ): WebSocketStompClient {
        websocketStompClient.messageConverter = MappingJackson2MessageConverter()

        val headers = StompHeaders()
        val url = "wss://oapi.waiker.ai/v2/center-ws"
        websocketStompClient.connect(url, null, headers, stompSessionHandler)

        return websocketStompClient
    }
}

class ClientWebSocketStompSessionHandler : StompSessionHandlerAdapter() {
    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        val headers = StompHeaders()
        headers.add("Waiker-Product-Key", "발급받은 웨이커 프로덕트 키");
        headers.add("Authorization", "Bearer ${jwtToken}");
        headers.add("destination", "/news/{regionCode}")
        session.subscribe(headers, this)
    }
}
