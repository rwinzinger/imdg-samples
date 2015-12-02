package winzinger.samples.imdg.s03_executor;

import com.hazelcast.core.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Executor2 {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        IExecutorService es = hazelcastInstance.getExecutorService("printer");
        es.executeOnAllMembers(new CityPrinter());
    }

    private static class CityPrinter implements Runnable, Serializable, HazelcastInstanceAware {
        private HazelcastInstance hazelcastInstance;

        public void run() {
            Map<String, String> capitals = hazelcastInstance.getMap("capitals");

            Set<String> keys = ((IMap<String, String>)capitals).localKeySet();
            for (String key : keys) {
                System.out.println("(CityPrinter) "+key+" -> "+capitals.get(key));
            }
        }

        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.hazelcastInstance = hazelcastInstance;
        }
    }

}