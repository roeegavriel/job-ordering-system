package com.roee.joborderingsystem.commands.indexjobs;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexJobCommandRequestFiltersTest {

    @Test
    @DisplayName("validate constructor with 2 params set jobIdCourser to 0")
    void validateConstructorWith2Params() {
        Long costumerId = Instancio.create(Long.class);
        Integer limit = Instancio.create(Integer.class);

        IndexJobCommandRequestFilters indexJobCommandRequestFilters = new IndexJobCommandRequestFilters(costumerId, limit);

        assertAll(
            () -> assertEquals(costumerId, indexJobCommandRequestFilters.costumerId()),
            () -> assertEquals(limit, indexJobCommandRequestFilters.limit()),
            () -> assertEquals(0L, indexJobCommandRequestFilters.jobIdCourser())
        );
    }
}
