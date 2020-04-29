package pds.stardust.scaccount.service;

import pds.stardust.scaccount.entity.CustomerEntity;

public interface ICustomerService {

    CustomerEntity findByUsername(String username);
    CustomerEntity getById(String id);
    CustomerEntity saveCustomer (CustomerEntity customerEntity);
    void initCustomerData();
}