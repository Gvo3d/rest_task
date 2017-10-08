package org.yakimovdenis.tests.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yakimovdenis.resttask.dao.ContactDao;
import org.yakimovdenis.resttask.models.Contact;
import org.yakimovdenis.resttask.service.ContactService;
import org.yakimovdenis.resttask.service.ContactServiceImpl;
import org.yakimovdenis.tests.AbstractDatabaseTest;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.PatternSyntaxException;

@Transactional
public class RepositoryTests extends AbstractDatabaseTest {

    @Autowired
    private ContactService contactService;


    @Test
    public void getList() {
        System.out.println(SEPARATOR);
        Collection<Contact> contacts = contactService.getContactList("^.*[^.*].*$");
        Long count = contactService.count();
        Assert.assertEquals(Math.toIntExact(count),contacts.size());
    }

    @Test
    public void getCustomList() {
        ContactService service = new ContactServiceImpl(10000, 50);
        ContactDao dao = new CustomContactDao();
        try {
            Field field = service.getClass().getDeclaredField("contactDao");
            field.setAccessible(true);
            field.set(service,dao);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(SEPARATOR);
        Collection<Contact> contacts = service.getContactList("^[^h].*$");
        Collection<Contact> contacts2 = service.getContactList("^.*[^e].*$");
        Collection<Contact> contacts3 = service.getContactList("^.*[^z].*$");
        Assert.assertEquals(2,contacts.size());
        Assert.assertEquals(2,contacts2.size());
        Assert.assertEquals(0,contacts3.size());
    }
}