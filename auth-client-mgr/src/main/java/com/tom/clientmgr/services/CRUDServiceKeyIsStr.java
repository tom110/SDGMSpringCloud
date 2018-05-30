package com.tom.clientmgr.services;

import javax.print.DocFlavor;
import java.util.List;

public interface CRUDServiceKeyIsStr<T> {
    List<?> listAll();

    T getById(String id);

    T saveOrUpdate(T domainObject);

    void delete(String id);
}
