package com.greg.lab8.client.util;

import com.greg.lab8.common.util.data.Organization;

public class OrganisationHolder {
    private  static Organization organization = null;

    private OrganisationHolder(){

    }

    public static Organization getOrganization() {
        return organization;
    }

    public static void setOrganization(Organization organization) {
        OrganisationHolder.organization = organization;
    }
}
