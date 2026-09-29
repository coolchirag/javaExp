package com.example.springjpa.config;

import java.net.SocketAddress;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisChannelHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionStateListener;
import io.lettuce.core.RedisURI;
import io.lettuce.core.StatefulRedisConnectionImpl;

public class ManagedIdentityRedisConnectionFactory  extends LettuceConnectionFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ManagedIdentityRedisConnectionFactory.class);

	//private RedisURI redisURI;

	private final Supplier<String> reconnectPasswordSupplier;

	/**
	 * @param reconnectPasswordSupplier supplies the password every time a dropped connection is about to be re-established
	 */
	public ManagedIdentityRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration,
			LettuceClientConfiguration clientConfiguration, Supplier<String> reconnectPasswordSupplier) {
		super(standaloneConfiguration, clientConfiguration);
		this.reconnectPasswordSupplier = reconnectPasswordSupplier;

		/*RedisURI.Builder builder = RedisURI.Builder
				.redis(standaloneConfiguration.getHostName(), standaloneConfiguration.getPort())
				.withDatabase(standaloneConfiguration.getDatabase())
				.withSsl(clientConfiguration.isUseSsl())
				.withVerifyPeer(clientConfiguration.isVerifyPeer())
				.withStartTls(clientConfiguration.isStartTls())
				.withTimeout(clientConfiguration.getCommandTimeout());
		clientConfiguration.getClientName().ifPresent(builder::withClientName);

		this.redisURI = builder.build();
		this.redisURI.setUsername(standaloneConfiguration.getUsername());
		standaloneConfiguration.getPassword().toOptional().ifPresent(this.redisURI::setPassword);*/
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		AbstractRedisClient redisClient = getNativeClient();
		redisClient.addListener(new ReconnectPasswordListener());
	}





	/**
	 * Lettuce keeps the credentials in the state of every open connection and re-sends them on each automatic reconnect,
	 * so the disconnect event (which fires before the reconnect attempt) is used to swap in a freshly generated password.
	 */
	private class ReconnectPasswordListener implements RedisConnectionStateListener {

		@Override
		public void onRedisDisconnected(RedisChannelHandler<?, ?> connection) {
			if(!(connection instanceof StatefulRedisConnectionImpl)) {
				return;
			}
			try {
				RedisURI redisURI = new RedisURI();
				redisURI.setUsername("john");
				redisURI.setPassword(reconnectPasswordSupplier.get().toCharArray());
				((StatefulRedisConnectionImpl<?, ?>) connection).getConnectionState().apply(redisURI);
				LOG.info("Redis connection lost, reconnecting with a newly generated password");
			} catch (RuntimeException e) {
				LOG.warn("Could not refresh the redis password before reconnecting : " + e.getMessage(), e);
			}
		}

		@Override
		public void onRedisExceptionCaught(RedisChannelHandler<?, ?> connection, Throwable cause) {
			System.out.println("OnRedisEception");
		}
		
		@Override
		public void onRedisConnected(RedisChannelHandler<?, ?> connection, SocketAddress socketAddress) {
			// TODO Auto-generated method stub
			RedisConnectionStateListener.super.onRedisConnected(connection, socketAddress);
		}
	}

}
