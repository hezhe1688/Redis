import com.hezhe.redis.model.User;
import com.hezhe.redis.utils.JedisUtil;
import com.hezhe.redis.utils.RedisUtil1;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 贺哲
 * @2019-06-21 14:43
 */
public class MyTest {

    @Test
    public void test() {
        Jedis jedis = RedisUtil1.getJedis();
        String key = "user:1";
        /**
         * 需求：如果此key存在，则到Redis中进行查找，否则到MySQL数据库中查找
         * 最终都是以对象的形式进行存储
         */
        if (jedis.exists(key)) {
            //如果key存在就到Redis缓存中取（hgetAll取出该key所有的字段）
            Map<String, String> map = jedis.hgetAll(key);
            //然后存储到user对象中
            User user = new User(Integer.parseInt(map.get("id")), map.get("name"));
            System.out.println("从Redis中查询出来的" + user);
        } else {
            User user = new User(1, "hezhe");
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", user.getId() + "");
            map.put("name", user.getName());
            //将从数据库中取出来的对象封装到map中，然后存储到jedis中
            jedis.hmset(key, map);
            System.out.println("MySQL查询出来的" + user);
        }
        RedisUtil1.closeJedis(jedis);
    }

    @Test
    public void test1(){
        JedisUtil jedisUtil = JedisUtil.getInstance();
        Jedis jedis = jedisUtil.getJedis();
        Map<String, String> stringMap = jedis.hgetAll("user:1");
        for (Map.Entry<String, String> stringEntry : stringMap.entrySet()) {
            System.out.println(stringEntry);
        }
        jedis.close();
    }
}
