package ua.delsix.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

@DisplayName("DatamuseService tests")
class DatamuseServiceImplTest {
    private DatamuseServiceImpl datamuseService;

    @BeforeEach
    void setUp() {
        datamuseService = new DatamuseServiceImpl();
    }

    @Test
    void getPerfectRhymes() throws IOException {
        List<String> actual = datamuseService.getPerfectRhymes("corn");

        System.out.println(actual);

        assertTrue(actual.size() > 0);
    }

    @Test
    void getNearRhymes()  throws IOException  {
        List<String> actual = datamuseService.getNearRhymes("corn");

        System.out.println(actual);

        assertTrue(actual.size() > 0);
    }
}