package org.yakimovdenis.resttask.service;

import org.yakimovdenis.resttask.models.Contact;

import java.util.Collection;
import java.util.List;

public interface ContactService {
    String getContactList(String regex);
    Long count();
}
