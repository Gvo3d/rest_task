package org.yakimovdenis.resttask.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.yakimovdenis.resttask.models.Contact;
import org.yakimovdenis.resttask.service.ContactService;

import java.util.List;

@EnableWebMvc
@RestController
public class ContactController {
    private final static Logger LOGGER = Logger.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, value = "/hello/contacts", params = {"nameFilter"})
    public ResponseEntity<String> getContacts(@RequestParam("nameFilter") String regex){
        HttpStatus status;
        String list;
        try {
             list = contactService.getContactList(regex);
        } catch (Exception e) {
            LOGGER.warn("BAD REQUEST: "+e);
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if (list.isEmpty()){
            status = HttpStatus.NO_CONTENT;
        } else status = HttpStatus.OK;
        ResponseEntity<String> response = new ResponseEntity<>(list,status);
        return response;
    }
}
