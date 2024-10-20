package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileZipTest {
    private final ClassLoader CL = FilesParsingTest.class.getClassLoader();
    private final String NAME_OF_ARCHIVE = "zipArchive.zip";
    private ZipEntry entry;
    private String name;

    @Test
    void zipFilePDFReaderParsingTest() throws Exception {
        PDF pdf = null;
        try (ZipInputStream zin = new ZipInputStream(CL.getResourceAsStream(NAME_OF_ARCHIVE))) {
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                System.out.printf("File name: %s \n", name);
                if (name.contains(".pdf")) {
                    pdf = new PDF(zin);
                    System.out.println("This is PDF file!");
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Assertions.assertEquals(1, pdf.numberOfPages);
        Assertions.assertEquals("Philip Hutchison", pdf.author);
        Assertions.assertEquals("sample", pdf.title);
    }

    @Test
    void zipFileCSVReaderParsingTest() throws Exception {
        List<String[]> data = new ArrayList<>();
        try (ZipInputStream zin = new ZipInputStream(CL.getResourceAsStream(NAME_OF_ARCHIVE))) {
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                System.out.printf("File name: %s \n", name);
                if (name.contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zin));
                    data = csvReader.readAll();
                    System.out.println("This is CSV file!");
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Assertions.assertEquals(79, data.size());
        Assertions.assertArrayEquals(
                new String[]{"11;Комментарий;\"Данил Билякович\";\"Видно и слышно отлично!)\";1"},
                data.get(2)
        );
    }

    @Test
    void zipFileXLSXReaderParsingTest() throws Exception {
        String actualValue = "";
        try (ZipInputStream zin = new ZipInputStream(CL.getResourceAsStream(NAME_OF_ARCHIVE))) {
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                System.out.printf("File name: %s \n", name);
                if (name.contains(".xls")) {
                    XLS xls = new XLS(zin);
                    actualValue = xls.excel.getSheetAt(0).getRow(2).getCell(3).getStringCellValue();
                    System.out.println("This is XLS or XLSX file!");
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Assertions.assertTrue(actualValue.contains("The Who; British Invasion; AM radio"));
    }
}