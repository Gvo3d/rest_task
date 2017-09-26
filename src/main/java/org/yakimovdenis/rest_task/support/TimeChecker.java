package org.yakimovdenis.rest_task.support;

public class TimeChecker {
    private long start;

    public TimeChecker() {
        start = System.currentTimeMillis();
    }

    public long doCheck(){
        return System.currentTimeMillis()-start;
    }
}
