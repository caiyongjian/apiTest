package com.qihoo.apitest.iterator;

import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by caiyongjian on 16-7-1.
 */
public class IteratorTest {
    private static final String TAG = "IteratorTest";

    public interface TestDelegate {
        void onPreExec();
        void onPostExec();
    }



    private static class Timer {
        long mStart;
        long mEnd;

        public Timer() {
            mStart = 0;
            mEnd = 0;
        }

        public void start() {
            mStart = System.currentTimeMillis();
        }

        public void stop() {
            mEnd = System.currentTimeMillis();
        }

        public long cost() {
            if (mEnd == 0) {
                throw new RuntimeException("not stoped!!!");
            }
            return mEnd - mStart;
        }
    }


    public static void AsyncTest(final TestDelegate delegate, int capacity) {
        new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                Integer cap = (Integer)params[0];
                test(cap);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                delegate.onPreExec();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                delegate.onPostExec();
            }
        }.execute(capacity);
    }

    private static long timeTick = 0;
    private static void checkTime(String msg) {
        if (msg != null) {
            Log.i(TAG, String.format("[%d] cost on %s", System.currentTimeMillis() - timeTick, msg));
        }
        timeTick = System.currentTimeMillis();
    }

    public static void test(int capacity) {
        testMapIterator(capacity);

        testListIterator(capacity);
    }


    private static void testMapIterator(int capacity) {
        checkTime(null);
        Map<Integer, String> testMap = prepareHashMap(capacity);
        checkTime("prepareTestMap");

        doMapIterator(testMap);
        checkTime("doMapIterator");

        doMapGet(testMap);
        checkTime("doMapGet");
        testMap = null;
    }

    private static void testListIterator(int capacity) {
        checkTime(null);
        List<String> testList = prepareArrayList(capacity);
        checkTime("prepareArrayList");

        doListIterator(testList);
        checkTime("doListIterator");

        doListGet(testList);
        checkTime("doListGet");
        testList = null;

        testList = prepareLinkedList(capacity);
        checkTime("prepareLinkedList");

        doListIterator(testList);
        checkTime("doListIterator");

        doListGet(testList);
        checkTime("doListGet");
    }

    private static Map<Integer, String> prepareHashMap(int capacity) {
        HashMap<Integer, String> testMap = new HashMap<>(capacity);
        for (int i = 0; i < capacity; i++) {
            testMap.put(i, String.format("test_%d", i));
        }
        return testMap;
    }

    private static void doMapIterator(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            check(key, value);
        }
    }

    private static void doMapGet(Map<Integer, String> map) {
        for (int key : map.keySet()) {
            String value = map.get(key);
            check(key, value);
        }
    }

    private static List<String> prepareArrayList(int capacity) {
        ArrayList<String> arrayList = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            arrayList.add(String.format("test_%d", i));
        }
        return arrayList;
    }

    private static List<String> prepareLinkedList(int capacity) {
        LinkedList<String> targetList = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            targetList.add(String.format("test_%d", i));
        }
        return targetList;
    }



    private static void doListIterator(List<String> list) {
        for (String it : list) {
            check(0, it);
        }
    }

    private static void doListGet(List<String> list) {
        final int list_size = list.size();
        for (int i = 0; i < list_size; i++) {
            check(i, list.get(i));
        }
    }

    private static void check(int key, String value) {

    }


}
