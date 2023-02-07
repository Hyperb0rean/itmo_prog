package com.greg.lab8.server.util;

import com.greg.lab8.common.util.data.Organization;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;

public abstract class CollectionManager {

    protected   LinkedList<Organization> organizations = new LinkedList<Organization>();
    protected   Date initDate;
    protected   Date modDate;

    public LinkedList<Organization> getOrganizations() {
        return organizations;
    }
    public Date getModDate() {
        return modDate;
    }
    public Date getInitDate() {
        return initDate;
    }
    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

}
