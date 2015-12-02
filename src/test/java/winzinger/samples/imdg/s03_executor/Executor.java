package winzinger.samples.imdg.s03_executor;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

import java.io.Serializable;

/**
 * Created by rwinzing on 02.12.15.
 */
public class Executor {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        IExecutorService es = hazelcastInstance.getExecutorService("printer");
        es.executeOnAllMembers(new SimpleHello());
    }

    private static class SimpleHello implements Runnable, Serializable {
        public void run() {
            System.out.println("hello");
        }
    }
}