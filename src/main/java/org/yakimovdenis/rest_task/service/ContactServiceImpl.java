package org.yakimovdenis.rest_task.service;

import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private long dataCount;
    private int charsCount;
    private int stepCount;

    @Autowired
    private ContactDao contactDao;

    public ContactServiceImpl(long dataCount, int charsCount, int stepCount) {
        this.dataCount = dataCount;
        this.charsCount = charsCount;
        this.stepCount = stepCount;
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
        LOGGER.info("Service started in "+checker.doCheck()+" milliseconds.");
    }

    public List<Contact> getContactList(String regex) {
        TimeChecker checker = new TimeChecker();
        long count = contactDao.count();
        List<Contact> contacts;
        int repeater = Math.max(Math.toIntExact(count/stepCount),1);
        if (regex.equals(".*")){
            contacts = new ArrayList<>(Math.toIntExact(count));
            contactDao.findAll().forEach(x->contacts.add(x));
            LOGGER.info(new StringBuilder("Regex: ").append(regex).append(" returns list of ").append(contacts.size()).append(" elements. With processing time: ").append(checker.doCheck()).append(" ms.").toString());
            return contacts;
        } else {
            contacts = new ArrayList<>(Math.toIntExact(count/2));
        }
        for (int i=0; i<repeater; i++){
            Pageable pageable = new PageRequest(i,stepCount);
            List<Contact> tempList = contactDao.findAll(pageable).getContent();
            contacts.addAll(new DataCollector(tempList, regex,tempList.size()/2).getResult());
        }
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
