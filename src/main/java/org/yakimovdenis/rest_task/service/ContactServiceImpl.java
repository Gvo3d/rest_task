package org.yakimovdenis.rest_task.service;

import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yakimovdenis.rest_task.dao.ContactDao;
import org.yakimovdenis.rest_task.models.Contact;
import org.yakimovdenis.rest_task.support.DataCollector;
import org.yakimovdenis.rest_task.support.TimeChecker;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    private final static Logger LOGGER = Logger.getLogger(ContactService.class);
    private List<Contact> data;
    private long dataCount;
    private int charsCount;

    @Autowired
    private ContactDao contactDao;

    public ContactServiceImpl(long dataCount, int charsCount) {
        this.dataCount = dataCount;
        this.charsCount = charsCount;
    }

    public ContactServiceImpl(List<Contact> data) {
        this.data = data;
    }

    public ContactServiceImpl() {
    }

    @PostConstruct
    private void onInit() {
        TimeChecker checker = new TimeChecker();
        long count = contactDao.count();
        if (count < dataCount) {
            fillDatabaseWithGeneratedData(dataCount - count);
        }
        data = new ArrayList<>(Math.toIntExact(dataCount));
        contactDao.findAll().iterator().forEachRemaining(contact -> data.add(contact));
        LOGGER.info("Service started and loaded all data in "+checker.doCheck()+" milliseconds.");
    }

    public List<Contact> getContactList(String regex) {
        TimeChecker checker = new TimeChecker();
        List<Contact> contacts = new DataCollector(data, regex,data.size()/2).getResult();
        LOGGER.info(new StringBuilder("Regex: ").append(regex).append(" returns list of ").append(contacts.size()).append(" elements. With processing time: ").append(checker.doCheck()).append(" ms.").toString());
        return contacts;
    }

    @Override
    public Long count() {
        return contactDao.count();
    }

    private void fillDatabaseWithGeneratedData(long quantity) {
        TimeChecker checker = new TimeChecker();
        LOGGER.info("Creating and persisting data.");
        List<Contact> contacts = new ArrayList<>(Math.toIntExact(quantity));
        for (int i = 0; i < quantity; i++) {
            contacts.add(new Contact(RandomStringUtils.randomAlphanumeric(charsCount)));
        }
        contactDao.save(contacts);
        LOGGER.info("Data persisted in "+checker.doCheck()+" milliseconds.");

    }
}
