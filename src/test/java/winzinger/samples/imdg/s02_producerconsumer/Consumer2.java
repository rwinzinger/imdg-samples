package winzinger.samples.imdg.s02_producerconsumer;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Set;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Consumer2 {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        Map<String, String> capitals = hazelcastInstance.getMap("capitals");

        capitals.put("Greece", "Athens");

        Set<String> keys = capitals.keySet();
        for (String key : keys) {
            System.out.println(key+" -> "+capitals.get(key));
        }
    }
}
