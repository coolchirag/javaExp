package com.example.springjpa.config;


import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
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

@Configuration
public class RedisConfig2 {
	
	private String password = "MySecretPassword123";
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfig2.class);
	
	private volatile ManagedIdentityRedisConnectionFactory managedIdentityConnectionFactory;
	
	private static final String[] AUTHENTICATION_FAILURE_MARKERS = { "WRONGPASS", "NOAUTH", "NOPERM",
	"INVALID USERNAME-PASSWORD" };


	@Bean
	public LettuceConnectionFactory getRedisConnectionFactory() throws Exception {
		
		ManagedIdentityRedisConnectionFactory connectionFactory = createManagedIdentityConnectionFactory();
		
		this.managedIdentityConnectionFactory = connectionFactory;
		
		return connectionFactory;
	}

	/**
	 * Authenticates with the object id and the access token of the azure managed identity, then proves the credentials
	 * are actually accepted before the factory is handed out. A managed identity that carries no redis access policy
	 * would otherwise start up cleanly and fail on every single redis operation afterwards.
	 *
	 * @return the connection factory, or <code>null</code> when the managed identity cannot be used at all.
	 */
	private ManagedIdentityRedisConnectionFactory createManagedIdentityConnectionFactory() {
		ManagedIdentityRedisConnectionFactory connectionFactory = null;
		try {
			RedisStandaloneConfiguration configuration = createStandaloneConfiguration();
			configuration.setUsername("john");
			configuration.setPassword(password);

			connectionFactory = new ManagedIdentityRedisConnectionFactory(configuration, createClientConfiguration(),
						this::passwordGenerator);
			connectionFactory.afterPropertiesSet();

			//verifyConnection(connectionFactory);
			LOG.info("Redis authenticated with azure managed identity");
			return connectionFactory;
		} catch (Exception e) {
			LOG.warn("Could not authenticate redis with azure managed identity : " + e.getMessage(), e);
			destroyQuietly(connectionFactory);
			return null;
		}
	}

	/**
	 * Opens a real connection so that a rejected token surfaces here instead of at the first redis call. A redis that
	 * is merely unreachable is not held against the managed identity, the client reconnects on its own once redis is
	 * back and falling back on a password at that point would only replace one broken setup with another.
	 */
	private void verifyConnection(LettuceConnectionFactory connectionFactory) {
		try {
			RedisConnection connection = connectionFactory.getConnection();
			try {
				connection.ping();
			} finally {
				connection.close();
			}
		} catch (RuntimeException e) {
			if(isAuthenticationFailure(e)) {
				throw e;
			}
			LOG.warn("Could not reach redis while verifying the managed identity credentials, keeping the managed "
					+ "identity authentication : " + e.getMessage(), e);
		}
	}

	private boolean isAuthenticationFailure(Throwable throwable) {
		Throwable cause = throwable;
		for (int depth = 0; cause != null && depth < 10; depth++) {
			final String message = cause.getMessage();
			if(message != null) {
				final String upperCasedMessage = message.toUpperCase();
				for (String marker : AUTHENTICATION_FAILURE_MARKERS) {
					if(upperCasedMessage.contains(marker)) {
						return true;
					}
				}
			}
			cause = cause.getCause() == cause ? null : cause.getCause();
		}
		return false;
	}

	private void destroyQuietly(LettuceConnectionFactory connectionFactory) {
		if(connectionFactory == null) {
			return;
		}
		try {
			connectionFactory.destroy();
		} catch (Exception e) {
			LOG.warn("Error occured while discarding the redis connection factory : " + e.getMessage(), e);
		}
	}

	private RedisStandaloneConfiguration createStandaloneConfiguration() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setUsername("john");
        configuration.setPassword(password);
        
        return configuration;
	}
	
	/*
	 * public String getAzureRedisPassword() { String redisStoragePassword =
	 * "MySecretPassword123"; return redisStoragePassword; }
	 */

	private LettuceClientConfiguration createClientConfiguration() {
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
        
        return clientConfig.build();
	}
	
	public void updatePassword(String newPassword) {
		password = newPassword;
		//managedIdentityConnectionFactory.updateCredentials("john", newPassword);
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
	
	public String passwordGenerator() {
		return "MySecretPassword1234";
	}
}
