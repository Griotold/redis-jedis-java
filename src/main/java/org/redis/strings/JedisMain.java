package org.redis.strings;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class JedisMain {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {

                jedis.set("users:300:email", "kim@fastcampus.co.kr");
                jedis.set("users:300:name", "kim 00");
                jedis.set("users:300:age", "100");

                String userEmail = jedis.get("users:300:email");
                System.out.println("userEmail = " + userEmail);

                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

//                long counter = jedis.incr("counter");
//                System.out.println("counter = " + counter);

//                long counter = jedis.incrBy("counter", 10L);
//                System.out.println("counter = " + counter);

//                long counter = jedis.decr("counter");
//                System.out.println("counter = " + counter);

                long counter = jedis.decrBy("counter", 10L);
                System.out.println("counter = " + counter);

                Pipeline pipelined = jedis.pipelined();
                pipelined.set("users:400:email", "greg@fastcampus.co.kr");
                pipelined.set("users:400:name", "greg");
                pipelined.set("users:400:age", "15");
                List<Object> objects = pipelined.syncAndReturnAll();
                objects.forEach(System.out::println);
            }
        }
    }
}
