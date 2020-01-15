package test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by caiping on 2020/1/14.
 */
public class TestJedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("101.132.126.62",6379);
        jedis.auth("783328905");
        //jedis.zadd("myset",6,"上海教育");
        Set<String> myset = jedis.zrange("myset", 0L, -1L);
//        Set<Tuple> myzet = jedis.zrangeByScoreWithScores("myzet", 0L, -1L);
//        List<String> list = new ArrayList<>(myset);
//        Collections.reverse(list);
        System.out.print( jedis.zscore("myset","上海教育"));

//
        myset.forEach(System.out::println);
    }
}
