package org.yakimovdenis.rest_task.support;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import org.hibernate.mapping.Collection;
import org.yakimovdenis.rest_task.models.Contact;
import org.yakimovdenis.rest_task.service.ContactService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataCollector {
    private final static Logger LOGGER = Logger.getLogger(DataCollector.class);
    private List<Contact> result;
    private Set<Future<List<Contact>>> set = new HashSet<>();

    public DataCollector(List<Contact> preData, String regex, int preSize) {
        result = new ArrayList<>(preSize);
        Runtime rnt = Runtime.getRuntime();
        int cpus = rnt.availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(cpus);
        int startingPoint=0;
        int diy = preData.size()/cpus;
        for (int i = 0; i < cpus; i++) {
            int enddingPoint = startingPoint+diy<=preData.size()?startingPoint+diy:preData.size();
            DataMatcher matcher = new DataMatcher(preData.subList(startingPoint,enddingPoint), regex);
            Future<List<Contact>> future = threadPool.submit(matcher);
            set.add(future);
            startingPoint = enddingPoint+1;
        }
    }

    public List<Contact> getResult(){
        for (Future<List<Contact>> future : set) {
            try {
                result.addAll(future.get());
            } catch (Exception e) {
                LOGGER.warn(e);
            }
        }
        return result;
    }

    private class DataMatcher implements Callable<List<Contact>> {
        private List<Contact> preData;
        RegexValidator regexValidator;

        DataMatcher(List<Contact> preData, String regex) {
            this.preData = preData;
            regexValidator = new RegexValidator(regex, true);
        }

        @Override
        public List<Contact> call() throws Exception {
            return preData.stream().filter(c -> regexValidator.isValid(c.getName())).collect(Collectors.toList());
        }
    }
}
