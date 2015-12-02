package winzinger.samples.imdg.s03_executor;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;

import java.util.Map;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Producer {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        Map<String, String> capitals = hazelcastInstance.getMap("capitals");
        IAtomicLong distIndex = hazelcastInstance.getAtomicLong("index");

        long idx = distIndex.incrementAndGet();
        System.out.println("I'm node #" + idx);

        switch ((int)idx) {
            case 1:
                System.out.println("setting Berlin, Paris");
                capitals.put("Germany", "Berlin");
                capitals.put("France", "Paris");
                break;
            case 2:
                System.out.println("setting Athens");
                capitals.put("Greece", "Athens");
                break;
            case 3:
                System.out.println("setting Rome");
                capitals.put("Italy", "Rome");
                break;
        }


    }
}
