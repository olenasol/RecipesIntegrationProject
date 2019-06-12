package app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Configuration
@ComponentScan
@EnableCaching
@SpringBootApplication
public class Application {

    private static final String QUEUE_NAME = "recipe_list";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            receiveMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessages() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            writeRecepesToDb(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
//    @Bean(destroyMethod="shutdown")
//    RedissonClient redisson() throws IOException {
//        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress("127.0.0.1:6379");
//        return Redisson.create(config);
//    }
//    @Bean
//    CacheManager cacheManager(RedissonClient redissonClient) {
//        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
//        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
//        config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
//        return new RedissonSpringCacheManager(redissonClient, config);
//    }

    private static void writeRecepesToDb(String message) {
        try {
            JSONArray jsonRecipes = new JSONArray(message);
            List<Recipe> list = new ArrayList<>();
            for (int i = 0; i < jsonRecipes.length(); i++) {
                JSONObject obj = jsonRecipes.getJSONObject(i);
                list.add(Recipe.decodeFromJSON(obj));
                RecipeDAL dal = RecipeDAL.newInstance();
                dal.insertRecipes(list);
            }
            System.out.println("Written to db ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}