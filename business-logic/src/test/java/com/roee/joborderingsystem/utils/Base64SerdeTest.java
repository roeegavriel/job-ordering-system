package com.roee.joborderingsystem.utils;

import com.roee.joborderingsystem.commands.createjob.CreateJobCommandParameters;
import com.roee.joborderingsystem.entities.Customer;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Base64SerdeTest {

    private final Base64Serde base64Serde = new Base64Serde();

    @Test
    @DisplayName("verify serialize and deserialize returning the same Customer")
    void verifySerializeAndDeserializeForCustomer() {
        Customer originalObject = Instancio.create(Customer.class);

        Customer serdeObject = base64Serde.deserialize(base64Serde.serialize(originalObject), Customer.class);

        assertEquals(originalObject, serdeObject);
    }

    @Test
    @DisplayName("verify serialize and deserialize returning the same CreateJobCommandParameters")
    void verifySerializeAndDeserializeForCreateJobCommandParameters() {
        CreateJobCommandParameters originalObject = Instancio.create(CreateJobCommandParameters.class);

        CreateJobCommandParameters serdeObject = base64Serde.deserialize(base64Serde.serialize(originalObject), CreateJobCommandParameters.class);

        assertEquals(originalObject, serdeObject);
    }
}
