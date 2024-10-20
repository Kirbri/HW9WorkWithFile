package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.modelJackson.Passport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonTest {

    private final ClassLoader cl = FilesParsingTest.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void jsonFileParsingImprovedJacksonTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream("jsonExampleJackson.json");
             Reader reader = new InputStreamReader(is)) {
            Passport actual = mapper.readValue(reader, Passport.class);
            System.out.println(actual);
            Assertions.assertEquals("Russian Federation",
                    actual.getCountry());
            Assertions.assertEquals("Krasnodar region, Khutor Veselaya Zhizn",
                    actual.getPassportIssued());
            Assertions.assertEquals("07.09.2000",
                    actual.getDateOfIssue());
            Assertions.assertEquals("100-999",
                    actual.getDepartmentCode());
            Assertions.assertEquals((short) 9999,
                    actual.getSeries());
            Assertions.assertEquals(121212,
                    actual.getNumber());
            Assertions.assertEquals("Avtogen Yunistianovich Napluisvechko",
                    actual.getFullName());
            Assertions.assertEquals(true,
                    actual.getSex());
            Assertions.assertEquals("10.10.2025",
                    actual.getDateOfBirth());
            Assertions.assertEquals("Kirov region, Adovo village",
                    actual.getPlaceOfBirth());
            Assertions.assertEquals("Moscow region, Pyankino",
                    actual.getPlaceOfResidence().getRegistered());
            Assertions.assertEquals("",
                    actual.getPlaceOfResidence().getDeregistered());
            Assertions.assertEquals(false,
                    actual.getChild().get(0).getSex());
            Assertions.assertEquals("Sukhozad Yunistianovich Napluisvechko",
                    actual.getChild().get(0).getFullName());
            Assertions.assertEquals("01.02.03",
                    actual.getChild().get(0).getDateOfBirth());
            Assertions.assertEquals((short) 101,
                    actual.getChild().get(0).getPersonalCode());
            Assertions.assertEquals(false,
                    actual.getChild().get(1).getSex());
            Assertions.assertEquals("Zadnikov Yunistianovich Napluisvechko",
                    actual.getChild().get(1).getFullName());
            Assertions.assertEquals("01.01.01",
                    actual.getChild().get(1).getDateOfBirth());
            Assertions.assertEquals((short) 100,
                    actual.getChild().get(1).getPersonalCode());
            Assertions.assertArrayEquals(new String[]{"comply with the Constitution of the Russian Federation and laws",
                            "pay taxes",
                            "to defend the Fatherland and perform military service",
                            "respect the rights and freedoms of others",
                            "preserve cultural and historical monuments",
                            "protect nature",
                            "receive basic general education (9 grades)",
                            "Parents must take care of minor children, and adult children must take care of their disabled parents."},
                    actual.getResponsibilities());
        }
    }
}