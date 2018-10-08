package com.qzq.haha.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {
	private JedisPool jedisPool;
	
	public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,
			final int port){
		try {
			jedisPool = new JedisPool(poolConfig,host,port);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
