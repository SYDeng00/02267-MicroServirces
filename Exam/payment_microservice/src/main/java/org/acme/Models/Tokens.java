package org.acme.Models;

import java.util.UUID;

import org.eclipse.persistence.internal.oxm.schema.model.List;

import io.cucumber.java.it.Data;

public class Tokens {
    private Data expireData;
    private UUID tokenID;
    private UUID customerID;
}
