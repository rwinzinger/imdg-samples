package winzinger.samples.imdg.s04_mapreduce;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;

import java.util.*;

/**
 * Created by rwinzing on 02.12.15.
 */
public class MapReduce {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        JobTracker jobTracker = hazelcastInstance.getJobTracker("default");

        IMap<String, String> map = hazelcastInstance.getMap("pages");
        KeyValueSource<String, String> source = KeyValueSource.fromMap(map);

        Job<String, String> job = jobTracker.newJob(source);

        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new SplitLineToWordsMapper())
                .combiner(new SimilarWordsCombinerFactory())
                .reducer(new CountUniqueWordsReducerFactory())
                .submit();

        Map<String, Long> results = null;
        try {
            results = future.get();
        } catch (Exception e) {
            throw new RuntimeException("failed to count words", e);
        }

        Set<String> keys = results.keySet();
        for (String key: keys) {
            System.out.println(key+" -> " + results.get(key));
        }
        /*
        List<Map.Entry> sortedResult = new ArrayList<>(results.entrySet());
        sortedResult.sort((entry1, entry2) -> ((Long)entry1.getValue()).compareTo((Long) entry2.getValue()));

        System.out.println("sortedResult.size() = " + sortedResult.size());
        for (Map.Entry<String, Long> entry: sortedResult) {
            System.out.println(entry);
        }
        */


    }

    /**
     * Step: "Map"
     *
     *
     */
    private static class SplitLineToWordsMapper implements Mapper<String, String, String, Long> {
        @Override
        public void map(String key, String line, Context<String, Long> context) {
            System.out.println("key = " + key);
            if (line.trim().length() == 0) {
                return;
            }
            String[] words = line.split(" ");
            for (String word: words) {
                System.out.println("emitting: "+word);
                context.emit(word, Long.valueOf(1));
            }
        }
    }

    /**
     * Step: --
     */
    private static class SimilarWordsCombinerFactory implements CombinerFactory<String, Long, Long> {
        @Override
        public Combiner<Long, Long> newCombiner(String word) {
            System.out.println("returning new Combiner");
            return new SimilarWordsCombiner(word);
        }

        /**
         *
         */
        private static class SimilarWordsCombiner extends Combiner<Long, Long> {
            private long cnt = 0;
            private String word;

            public SimilarWordsCombiner(String word) {
                this.word = word;
            }

            @Override
            public void combine(Long value) {
                System.out.println("combine("+word+"): "+(cnt+value));
                cnt += value;
            }

            @Override
            public Long finalizeChunk() {
                System.out.println("cnt = " + cnt);
                return cnt;
            }

            @Override
            public void reset() {
                System.out.println("reset");
                cnt = 0;
            }
        }
    }

    /**
     * Step: "Reduce"
     */
    private static class CountUniqueWordsReducerFactory implements ReducerFactory<String, Long, Long> {
        @Override
        public Reducer<Long, Long> newReducer(String word) {
            System.out.println("word = " + word);
            return new CountUniqueWordsReducer(word);
        }

        private static class CountUniqueWordsReducer extends Reducer<Long, Long> {
            private final String word;
            private volatile long cnt = 0;

            public CountUniqueWordsReducer(String word) {
                System.out.println("reducer for " + word);
                this.word = word;
            }

            @Override
            public void reduce(Long cnt) {
                System.out.println("reduce("+word+"): "+cnt);
                this.cnt = cnt;
            }

            @Override
            public Long finalizeReduce() {
                System.out.println("reduce(final, "+word+"): "+cnt);
                return cnt;
            }
        }
    }
}
