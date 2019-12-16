package pt.lisomatrix.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import pt.lisomatrix.demo.handler.CustomWebSocketHandler;
import pt.lisomatrix.demo.service.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class PtProject {

	@Autowired
	private RoomService roomService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private TopScoreService topScoreService;

	@Bean
	public HandlerMapping webSocketMapping() {
		Map<String, Object> map = new HashMap<>();
		map.put("/websocket/chat", new CustomWebSocketHandler(roomService, participantService, questionService, topScoreService));
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		simpleUrlHandlerMapping.setUrlMap(map);
		simpleUrlHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return simpleUrlHandlerMapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}


	public static void main(String[] args) {
		SpringApplication.run(PtProject.class, args);
	}

}
