package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


class CardDeliveryOrderTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup() {
       Selenide.open("http://localhost:9999");
    }


    @Test
    void SuccessfulOrderForCardDelivery() {
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue(("Чита"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Куприянов Никита");
        $("[data-test-id='phone'] input").setValue("+78906754545");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,Duration.ofSeconds(15));
        $("[data-test-id='notification']").should(Condition.text("Успешно!"));
        $("[data-test-id='notification']")
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));

    }

}

