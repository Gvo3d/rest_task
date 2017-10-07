package org.yakimovdenis.resttask.support;

public class TimeChecker {
    private long start;

    public TimeChecker() {
        start = System.currentTimeMillis();
    }

    public long doCheck(){
        return System.currentTimeMillis()-start;
    }
}
