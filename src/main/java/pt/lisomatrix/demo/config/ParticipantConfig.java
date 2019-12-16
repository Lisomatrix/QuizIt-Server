package pt.lisomatrix.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pt.lisomatrix.demo.model.Participant;
import pt.lisomatrix.demo.model.Room;

@Configuration
public class ParticipantConfig {

    @Bean
    public ReactiveRedisTemplate<String, Participant> reactiveRedisTemplateParticipant(@Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Participant> valueSerializer = new Jackson2JsonRedisSerializer<>(Participant.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Participant> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Participant> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
