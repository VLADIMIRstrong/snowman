package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void Test1() {
        $("[placeholder='Город']").setValue("Улан-Удэ");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Салтыков  Александр");
        $("[name='phone']").setValue("+75674567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);
    }

    @Test
    void EmptyFileCityTest() {
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Салтыков-Щедрин Владимир");
        $("[name='phone']").setValue("+70984567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void InvalidCityTest() {
        $("[placeholder='Город']").setValue("Улан-Уде");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Салтыков-Щедрин Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Доставка в выбранный город недоступна')]").shouldBe(visible);
    }

    @Test
    void DataTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[name='name']").setValue("Салтыков-Щедрин Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Неверно введена дата')]").shouldBe(visible);
    }

    @Test
    void DataTest2() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue("10.01.2021");
        $("[name='name']").setValue("Салтыков-Щедрин Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Заказ на выбранную дату невозможен')]").shouldBe(visible);
    }

    @Test
    void NoNameTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+79870560987");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void EnglishNameTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("John Rambo");
        $("[name='phone']").setValue("+79870567654");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Имя и Фамилия указаные неверно.')]").shouldBe(visible);
    }

    @Test
    void EmptyPhoneTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Сидоров Андрей");
        $("[name='phone']").setValue("");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void InvalidPhoneTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("456");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void InvalidPhoneTest2() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("+711456989010");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void InvalidPhoneWithoutPlusTest3() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("79095678907");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void CheckboxTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Сидоров Николай");
        $("[name='phone']").setValue("+79895678965");
        //$("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Я соглашаюсь с условиями обработки')]").shouldBe(visible);
    }
    @Test
    void InvalidCityTest2() {
        $("[placeholder='Город']").setValue("Moscow");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Салтыков-Щедрин Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Доставка в выбранный город недоступна')]").shouldBe(visible);
    }
}




