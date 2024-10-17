package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.modelGson.JsonExampleGson;
import guru.qa.modelJackson.Passport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FilesParsingTest extends TestBase {

    private ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private static final Gson gson = new Gson();
    private static final JsonFactory jackson = JsonFactory.builder()
            .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS)
            .build();
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void pdfFileParsingTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
//       $("#table-files_wrapper").scrollTo();
        File downloaded =
                $("[href*=\"-guide-5.11.2.pdf\"]").download();
//       try(InputStream is = new FileInputStream(downloaded)) {
        PDF pdf = new PDF(downloaded);
//       }
        Assertions.assertEquals(pdf.author, "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");

        System.out.println();
    }

    @Test
    void xlsFileParsingTest() throws Exception {
        open("https://www.cmu.edu/blackboard/files/");
        $("[href=\"evaluate/\"]").click();
        File downloaded = $("[href=\"tests-example.xls\"]").download();
        XLS xls = new XLS(downloaded);
        String actualValue = xls.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();

        Assertions.assertTrue(actualValue.contains("Rank the following in their order of operation."));
//        System.out.println("");
    }

    @Test
    void csvFileParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("example.csv");
             CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> data = csvReader.readAll();
            Assertions.assertEquals(3, data.size());
            Assertions.assertArrayEquals(
                    new String[]{"rest", "ru.wikipedia.org"},
                    data.get(0)
            );
            Assertions.assertArrayEquals(
                    new String[]{"soap", "ru.wikipedia.org"},
                    data.get(1)
            );
            Assertions.assertArrayEquals(
                    new String[]{"api", "ru.wikipedia.org"},
                    data.get(2)
            );
        }
    }

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("zipArchive.zip"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                System.out.println(zipEntry.getName());
                if (zipEntry.getName().contains("pdf")) {
                    System.out.println("This is PDF file!");
                } else if (zipEntry.getName().contains("csv")) {
                    System.out.println("This is CSV file!");
                } else if (zipEntry.getName().contains("xls")) {
                    System.out.println("This is XLS or XLSX file!");
                }
            }
        }
    }

    @Test
    void jsonFileParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("jsonExampleGson.json");
             Reader reader = new InputStreamReader(is)) {
            JsonObject actual = gson.fromJson(reader, JsonObject.class);

            Assertions.assertTrue(actual.get("active").getAsBoolean());
            Assertions.assertEquals(2016, actual.get("formed").getAsInt());
            Assertions.assertEquals("Metro City", actual.get("homeTown").getAsString());

            JsonObject inner = actual.get("members").getAsJsonObject();
            Assertions.assertEquals(29, inner.get("age").getAsInt());
            Assertions.assertEquals("Molecule Man", inner.get("name").getAsString());

        }
    }

    @Test
    void jsonFileParsingImprovedGsonTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("jsonExampleGson.json");
             Reader reader = new InputStreamReader(is)) {
            JsonExampleGson actual = gson.fromJson(reader, JsonExampleGson.class);
            System.out.println(actual);

            Assertions.assertEquals("Super hero squad", actual.getSquadName());
            Assertions.assertEquals("Metro City", actual.getHomeTown());
            Assertions.assertEquals(2016, actual.getFormed());
            Assertions.assertEquals("Super tower", actual.getSecretBase());
            Assertions.assertTrue(actual.getActive());

            Assertions.assertEquals(29, actual.getMembers().getAge());
            Assertions.assertEquals("Molecule Man", actual.getMembers().getName());
            Assertions.assertEquals("Dan Jukes", actual.getMembers().getSecretIdentity());
            Assertions.assertArrayEquals(new String[]{"Radiation resistance", "Turning tiny", "Radiation blast"},
                    actual.getMembers().getPowers());

        }
    }

    @Test
    void jsonFileParsingImprovedJacksonTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream("jsonExampleJackson.json");
             Reader reader = new InputStreamReader(is)) {
//            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
                    actual.getChildren().getChild1().getSex());
            Assertions.assertEquals("Sukhozad Yunistianovich Napluisvechko",
                    actual.getChildren().getChild1().getFullName());
            Assertions.assertEquals("01.02.03",
                    actual.getChildren().getChild1().getDateOfBirth());
            Assertions.assertEquals((short) 101,
                    actual.getChildren().getChild1().getPersonalCode());
            Assertions.assertEquals(false,
                    actual.getChildren().getChild2().getSex());
            Assertions.assertEquals("Zadnikov Yunistianovich Napluisvechko",
                    actual.getChildren().getChild2().getFullName());
            Assertions.assertEquals("01.01.01",
                    actual.getChildren().getChild2().getDateOfBirth());
            Assertions.assertEquals((short) 100,
                    actual.getChildren().getChild2().getPersonalCode());
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

    @Test
    void zipFileReaderParsingTest() throws Exception {
        try (ZipInputStream zin = new ZipInputStream(cl.getResourceAsStream("zipArchive.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName();
                System.out.printf("File name: %s \n", name);

                if (name.contains("pdf")) {
                    PDF pdf = new PDF(zin);
                    System.out.println("This is PDF file!");
                    Assertions.assertEquals(1, pdf.numberOfPages);
                } else if (name.contains("csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zin));
                    System.out.println("This is CSV file!");
                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(79, data.size());
                    Assertions.assertArrayEquals(
                            new String[]{"11;Комментарий;\"Данил Билякович\";\"Видно и слышно отлично!)\";1"},
                            data.get(2)
                    );
                } else if (name.contains("xls")) {
                    XLS xls = new XLS(zin);
                    System.out.println("This is XLS or XLSX file!");
                    String actualValue = xls.excel.getSheetAt(0).getRow(2).getCell(3).getStringCellValue();
                    Assertions.assertTrue(actualValue.contains("The Who; British Invasion; AM radio"));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

