package winzinger.samples.imdg.s01_autodiscovery;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Node {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    }
}
