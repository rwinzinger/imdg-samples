package winzinger.samples.imdg.s04_mapreduce;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Created by rwinzing on 02.12.15.
 */
public class SimpleNode {
    public static void main(String[] args) {
        Hazelcast.newHazelcastInstance();
    }
}
