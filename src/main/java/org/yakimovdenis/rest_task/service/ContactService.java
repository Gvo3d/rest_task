package org.yakimovdenis.rest_task.service;

import org.yakimovdenis.rest_task.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContactList(String regex);
}
