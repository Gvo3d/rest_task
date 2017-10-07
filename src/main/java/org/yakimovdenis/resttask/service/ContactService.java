package org.yakimovdenis.resttask.service;

import org.yakimovdenis.resttask.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContactList(String regex);
    Long count();
}
