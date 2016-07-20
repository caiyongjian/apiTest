package com.qihoo.apitest.hashmap;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by caiyongjian on 16-6-23.
 */
public class HashMapTest {
    private static final String TAG = "HashMapTest";
    private static final int map_count = 50;
    private static HashMap<String, String> map = new HashMap<String, String>(map_count);
    static {
        for (int i = 0; i < map_count; i++) {
            String indexStr = String.valueOf(i);
            map.put("key_" + indexStr, "val_" + indexStr);
        }
    }

    private static class ReadTask implements Callable<Long> {
        long taskId = 0;
        public ReadTask(int id) {
            taskId = id;
        }

        @Override
        public Long call() throws Exception {
            long count = 0;
            for (String str : map.values()) {
                Log.i(TAG, String.format("read value:%s", str));
                ++count;
            }
            return count;
        }
    }
    public static void performMultiThreadReadTest(int threadCount) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        ArrayList<ReadTask> tasks = new ArrayList<ReadTask>(map.size());
        for (int i = 0; i < map.size(); i++) {
            tasks.add(new ReadTask(i));
        }

        List<Future<Long>> result = null;
        try {
            result = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<Long> future : result) {
            try {
                Long res = future.get();
                Log.i(TAG, String.format("last result:%d", res.longValue()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
