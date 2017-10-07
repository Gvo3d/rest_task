package org.yakimovdenis.tests.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.yakimovdenis.resttask.dao.ContactDao;
import org.yakimovdenis.resttask.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class CustomContactDao implements ContactDao {
    @Override
    public Iterable<Contact> findAll(Sort sort) {
        return null;
    }
    @Override
    public Page<Contact> findAll(Pageable pageable) {
        return new PageImpl<Contact>((List<Contact>) iterables());
    }
    @Override
    public <S extends Contact> S save(S s) {
        return null;
    }
    @Override
    public <S extends Contact> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }
    @Override
    public Contact findOne(Long aLong) {
        return null;
    }
    @Override
    public boolean exists(Long aLong) {
        return false;
    }
    @Override
    public Iterable<Contact> findAll() {
        return iterables();
    }
    @Override
    public Iterable<Contact> findAll(Iterable<Long> iterable) {
        return null;
    }
    @Override
    public long count() {
        return 3;
    }
    @Override
    public void delete(Long aLong) {
    }
    @Override
    public void delete(Contact contact) {
    }
    @Override
    public void delete(Iterable<? extends Contact> iterable) {
    }
    @Override
    public void deleteAll() {
    }
    public Iterable<Contact> iterables() {
        Contact contact = new Contact(1L,"hello");
        Contact contact2 = new Contact(2L,"hey");
        Contact contact3 = new Contact(3L,"yo");
        List<Contact> result = new ArrayList<>();
        result.add(contact);
        result.add(contact2);
        result.add(contact3);
        return result;
    }
}
