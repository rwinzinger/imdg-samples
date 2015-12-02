package winzinger.samples.imdg.s02_producerconsumer;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Producer {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        Map<String, String> capitals = hazelcastInstance.getMap("capitals");

        capitals.put("Germany", "Berlin");
        capitals.put("France", "Paris");

    }
}
