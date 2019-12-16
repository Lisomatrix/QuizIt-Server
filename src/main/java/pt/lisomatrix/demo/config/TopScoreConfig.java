package pt.lisomatrix.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import pt.lisomatrix.demo.model.TopScore;

@Configuration
public class TopScoreConfig {

    @Bean
    public ReactiveRedisTemplate<String, TopScore> reactiveRedisTemplateTopScore(@Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<TopScore> valueSerializer = new Jackson2JsonRedisSerializer<>(TopScore.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, TopScore> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, TopScore> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
