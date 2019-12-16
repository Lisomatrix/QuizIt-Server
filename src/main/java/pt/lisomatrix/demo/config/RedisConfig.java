package pt.lisomatrix.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pt.lisomatrix.demo.model.Room;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        //return new LettuceConnectionFactory("localhost", 6379);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("ec2-34-250-15-37.eu-west-1.compute.amazonaws.com");
        configuration.setPort(11789);
        configuration.setPassword("p502c069a15b9539dc3f19f05c9c05c86adffdf5448fac97ad138010d9f37fbae");

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString(@Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }

    @Bean
    ReactiveRedisOperations<String, Room> redisOperations(@Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Room> serializer = new Jackson2JsonRedisSerializer<>(Room.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Room> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Room> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
