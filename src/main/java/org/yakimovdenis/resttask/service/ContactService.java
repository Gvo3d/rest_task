package org.yakimovdenis.resttask.service;

import org.yakimovdenis.resttask.models.Contact;

import java.util.Collection;
import java.util.List;

public interface ContactService {
    Collection<Contact> getContactList(String regex);
    Long count();
}
