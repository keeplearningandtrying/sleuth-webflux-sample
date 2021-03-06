package sample.load.web

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters.fromObject
import sample.load.Message
import sample.load.MessageHandler
import sample.load.config.RoutesConfig

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = arrayOf(RoutesConfig::class, MessageHandler::class))
class MessageHandlerControllerTest {
    
    @Autowired
    private lateinit var webTestClient: WebTestClient
    
    @Test
    fun testCallToMessageEndpoint() {
        webTestClient.post().uri("/messages")
                .body(fromObject(Message("1", "one", 0)))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(""" 
                    | {
                    |   "id": "1",
                    |   "received": "one",
                    |   "ack": "ack"
                    | }
                """.trimMargin())
    }

}