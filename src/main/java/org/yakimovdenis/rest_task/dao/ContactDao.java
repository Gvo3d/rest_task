package org.yakimovdenis.rest_task.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.rest_task.models.Contact;

@Repository
public interface ContactDao extends CrudRepository<Contact, Long> {
}
