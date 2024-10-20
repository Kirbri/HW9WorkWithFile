package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.modelGson.JsonExampleGson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FilesParsingTest extends TestBase {

    private final ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private static final Gson gson = new Gson();

    @Test
    void pdfFileParsingTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloaded =
                $("[href*=\"-guide-5.11.2.pdf\"]").download();
        PDF pdf = new PDF(downloaded);

        Assertions.assertEquals(pdf.author, "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Test
    void xlsFileParsingTest() {
        open("https://www.cmu.edu/blackboard/files/");
        $("[href=\"evaluate/\"]").click();
        File downloaded = $("[href=\"tests-example.xls\"]").download();
        XLS xls = new XLS(downloaded);
        String actualValue = xls.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();

        Assertions.assertTrue(actualValue.contains("Rank the following in their order of operation."));
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
            List<String> list = new ArrayList<>();
            while ((zipEntry = zis.getNextEntry()) != null) {
                System.out.println(zipEntry.getName());
                list.add(zipEntry.getName());
                if (zipEntry.getName().contains(".pdf")) {
                    System.out.println("This is PDF file!");
                } else if (zipEntry.getName().contains(".csv")) {
                    System.out.println("This is CSV file!");
                } else if (zipEntry.getName().contains(".xls")) {
                    System.out.println("This is XLS or XLSX file!");
                }
            }
            Assertions.assertTrue(list.contains("ExampleCSV.csv"));
            Assertions.assertTrue(list.contains("ExamplePDF.pdf"));
            Assertions.assertTrue(list.contains("ExampleXLSX.xlsx"));
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
}