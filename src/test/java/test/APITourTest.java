package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.APIHelper;
import data.DBHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.TourPage;

public class APITourTest {
    TourPage tourPage = new TourPage();

    @AfterEach
    void clearDB() {
        DBHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("89) HappyPath PayCard Status Approved API Test Response: 200")
    void shouldResponse200ByPayCardApproved() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedCard())),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("90) HappyPath PayCard Status Declined API Test Response: 200")
    void shouldResponse200ByPayCardDeclined() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidDeclineCard())),
                () -> tourPage.payDeclinedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("91) HappyPath CreditCard Status Approved API Test Response: 200")
    void shouldResponse200ByPayCreditCard() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyCreditCardAPI(DataHelper.getValidApprovedCard())),
                () -> tourPage.creditApprovedStatusAssertion(),
                () -> tourPage.creditAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("92) HappyPath CreditCard Status Declined API Test Response: 200")
    void shouldResponse200ByPayCreditCardDeclined() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyCreditCardAPI(DataHelper.getValidDeclineCard())),
                () -> tourPage.creditDeclinedStatusAssertion(),
                () -> tourPage.creditAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("93) Empty PayCard API Test Response: 400")
    void shouldResponse400ByPayEmptyPayCard() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getEmptyCard())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("94) Approved PayCard And Random Invalid Field API Test Response: 400")
    void shouldResponse400ByApprovedPayCardAndRandomInvalidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getValidCardAndRandomInvalidFields())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("95) Approved PayCard And Empty Fields API Test Response: 400")
    void shouldResponse400ByApprovedPayCardAndEmptyField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedNumberAndEmptyFields())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("96) Random PayCard Number And Valid Other Fields API Test Response: 400")
    void shouldResponse400ByRandomCardNumberAndValidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getRandomCardAndValidFields())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("97) Empty CreditCard API Test Response 400")
    void shouldResponse400ByPayEmptyCreditCard() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getEmptyCard())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("98) Approved CreditCard And Random Invalid Fields API Test Response: 400")
    void shouldResponse400ByApprovedCreditCardAndRandomInvalidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getValidCardAndRandomInvalidFields())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("99) Approved CreditCard And Empty Fields API Test Response: 400")
    void shouldResponse400ByApprovedCreditCardAndEmptyOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getValidApprovedNumberAndEmptyFields())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("100) Random CreditCard Number And Valid Fields API Test Response 400")
    void shouldResponse400ByRandomCreditCardNumberAndValidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getRandomCardAndValidFields())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
}