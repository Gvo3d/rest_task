package org.yakimovdenis.resttask.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yakimovdenis.resttask.dao.ContactDao;
import org.yakimovdenis.resttask.models.Contact;
import org.yakimovdenis.resttask.support.DataCollector;
import org.yakimovdenis.resttask.support.PatternResolver;
import org.yakimovdenis.resttask.support.TimeChecker;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final static Logger LOGGER = Logger.getLogger(ContactService.class);
    private long dataCount;
    private int charsCount;

    @Autowired
    private ContactDao contactDao;

    public ContactServiceImpl(long dataCount, int charsCount) {
        this.dataCount = dataCount;
        this.charsCount = charsCount;
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
        LOGGER.info("Service started in " + checker.doCheck() + " milliseconds.");
    }

    public List<Contact> getContactList(String regex) {
        TimeChecker checker = new TimeChecker();
        long count = contactDao.count();
        List<Contact> contacts;
        String pattern = PatternResolver.resolvePattern(regex);
        contacts = new ArrayList<>(Math.toIntExact(count / 2));
        List<Contact> tempList = (List<Contact>) contactDao.findAll();
        contacts.addAll(new DataCollector(tempList, pattern, tempList.size() / 2).getResult());

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
        LOGGER.info("Data persisted in " + checker.doCheck() + " milliseconds.");
    }
}
