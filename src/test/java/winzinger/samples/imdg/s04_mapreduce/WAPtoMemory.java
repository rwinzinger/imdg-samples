package winzinger.samples.imdg.s04_mapreduce;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by rwinzing on 02.12.15.
 */
public class WAPtoMemory {
    public static void main(String[] args) throws Exception {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        Map<String, String> map = hazelcastInstance.getMap("pages");
        AtomicInteger ai = new AtomicInteger(0);

        Instant start = Instant.now();
        Stream<String> lines = Files.lines(Paths.get("src/test/resources/war-and-peace_small.txt"));
        lines.sequential().forEach(line -> {
            map.put("line-"+ai.incrementAndGet(), line);
        });
        Duration duration = Duration.between(start, Instant.now());

        System.out.println("read and stored " + ai + " lines of War and Peace in "+duration.toNanos()/1000000+" msecs");
        System.out.println("now counting words ...");

        // that's not exact but close enough
        long wordcount = 0;
        Set<String> keys = map.keySet();
        start = Instant.now();
        for (String key: keys) {
            String line = map.get(key);
            if (line.trim().length() == 0) {
                continue;
            }
            wordcount += line.split(" ").length;
        }
        duration = Duration.between(start, Instant.now());

        System.out.println("(centralized) counted " + wordcount + " words on one node in "+duration.toNanos()/1000000+" msecs");
    }
}
