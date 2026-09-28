package com.example.springjpa.config;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * {@link RedisConnectionFactory} that delegates to a {@link LettuceConnectionFactory} which can be swapped at runtime.
 * <p>
 * A {@link LettuceConnectionFactory} bakes the password into its RedisClient during {@code afterPropertiesSet()},
 * so changing the password on an initialized factory has no effect on new connections. Instead, a new factory is
 * built with the new password, verified, and swapped in; the old one is then destroyed.
 */
public class RefreshableRedisConnectionFactory implements RedisConnectionFactory, DisposableBean {

	private static final Logger LOG = LoggerFactory.getLogger(RefreshableRedisConnectionFactory.class);

	private final Function<String, LettuceConnectionFactory> factoryCreator;

	private volatile LettuceConnectionFactory delegate;

	public RefreshableRedisConnectionFactory(Function<String, LettuceConnectionFactory> factoryCreator, String password) {
		this.factoryCreator = factoryCreator;
		this.delegate = factoryCreator.apply(password);
	}

	/**
	 * Switch to a new password. All connections obtained after this call use the new password.
	 * The new password is verified with a PING before switching; on failure the current factory is kept.
	 *
	 * @return true when the new password was applied, false when authentication with it failed
	 */
	public synchronized boolean updatePassword(String password) {
		LettuceConnectionFactory newFactory = factoryCreator.apply(password);
		try (RedisConnection connection = newFactory.getConnection()) {
			connection.ping();
		} catch (Exception e) {
			LOG.error("Unable to connect to Redis with the new password, keeping the current one", e);
			newFactory.destroy();
			return false;
		}

		LettuceConnectionFactory oldFactory = this.delegate;
		this.delegate = newFactory;
		oldFactory.destroy();
		LOG.info("Redis password updated, new connections will use the new password");
		return true;
	}

	@Override
	public RedisConnection getConnection() {
		return delegate.getConnection();
	}

	@Override
	public RedisClusterConnection getClusterConnection() {
		return delegate.getClusterConnection();
	}

	@Override
	public boolean getConvertPipelineAndTxResults() {
		return delegate.getConvertPipelineAndTxResults();
	}

	@Override
	public RedisSentinelConnection getSentinelConnection() {
		return delegate.getSentinelConnection();
	}

	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return delegate.translateExceptionIfPossible(ex);
	}

	@Override
	public void destroy() {
		delegate.destroy();
	}
}
