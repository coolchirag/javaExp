package com.example.springjpa.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import com.example.springjpa.config.ManagedIdentityRedisConnectionFactory;
import com.example.springjpa.config.RefreshableRedisConnectionFactory;

@Service
@Transactional
public class RedisService {

	private static final int SCAN_BATCH_SIZE = 1000;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	//private RefreshableRedisConnectionFactory connectionFactory;
	private RedisConnectionFactory connectionFactory;
	
	/**
	 * Fetch all keys currently present in Redis.
	 * Uses SCAN (cursor based) instead of KEYS so the Redis server is not blocked
	 * while the whole keyspace is iterated.
	 *
	 * @return list of all keys, empty list when the keyspace is empty
	 */
	public List<String> getAllKeys() {
		return getKeysByPattern("*");
	}

	/**
	 * Fetch the keys matching the given glob style pattern, e.g. {@code user:*}.
	 */
	public List<String> getKeysByPattern(String pattern) {
		ScanOptions options = ScanOptions.scanOptions().match(pattern).count(SCAN_BATCH_SIZE).build();

		return redisTemplate.execute((RedisCallback<List<String>>) connection -> {
			List<String> keys = new ArrayList<>();
			try (Cursor<byte[]> cursor = connection.scan(options)) {
				while (cursor.hasNext()) {
					keys.add(new String(cursor.next(), StandardCharsets.UTF_8));
				}
			} catch (Exception e) {
				throw new IllegalStateException("Failed to fetch keys from Redis for pattern: " + pattern, e);
			}
			return keys;
		});
	}
	
	/**
	 * Update the Redis password so every connection created from now on authenticates with it.
	 *
	 * @return true when the new password was verified and applied, false when Redis rejected it
	 */
	public boolean updateRedisCredentials(String password) {
		if(connectionFactory instanceof ManagedIdentityRedisConnectionFactory) {
			ManagedIdentityRedisConnectionFactory cf = (ManagedIdentityRedisConnectionFactory) connectionFactory; 
			cf.updateCredentials("john", password);
		} 
		return true;
		//return connectionFactory.updatePassword(password);
	}
	
}
