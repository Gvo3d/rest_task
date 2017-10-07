package org.yakimovdenis.test.parsers;

import org.apache.commons.validator.routines.RegexValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yakimovdenis.resttask.support.TimeChecker;
import org.yakimovdenis.resttask.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParsersTest {
    private static final int CONTACTS_COUNT = 100;
    private static final int CHARS_COUNT = 5;
    private static List<Contact> testContacts;

    @Before
    public void init(){
        testContacts = new ArrayList<>(CONTACTS_COUNT);
        for (int i = 0; i< CONTACTS_COUNT; i++){
            Contact contact = new Contact(rndChar(CHARS_COUNT));
            contact.setId((long) i);
            testContacts.add(contact);
        }
    }

    @Test
    public void printInitList(){
        printList(testContacts);
    }

    @Test
    public void testRegexMacther(){
        RegexValidator regexValidator = new RegexValidator("[^v]*", true);
        List<Contact> contacts = testContacts.parallelStream().filter(c -> regexValidator.isValid(c.getName())).collect(Collectors.toList());
        printList(contacts);
    }

    @Test
    public void testRegexMatcher2(){
        RegexValidator regexValidator = new RegexValidator("^[^A].*", true);
        List<Contact> contacts = testContacts.parallelStream().filter(c -> regexValidator.isValid(c.getName())).collect(Collectors.toList());
        printList(contacts);
    }


    @Test
    public void hundredSimpleMatcherTest(){
        TimeChecker checker = new TimeChecker();
            for (int i=0;i<100;i++){
                standartMatcherTest();
            }
        System.out.println("T: "+checker.doCheck());
    }

    @Test
    public void hundredPatternMatcherTest(){
        TimeChecker checker = new TimeChecker();
        Pattern p1 = Pattern.compile("[^v]*");
        Pattern p2 = Pattern.compile("[^o]*");
        for (int i=0;i<100;i++){
            patternMatcherTest(p1,p2);
        }
        System.out.println("T: "+checker.doCheck());
    }

    @Test
    public void hundredApacheMatcherTest(){
        TimeChecker checker = new TimeChecker();
        RegexValidator regexValidator = new RegexValidator("[^v]*", true);
        RegexValidator regexValidator2 = new RegexValidator("[^o]*", true);
        for (int i=0;i<100;i++){
            apacheMatcherTest(regexValidator,regexValidator2);
        }
        System.out.println("T: "+checker.doCheck());
    }

    private void standartMatcherTest(){
        String data = "jsSD@#f2vEfeww";
        boolean result = data.matches("[^v]*");
        boolean result2 = data.matches("[^o]*");
        Assert.assertFalse(result);
        Assert.assertTrue(result2);
    }

    private void patternMatcherTest(Pattern patternFalse, Pattern patternTrue){
        String data = "jsSD@#f2vEfeww";
        Matcher m1 = patternFalse.matcher(data);
        Matcher m2 = patternTrue.matcher(data);
        boolean result = m1.matches();
        boolean result2 = m2.matches();
        Assert.assertFalse(result);
        Assert.assertTrue(result2);
    }

    private void apacheMatcherTest(RegexValidator falseRegex, RegexValidator trueRegex){
        String data = "jsSD@#f2vEfeww";
        boolean result = falseRegex.isValid(data);
        boolean result2 = trueRegex.isValid(data);
        Assert.assertFalse(result);
        Assert.assertTrue(result2);
    }

    private static void printList(List<Contact> list){
        System.out.println("ORIGINAL LIST SIZE: "+testContacts.size());
        System.out.println("SIZE: "+list.size());
        list.forEach(System.out::println);
    }

    private static String rndChar (int length) {
        char[] data = new char[length];
        for (int i=0; i<length;i++) {
            int rnd = (int) (Math.random() * 52);
            char base = (rnd < 26) ? 'A' : 'a';
            data[i]=(char) (base + rnd % 26);
        }
        return String.valueOf(data);
    }
}
