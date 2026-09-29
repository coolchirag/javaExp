package com.example.springjpa.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;

//@Configuration
public class RedisConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);
	
    public static final String REDIS_STORAGE_KEY = "saas-license-redis-storage-key";
	
	
	
	public String getAzureRedisPassword() throws Exception {
		String redisStoragePassword = "MySecretPassword123";
		return redisStoragePassword;
	}
	
	@Bean
	public RefreshableRedisConnectionFactory getRedisConnectionFactory() throws Exception {
		return new RefreshableRedisConnectionFactory(this::createLettuceConnectionFactory, getAzureRedisPassword());
	}

	private LettuceConnectionFactory createLettuceConnectionFactory(String redisPassword) {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setUsername("john");

        if(redisPassword != null) {
        	configuration.setPassword(redisPassword);
        }
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfig =
        		LettuceClientConfiguration.builder()
        				.commandTimeout(Duration.ofSeconds(3))
        				.clientOptions(ClientOptions.builder()
        						.socketOptions(SocketOptions.builder()
        								.connectTimeout(Duration.ofSeconds(5))
        								.keepAlive(true)
        								.build())
        						.timeoutOptions(TimeoutOptions.enabled())
        						.autoReconnect(true)
        						.disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
        						.build());
       
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration, clientConfig.build());
        lettuceConnectionFactory.afterPropertiesSet();

        return lettuceConnectionFactory;
	}

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) throws Exception {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
