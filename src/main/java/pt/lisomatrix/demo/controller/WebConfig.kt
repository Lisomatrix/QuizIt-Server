package pt.lisomatrix.demo.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import java.net.URI

@Configuration
open class WebConfiguration {
    
    @Bean
    open fun indexRouter(): RouterFunction<ServerResponse> {
        val redirectToIndex =
                ServerResponse
                        .temporaryRedirect(URI("/index.html"))
                        .build()

        return router {
            GET("/") {
                redirectToIndex // also you can create request here
            }
        }
    }
}