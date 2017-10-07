package org.yakimovdenis.resttask.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.resttask.models.Contact;

@Repository
public interface ContactDao extends PagingAndSortingRepository<Contact, Long> {
}
