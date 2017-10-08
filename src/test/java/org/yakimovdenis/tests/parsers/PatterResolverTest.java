package org.yakimovdenis.tests.parsers;

import org.junit.Assert;
import org.junit.Test;
import org.yakimovdenis.resttask.support.PatternResolver;

public class PatterResolverTest {
    private static final String SEPARATOR = "****************************************";

    @Test
    public void getResultString(){
        String preRegex = "^.*[aei].*$";
        String awaits ="^[^aei]*$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }

    @Test
    public void getResultString2(){
        String preRegex = "^[aei].*$";
        String awaits ="^[^aei].*$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }

    @Test
    public void getResultString3(){
        String preRegex = "^ei.*$";
        String awaits ="^[^ei].*$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }

    @Test
    public void getResultString4(){
        String preRegex = "^.*ei$";
        String awaits ="^.*[^ei]$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }

    @Test
    public void getResultString5(){
        String preRegex = "^.*[^ei]$";
        String awaits ="^.*[ei]$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }

    @Test
    public void getResultString6(){
        String preRegex = "^.*[^aei].*$";
        String awaits ="^.*[aei].*$";
        String resultRegex = PatternResolver.resolvePattern(preRegex);
        System.out.println(SEPARATOR);
        System.out.println("pre:    "+preRegex);
        System.out.println("awaits: "+awaits);
        System.out.println("result: "+resultRegex);
        Assert.assertEquals(awaits, resultRegex);
    }
}
