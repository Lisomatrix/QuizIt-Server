package pt.lisomatrix.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pt.lisomatrix.demo.model.Room;

@Configuration
public class RoomsConfig {

    @Bean
    public ReactiveRedisTemplate<String, Room> reactiveRedisTemplateRoom(@Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Room> valueSerializer = new Jackson2JsonRedisSerializer<>(Room.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Room> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Room> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
