package com.qzq.haha.cache;


import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

public class JedisUtil {
	
   private final int expire = 60000;
	
   public Keys KEYS;
   
   public Strings STRINGS;
   
   public Lists LISTS;
	/** 对存储结构为Set类型的操作 */
   
	public Sets SETS;
	/** 对存储结构为HashMap类型的操作 */
	
	public Hash HASH;
	
   private JedisPool jedisPool;

public JedisPool getJedisPool() {
	return jedisPool;
}

public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
	this.jedisPool = jedisPoolWriper.getJedisPool();
}

public Jedis getJedis(){
	return jedisPool.getResource();
}
public void expire(String key, int seconds) {
	if (seconds <= 0) {
		return;
	}
	Jedis jedis = getJedis();
	jedis.expire(key, seconds);
	jedis.close();
}


public void expire(String key) {
	expire(key, expire);

}

public Keys getKEYS() {
	return KEYS;
}

public void setKEYS(Keys kEYS) {
	KEYS = kEYS;
}

public Strings getSTRINGS() {
	return STRINGS;
}

public void setSTRINGS(Strings sTRINGS) {
	STRINGS = sTRINGS;
}

//******************************keys*****************
public class Keys{
	public String flushAll(){
		Jedis jedis = getJedis();
		String stata= jedis.flushAll();
		jedis.close();
		return stata;
	}
	public String rename(String oldkey,String newkey){
		return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
	}
	
	public String rename(byte[] oldkey, byte[] newkey) {
		Jedis jedis = getJedis();
		String status = jedis.rename(oldkey, newkey);
		jedis.close();
		return status;
	}
	public long renamenx(String oldkey, String newkey) {
		Jedis jedis = getJedis();
		long status = jedis.renamenx(oldkey, newkey);
		jedis.close();
		return status;
	}
	public long expired(String key, int seconds) {
		Jedis jedis = getJedis();
		long count = jedis.expire(key, seconds);
		jedis.close();
		return count;
	}
	public long expireAt(String key, long timestamp) {
		Jedis jedis = getJedis();
		long count = jedis.expireAt(key, timestamp);
		jedis.close();
		return count;
	}
	public long ttl(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		long len = sjedis.ttl(key);
		sjedis.close();
		return len;
	}
	public long persist(String key) {
		Jedis jedis = getJedis();
		long count = jedis.persist(key);
		jedis.close();
		return count;
	}
	public long del(String... keys) {
		Jedis jedis = getJedis();
		long count = jedis.del(keys);
		jedis.close();
		return count;
	}
	public long del(byte[]... keys) {
		Jedis jedis = getJedis();
		long count = jedis.del(keys);
		jedis.close();
		return count;
	}
	public boolean exists(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		boolean exis = sjedis.exists(key);
		sjedis.close();
		return exis;
	}
	public List<String> sort(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		List<String> list = sjedis.sort(key);
		sjedis.close();
		return list;
	}
	public List<String> sort(String key, SortingParams parame) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		List<String> list = sjedis.sort(key, parame);
		sjedis.close();
		return list;
	}
	public String type(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		String type = sjedis.type(key);
		sjedis.close();
		return type;
	}
	public Set<String> keys(String pattern) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.keys(pattern);
		jedis.close();
		return set;
	}
}
//*******************Set*********************************
public class Sets{
	public long sadd(String key, String member) {
		Jedis jedis = getJedis();
		long s = jedis.sadd(key, member);
		jedis.close();
		return s;
	}

	public long sadd(byte[] key, byte[] member) {
		Jedis jedis = getJedis();
		long s = jedis.sadd(key, member);
		jedis.close();
		return s;
	}
	public long scard(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		long len = sjedis.scard(key);
		sjedis.close();
		return len;
	}
	public Set<String> sdiff(String... keys) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.sdiff(keys);
		jedis.close();
		return set;
	}
	public long sdiffstore(String newkey, String... keys) {
		Jedis jedis = getJedis();
		long s = jedis.sdiffstore(newkey, keys);
		jedis.close();
		return s;
	}
	public Set<String> sinter(String... keys) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.sinter(keys);
		jedis.close();
		return set;
	}
	
	public long sinterstore(String newkey, String... keys) {
		Jedis jedis = getJedis();
		long s = jedis.sinterstore(newkey, keys);
		jedis.close();
		return s;
	}
	public boolean sismember(String key, String member) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		boolean s = sjedis.sismember(key, member);
		sjedis.close();
		return s;
	}
	public Set<String> smembers(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		Set<String> set = sjedis.smembers(key);
		sjedis.close();
		return set;
	}
	public Set<byte[]> smembers(byte[] key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getJedis();
		Set<byte[]> set = sjedis.smembers(key);
		sjedis.close();
		return set;
	}
	public long smove(String srckey, String dstkey, String member) {
		Jedis jedis = getJedis();
		long s = jedis.smove(srckey, dstkey, member);
		jedis.close();
		return s;
	}
	public String spop(String key) {
		Jedis jedis = getJedis();
		String s = jedis.spop(key);
		jedis.close();
		return s;
	}
	public long srem(String key, String member) {
		Jedis jedis = getJedis();
		long s = jedis.srem(key, member);
		jedis.close();
		return s;
	}
	public Set<String> sunion(String... keys) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.sunion(keys);
		jedis.close();
		return set;
	}
	public long sunionstore(String newkey, String... keys) {
		Jedis jedis = getJedis();
		long s = jedis.sunionstore(newkey, keys);
		jedis.close();
		return s;
	}
}
//*******************************************Hash*******************************************//
	public class Hash {

		/**
		 * 从hash中删除指定的存储
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储的名字
		 * @return 状态码，1成功，0失败
		 * */
		public long hdel(String key, String fieid) {
			Jedis jedis = getJedis();
			long s = jedis.hdel(key, fieid);
			jedis.close();
			return s;
		}

		public long hdel(String key) {
			Jedis jedis = getJedis();
			long s = jedis.del(key);
			jedis.close();
			return s;
		}

		/**
		 * 测试hash中指定的存储是否存在
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储的名字
		 * @return 1存在，0不存在
		 * */
		public boolean hexists(String key, String fieid) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			boolean s = sjedis.hexists(key, fieid);
			sjedis.close();
			return s;
		}

		/**
		 * 返回hash中指定存储位置的值
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储的名字
		 * @return 存储对应的值
		 * */
		public String hget(String key, String fieid) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			String s = sjedis.hget(key, fieid);
			sjedis.close();
			return s;
		}

		public byte[] hget(byte[] key, byte[] fieid) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			byte[] s = sjedis.hget(key, fieid);
			sjedis.close();
			return s;
		}

		/**
		 * 以Map的形式返回hash中的存储和值
		 * 
		 * @param String
		 *            key
		 * @return Map<Strinig,String>
		 * */
		public Map<String, String> hgetAll(String key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			Map<String, String> map = sjedis.hgetAll(key);
			sjedis.close();
			return map;
		}

		/**
		 * 添加一个对应关系
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid
		 * @param String
		 *            value
		 * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
		 * **/
		public long hset(String key, String fieid, String value) {
			Jedis jedis = getJedis();
			long s = jedis.hset(key, fieid, value);
			jedis.close();
			return s;
		}

		public long hset(String key, String fieid, byte[] value) {
			Jedis jedis = getJedis();
			long s = jedis.hset(key.getBytes(), fieid.getBytes(), value);
			jedis.close();
			return s;
		}

		/**
		 * 添加对应关系，只有在fieid不存在时才执行
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid
		 * @param String
		 *            value
		 * @return 状态码 1成功，0失败fieid已存
		 * **/
		public long hsetnx(String key, String fieid, String value) {
			Jedis jedis = getJedis();
			long s = jedis.hsetnx(key, fieid, value);
			jedis.close();
			return s;
		}

		/**
		 * 获取hash中value的集合
		 * 
		 * @param String
		 *            key
		 * @return List<String>
		 * */
		public List<String> hvals(String key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			List<String> list = sjedis.hvals(key);
			sjedis.close();
			return list;
		}

		/**
		 * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储位置
		 * @param String
		 *            long value 要增加的值,可以是负数
		 * @return 增加指定数字后，存储位置的值
		 * */
		public long hincrby(String key, String fieid, long value) {
			Jedis jedis = getJedis();
			long s = jedis.hincrBy(key, fieid, value);
			jedis.close();
			return s;
		}

		/**
		 * 返回指定hash中的所有存储名字,类似Map中的keySet方法
		 * 
		 * @param String
		 *            key
		 * @return Set<String> 存储名称的集合
		 * */
		public Set<String> hkeys(String key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			Set<String> set = sjedis.hkeys(key);
			sjedis.close();
			return set;
		}

		/**
		 * 获取hash中存储的个数，类似Map中size方法
		 * 
		 * @param String
		 *            key
		 * @return long 存储的个数
		 * */
		public long hlen(String key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			long len = sjedis.hlen(key);
			sjedis.close();
			return len;
		}

		/**
		 * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            ... fieids 存储位置
		 * @return List<String>
		 * */
		public List<String> hmget(String key, String... fieids) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			List<String> list = sjedis.hmget(key, fieids);
			sjedis.close();
			return list;
		}

		public List<byte[]> hmget(byte[] key, byte[]... fieids) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			List<byte[]> list = sjedis.hmget(key, fieids);
			sjedis.close();
			return list;
		}

		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * 
		 * @param Strin
		 *            key
		 * @param Map
		 *            <String,String> 对应关系
		 * @return 状态，成功返回OK
		 * */
		public String hmset(String key, Map<String, String> map) {
			Jedis jedis = getJedis();
			String s = jedis.hmset(key, map);
			jedis.close();
			return s;
		}

		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * 
		 * @param Strin
		 *            key
		 * @param Map
		 *            <String,String> 对应关系
		 * @return 状态，成功返回OK
		 * */
		public String hmset(byte[] key, Map<byte[], byte[]> map) {
			Jedis jedis = getJedis();
			String s = jedis.hmset(key, map);
			jedis.close();
			return s;
		}

	}

	// *******************************************Strings*******************************************//
	public class Strings {
		/**
		 * 根据key获取记录
		 * 
		 * @param String
		 *            key
		 * @return 值
		 * */
		public String get(String key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			String value = sjedis.get(key);
			sjedis.close();
			return value;
		}

		/**
		 * 根据key获取记录
		 * 
		 * @param byte[] key
		 * @return 值
		 * */
		public byte[] get(byte[] key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			byte[] value = sjedis.get(key);
			sjedis.close();
			return value;
		}

		/**
		 * 添加有过期时间的记录
		 * 
		 * @param String
		 *            key
		 * @param int seconds 过期时间，以秒为单位
		 * @param String
		 *            value
		 * @return String 操作状态
		 * */
		public String setEx(String key, int seconds, String value) {
			Jedis jedis = getJedis();
			String str = jedis.setex(key, seconds, value);
			jedis.close();
			return str;
		}

		/**
		 * 添加有过期时间的记录
		 * 
		 * @param String
		 *            key
		 * @param int seconds 过期时间，以秒为单位
		 * @param String
		 *            value
		 * @return String 操作状态
		 * */
		public String setEx(byte[] key, int seconds, byte[] value) {
			Jedis jedis = getJedis();
			String str = jedis.setex(key, seconds, value);
			jedis.close();
			return str;
		}

		/**
		 * 添加一条记录，仅当给定的key不存在时才插入
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return long 状态码，1插入成功且key不存在，0未插入，key存在
		 * */
		public long setnx(String key, String value) {
			Jedis jedis = getJedis();
			long str = jedis.setnx(key, value);
			jedis.close();
			return str;
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 状态码
		 * */
		public String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 状态码
		 * */
		public String set(String key, byte[] value) {
			return set(SafeEncoder.encode(key), value);
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * 
		 * @param byte[] key
		 * @param byte[] value
		 * @return 状态码
		 * */
		public String set(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.set(key, value);
			jedis.close();
			return status;
		}

		/**
		 * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
		 * 例:String str1="123456789";<br/>
		 * 对str1操作后setRange(key,4,0000)，str1="123400009";
		 * 
		 * @param String
		 *            key
		 * @param long offset
		 * @param String
		 *            value
		 * @return long value的长度
		 * */
		public long setRange(String key, long offset, String value) {
			Jedis jedis = getJedis();
			long len = jedis.setrange(key, offset, value);
			jedis.close();
			return len;
		}

		/**
		 * 在指定的key中追加value
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return long 追加后value的长度
		 * **/
		public long append(String key, String value) {
			Jedis jedis = getJedis();
			long len = jedis.append(key, value);
			jedis.close();
			return len;
		}

		/**
		 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
		 * 
		 * @param String
		 *            key
		 * @param long number 要减去的值
		 * @return long 减指定值后的值
		 * */
		public long decrBy(String key, long number) {
			Jedis jedis = getJedis();
			long len = jedis.decrBy(key, number);
			jedis.close();
			return len;
		}

		/**
		 * <b>可以作为获取唯一id的方法</b><br/>
		 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		 * 
		 * @param String
		 *            key
		 * @param long number 要减去的值
		 * @return long 相加后的值
		 * */
		public long incrBy(String key, long number) {
			Jedis jedis = getJedis();
			long len = jedis.incrBy(key, number);
			jedis.close();
			return len;
		}

		/**
		 * 对指定key对应的value进行截取
		 * 
		 * @param String
		 *            key
		 * @param long startOffset 开始位置(包含)
		 * @param long endOffset 结束位置(包含)
		 * @return String 截取的值
		 * */
		public String getrange(String key, long startOffset, long endOffset) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			String value = sjedis.getrange(key, startOffset, endOffset);
			sjedis.close();
			return value;
		}

		/**
		 * 获取并设置指定key对应的value<br/>
		 * 如果key存在返回之前的value,否则返回null
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return String 原始value或null
		 * */
		public String getSet(String key, String value) {
			Jedis jedis = getJedis();
			String str = jedis.getSet(key, value);
			jedis.close();
			return str;
		}

		/**
		 * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
		 * 
		 * @param String
		 *            keys
		 * @return List<String> 值得集合
		 * */
		public List<String> mget(String... keys) {
			Jedis jedis = getJedis();
			List<String> str = jedis.mget(keys);
			jedis.close();
			return str;
		}

		/**
		 * 批量存储记录
		 * 
		 * @param String
		 *            keysvalues 例:keysvalues="key1","value1","key2","value2";
		 * @return String 状态码
		 * */
		public String mset(String... keysvalues) {
			Jedis jedis = getJedis();
			String str = jedis.mset(keysvalues);
			jedis.close();
			return str;
		}

		/**
		 * 获取key对应的值的长度
		 * 
		 * @param String
		 *            key
		 * @return value值得长度
		 * */
		public long strlen(String key) {
			Jedis jedis = getJedis();
			long len = jedis.strlen(key);
			jedis.close();
			return len;
		}
	}

	// *******************************************Lists*******************************************//
	public class Lists {
		/**
		 * List长度
		 * 
		 * @param String
		 *            key
		 * @return 长度
		 * */
		public long llen(String key) {
			return llen(SafeEncoder.encode(key));
		}

		/**
		 * List长度
		 * 
		 * @param byte[] key
		 * @return 长度
		 * */
		public long llen(byte[] key) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			long count = sjedis.llen(key);
			sjedis.close();
			return count;
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * 
		 * @param byte[] key
		 * @param int index 位置
		 * @param byte[] value 值
		 * @return 状态码
		 * */
		public String lset(byte[] key, int index, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.lset(key, index, value);
			jedis.close();
			return status;
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * 
		 * @param key
		 * @param int index 位置
		 * @param String
		 *            value 值
		 * @return 状态码
		 * */
		public String lset(String key, int index, String value) {
			return lset(SafeEncoder.encode(key), index,
					SafeEncoder.encode(value));
		}

		/**
		 * 在value的相对位置插入记录
		 * 
		 * @param key
		 * @param LIST_POSITION
		 *            前面插入或后面插入
		 * @param String
		 *            pivot 相对位置的内容
		 * @param String
		 *            value 插入的内容
		 * @return 记录总数
		 * */
		public long linsert(String key, LIST_POSITION where, String pivot,
				String value) {
			return linsert(SafeEncoder.encode(key), where,
					SafeEncoder.encode(pivot), SafeEncoder.encode(value));
		}

		/**
		 * 在指定位置插入记录
		 * 
		 * @param String
		 *            key
		 * @param LIST_POSITION
		 *            前面插入或后面插入
		 * @param byte[] pivot 相对位置的内容
		 * @param byte[] value 插入的内容
		 * @return 记录总数
		 * */
		public long linsert(byte[] key, LIST_POSITION where, byte[] pivot,
				byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.linsert(key, where, pivot, value);
			jedis.close();
			return count;
		}

		/**
		 * 获取List中指定位置的值
		 * 
		 * @param String
		 *            key
		 * @param int index 位置
		 * @return 值
		 * **/
		public String lindex(String key, int index) {
			return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
		}

		/**
		 * 获取List中指定位置的值
		 * 
		 * @param byte[] key
		 * @param int index 位置
		 * @return 值
		 * **/
		public byte[] lindex(byte[] key, int index) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			byte[] value = sjedis.lindex(key, index);
			sjedis.close();
			return value;
		}

		/**
		 * 将List中的第一条记录移出List
		 * 
		 * @param String
		 *            key
		 * @return 移出的记录
		 * */
		public String lpop(String key) {
			return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
		}

		/**
		 * 将List中的第一条记录移出List
		 * 
		 * @param byte[] key
		 * @return 移出的记录
		 * */
		public byte[] lpop(byte[] key) {
			Jedis jedis = getJedis();
			byte[] value = jedis.lpop(key);
			jedis.close();
			return value;
		}

		/**
		 * 将List中最后第一条记录移出List
		 * 
		 * @param byte[] key
		 * @return 移出的记录
		 * */
		public String rpop(String key) {
			Jedis jedis = getJedis();
			String value = jedis.rpop(key);
			jedis.close();
			return value;
		}

		/**
		 * 向List尾部追加记录
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 记录总数
		 * */
		public long lpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 向List头部追加记录
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 记录总数
		 * */
		public long rpush(String key, String value) {
			Jedis jedis = getJedis();
			long count = jedis.rpush(key, value);
			jedis.close();
			return count;
		}

		/**
		 * 向List头部追加记录
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 记录总数
		 * */
		public long rpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.rpush(key, value);
			jedis.close();
			return count;
		}

		/**
		 * 向List中追加记录
		 * 
		 * @param byte[] key
		 * @param byte[] value
		 * @return 记录总数
		 * */
		public long lpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.lpush(key, value);
			jedis.close();
			return count;
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * 
		 * @param String
		 *            key
		 * @param long start
		 * @param long end
		 * @return List
		 * */
		public List<String> lrange(String key, long start, long end) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			List<String> list = sjedis.lrange(key, start, end);
			sjedis.close();
			return list;
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * 
		 * @param byte[] key
		 * @param int start
		 * @param int end 如果为负数，则尾部开始计算
		 * @return List
		 * */
		public List<byte[]> lrange(byte[] key, int start, int end) {
			// ShardedJedis sjedis = getShardedJedis();
			Jedis sjedis = getJedis();
			List<byte[]> list = sjedis.lrange(key, start, end);
			sjedis.close();
			return list;
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * 
		 * @param byte[] key
		 * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param byte[] value 要匹配的值
		 * @return 删除后的List中的记录数
		 * */
		public long lrem(byte[] key, int c, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.lrem(key, c, value);
			jedis.close();
			return count;
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * 
		 * @param String
		 *            key
		 * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param String
		 *            value 要匹配的值
		 * @return 删除后的List中的记录数
		 * */
		public long lrem(String key, int c, String value) {
			return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
		}

		/**
		 * 算是删除吧，只保留start与end之间的记录
		 * 
		 * @param byte[] key
		 * @param int start 记录的开始位置(0表示第一条记录)
		 * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 * */
		public String ltrim(byte[] key, int start, int end) {
			Jedis jedis = getJedis();
			String str = jedis.ltrim(key, start, end);
			jedis.close();
			return str;
		}

		/**
		 * 算是删除吧，只保留start与end之间的记录
		 * 
		 * @param String
		 *            key
		 * @param int start 记录的开始位置(0表示第一条记录)
		 * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 * */
		public String ltrim(String key, int start, int end) {
			return ltrim(SafeEncoder.encode(key), start, end);
		}
	}



}
