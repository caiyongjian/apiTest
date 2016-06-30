package com.qihoo.apitest;

import android.text.TextUtils;
import android.util.Log;

/**
 * Simple assert helper to implement assert.
 * if check failed just print message with android.util.Log.
 */
public class Assert {
    private static boolean sEnable = false;

    /**
     * It should set to true in debug mode. Assert.setEnable(BuildConfig.DEBUG).
     *
     * @param enable enable the assert.
     * */
    public static void setEnable(boolean enable) {
        sEnable = enable;
    }

    /**
     * =============================================================================================
     *
     * assertTrue
     *
     * =============================================================================================
     * */
    public static void assertTrue(boolean result) {
        assertTrue(result, null);
    }

    public static void assertTrue(boolean result, String errMsg) {
        if (!shouldCheck()) return;

        if (!result) {
            failed(errMsg);
        }
    }

    /**
     * =============================================================================================
     *
     * assert Flase
     *
     * =============================================================================================
     * */
    public static void assertFalse(boolean result) {
        assertFalse(result, null);
    }

    public static void assertFalse(boolean result, String errMsg) {
        if (!shouldCheck()) return;

        if (result) {
            failed(errMsg);
        }
    }

    /**
     * =============================================================================================
     *
     * assert Equals
     *
     * =============================================================================================
     * */
    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(Object expected, Object actual, String errMsg) {
        if (!shouldCheck()) return;

        if (expected == null && actual == null) {
            return;
        }

        if (expected != null && isEquals(expected, actual)) {
            return;
        }

        failed(errMsg);
    }

    /**
     * =============================================================================================
     *
     * private members.
     *
     * =============================================================================================
     * */
    private static final String ASSERT_FAILED_PREFIX = "DebugAssertError";

    private static String genErrorMessage(String errMsg) {
        String errorStr = TextUtils.isEmpty(errMsg) ?
                ASSERT_FAILED_PREFIX :
                String.format("%s:%s", ASSERT_FAILED_PREFIX, errMsg);
        return errorStr;
    }

    private static void failed(String errMsg) {
        AssertionError assertionError = new AssertionError(genErrorMessage(errMsg));

        Log.println(Log.ASSERT, ASSERT_FAILED_PREFIX,
                assertionError.getMessage() + '\n' + Log.getStackTraceString(assertionError));
    }

    private static boolean shouldCheck() {
        return sEnable;
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }
}
