package com.example.springjpa.config;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

public class ManagedIdentityRedisConnectionFactory  extends LettuceConnectionFactory {

	private final RedisURI redisURI;

	public ManagedIdentityRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration,
			LettuceClientConfiguration clientConfiguration) {
		super(standaloneConfiguration, clientConfiguration);

		RedisURI.Builder builder = RedisURI.Builder
				.redis(standaloneConfiguration.getHostName(), standaloneConfiguration.getPort())
				.withDatabase(standaloneConfiguration.getDatabase())
				.withSsl(clientConfiguration.isUseSsl())
				.withVerifyPeer(clientConfiguration.isVerifyPeer())
				.withStartTls(clientConfiguration.isStartTls())
				.withTimeout(clientConfiguration.getCommandTimeout());
		clientConfiguration.getClientName().ifPresent(builder::withClientName);

		this.redisURI = builder.build();
		this.redisURI.setUsername(standaloneConfiguration.getUsername());
		standaloneConfiguration.getPassword().toOptional().ifPresent(this.redisURI::setPassword);
	}

	@Override
	protected AbstractRedisClient createClient() {
		RedisClient redisClient = getClientConfiguration().getClientResources()
				.map(clientResources -> RedisClient.create(clientResources, redisURI))
				.orElseGet(() -> RedisClient.create(redisURI));
		getClientConfiguration().getClientOptions().ifPresent(redisClient::setOptions);
		return redisClient;
	}

	/**
	 * Replaces the credentials the next connections will authenticate with. The connections that are already open keep
	 * the credentials they were opened with until they are re-authenticated with an AUTH command.
	 */
	public void updateCredentials(String username, String accessToken) {
		redisURI.setUsername(username);
		redisURI.setPassword(accessToken != null ? accessToken.toCharArray() : null);
		cf.
	}

}
