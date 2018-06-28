package com.tom.authrest.services;

import java.util.List;

public interface CRUDServiceKeyIsStr<T> {
    List<?> listAll();

    T getById(String id);

    T saveOrUpdate(T domainObject);

    void delete(String id);
}
