package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFilesTest extends TestBase{

    @Test
    void downloadTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloaded = $(".paragraph [href=\"junit-user-guide-5.11.2.pdf\"]").download();
        try (InputStream is = new FileInputStream(downloaded)){
            byte[] data = is.readAllBytes();
            String dataAsString = new String(data, StandardCharsets.UTF_8);
            Assertions.assertTrue(dataAsString.contains("JUnit 5 User Guide"));
        }

        System.out.println();
    }

    @Test
    void uploadFileTest() {
        open("https://wdfiles.ru/");
        $(".slider-btn.slider-btn-upload").click();
        $("input[type='file']")
                .uploadFromClasspath("cutlertySet.png");
        $("#fileUploadRow .name").shouldHave(text("cutlertySet.png"));
    }
}