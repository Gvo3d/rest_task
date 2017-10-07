//package org.yakimovdenis.test.database;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.yakimovdenis.test.AbstractDatabaseTest;
//import org.yakimovdenis.resttask.dao.ContactDao;
//import org.yakimovdenis.resttask.models.Contact;
//import org.yakimovdenis.resttask.service.ContactService;
//import org.yakimovdenis.resttask.service.ContactServiceImpl;
//
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.regex.PatternSyntaxException;
//
//@Transactional
//public class RepositoryTests extends AbstractDatabaseTest {
//    private final static String wrongRegex = "*";
//    private final static String rightRegex = ".*";
//
//    @Autowired
//    private ContactService contactService;
//
//    @Test(expected = PatternSyntaxException.class)
//    public void getListWithError() {
//        System.out.println(SEPARATOR);
//        contactService.getContactList(wrongRegex);
//    }
//
//    @Test
//    public void getList() {
//        System.out.println(SEPARATOR);
//        List<Contact> contacts = contactService.getContactList(rightRegex);
//        Long count = contactService.count();
//        Assert.assertEquals(Math.toIntExact(count),contacts.size());
//    }
//
//    @Test
//    public void getCustomList() {
//        ContactService service = new ContactServiceImpl(10000, 50);
//        ContactDao dao = new CustomContactDao();
//        try {
//            Field field = service.getClass().getDeclaredField("contactDao");
//            field.setAccessible(true);
//            field.set(service,dao);
//            field.setAccessible(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(SEPARATOR);
//        List<Contact> contacts = service.getContactList("[h].*");
//        List<Contact> contacts2 = service.getContactList(".*[e].*");
//        List<Contact> contacts3 = service.getContactList(".*[z].*");
//        Assert.assertEquals(2,contacts.size());
//        Assert.assertEquals(2,contacts2.size());
//        Assert.assertEquals(0,contacts3.size());
//    }
//}