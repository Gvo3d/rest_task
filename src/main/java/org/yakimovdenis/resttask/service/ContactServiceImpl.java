package org.yakimovdenis.resttask.service;

import com.hazelcast.core.*;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    private final static Logger LOGGER = Logger.getLogger(ContactService.class);
    private long dataCount;
    private int charsCount;
    private HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();

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
        LOGGER.info("Loading data from db to hazelcast.");
        IMap<Long, Contact> contacts = hzInstance.getMap("contacts");
        contactDao.findAll().forEach(x -> contacts.put(x.getId(), x));
        LOGGER.info("Service started in " + checker.doCheck() + " milliseconds.");
    }

    public String getContactList(String regex) {
        TimeChecker checker = new TimeChecker();
        String pattern = PatternResolver.resolvePattern(regex);
        IMap<Long, Contact> contacts = hzInstance.getMap("contacts");
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = Predicates.regex("name",pattern);
        Collection<Contact> result = contacts.values( predicate );

        LOGGER.info(new StringBuilder("Regex: ").append(regex).append(" returns list of ").append(result.size()).append(" elements. With processing time: ").append(checker.doCheck()).append(" ms.").toString());
        return getData(result);
    }

    private String getData(Collection<Contact> data) {
        StringBuilder builder = new StringBuilder(100000);
        builder.append("[\n");
        boolean first = true;
        for (Contact contact : data) {
            if (!first) builder.append(",\n");
            builder.append(
                    "    {\n        \"id\": ")
                    .append(contact.getId())
                    .append(",\n        \"name\": \"").append(contact.getName()).append("\"\n    }");
            first = false;
        }
        builder.append("]");
        return builder.toString();
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
            Contact contact = new Contact(RandomStringUtils.randomAlphanumeric(charsCount));
            contacts.add(contact);
        }
        contactDao.save(contacts);
        LOGGER.info("Data persisted in " + checker.doCheck() + " milliseconds.");
    }
}
