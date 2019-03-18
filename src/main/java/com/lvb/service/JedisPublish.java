package com.lvb.service;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class JedisPublish {
	final JedisPoolConfig poolConfig = new JedisPoolConfig();
 	final JedisPool jedisPool = new JedisPool(poolConfig, "162.241.222.49", 6379, 0);
	final Jedis publishJedis = jedisPool.getResource();
	 

	public void publish(String filename){
		publishJedis.publish("notification", filename);
	}

}
