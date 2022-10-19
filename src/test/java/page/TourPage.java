package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DBHelper;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.util.ArrayList;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TourPage {

    HomePage homePage = new HomePage();

    private final SelenideElement continueButton = $x("//*[@id='root']/div/form/fieldset/div[4]/button");
    private final SelenideElement titleCard = $x("//*[@id='root']/div/h3");

    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement cardMonth = $("[placeholder='08']");
    private final SelenideElement cardYear = $("[placeholder='22']");
    private final SelenideElement cardHolder = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cardCVC = $("[placeholder='999']");


    private final SelenideElement notificationTitleAccept = $(".notification_status_ok");
    private final SelenideElement notificationContentAccept = $(".notification_status_ok");

    private final SelenideElement notificationTitleDenial = $(".notification_status_error");
    private final SelenideElement notificationContentDenial = $(".notification_status_error");

    private final SelenideElement numberFieldError = $x("//*[@id='root']/div/form/fieldset/div[1]/span/span/span[3]");
    private final SelenideElement monthFieldError = $x("//*[@id='root']/div/form/fieldset/div[2]/span/span[1]/span/span/span[3]");
    private final SelenideElement yearFieldError = $x("//*[@id='root']/div/form/fieldset/div[2]/span/span[2]/span/span/span[3]");
    private final SelenideElement holderFieldError = $x("//*[@id='root']/div/form/fieldset/div[3]/span/span[1]/span/span/span[3]");
    private final SelenideElement CVCFieldError = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[3]");

    public void completePayForm(String number, String month, String year, String holder, String cvc) {
        homePage.clickPayButton();
        titleCard.shouldBe(Condition.text("Оплата по карте"));
        cardNumber.setValue(number);
        cardMonth.setValue(month);
        cardYear.setValue(year);
        cardHolder.setValue(holder);
        cardCVC.setValue(cvc);
    }

    public void completeCreditForm(String number, String month, String year, String holder, String cvc) {
        homePage.clickCreditButton();
        titleCard.shouldBe(Condition.text("Кредит по данным карты"));
        cardNumber.setValue(number);
        cardMonth.setValue(month);
        cardYear.setValue(year);
        cardHolder.setValue(holder);
        cardCVC.setValue(cvc);
    }

    public ArrayList<String> getForm() {
        ArrayList<String> form = new ArrayList<>();
        form.add(cardNumber.getValue());
        form.add(cardMonth.getValue());
        form.add(cardYear.getValue());
        form.add(cardHolder.getValue());
        form.add(cardCVC.getValue());
        return form;
    }

    public void continueClick() {
        continueButton.click();
    }

    public void acceptAssertion() {
        notificationTitleAccept.shouldBe(Condition.text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        notificationContentAccept.shouldBe(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void denialAssertion() {
        notificationTitleDenial.shouldBe(Condition.text("Ошибка"), Duration.ofSeconds(15)).shouldBe(visible);
        notificationContentDenial.shouldBe(Condition.text("Ошибка! Банк отказал в проведении операции"), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void numberFieldFormatError() {
        numberFieldError.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void monthFieldFormatError() {
        monthFieldError.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void monthFieldPeriodError() {
        monthFieldError.shouldBe(Condition.text("Неверно указан срок действия карты"), Condition.visible);
    }

    public void yearFieldFormatError() {
        yearFieldError.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void yearFieldMinusPeriodError() {
        yearFieldError.shouldBe(Condition.text("Истек срок действия карты"), visible);
    }

    public void yearFieldPlusPeriodError() {
        yearFieldError.shouldBe(Condition.text("Истек срок действия карты"), visible);
    }

    public void yearFieldPeriodError() {
        yearFieldError.shouldBe(Condition.text("Неверно указан срок действия карты"));
    }

    public void holderFieldEmptyError() {
        holderFieldError.shouldBe(Condition.text("Поле обязательно для заполнения"), visible);
    }

    public void holderFieldFormatError() {
        holderFieldError.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void CVCFieldFormatError() {
        CVCFieldError.shouldBe(Condition.text("Неверный формат"), visible);
    }

    public void payApprovedStatusAssertion() {
        String statusExpected = "APPROVED";
        String statusActual = DBHelper.getPaymentStatus();
        Assertions.assertEquals(statusExpected, statusActual);
    }

    public void payDeclinedStatusAssertion() {
        String statusExpected = "DECLINED";
        String statusActual = DBHelper.getPaymentStatus();
        Assertions.assertEquals(statusExpected, statusActual);
    }

    public void payAcceptCountAssertion() {
        long countExpected = 1;
        long countActual = DBHelper.getPaymentCount();
        Assertions.assertEquals(countExpected, countActual);
    }

    public void payDenialCountAssertion() {
        long countExpected = 0;
        long countActual = DBHelper.getPaymentCount();
        Assertions.assertEquals(countExpected, countActual);
    }

    public void creditApprovedStatusAssertion() {
        String statusExpected = "APPROVED";
        String statusActual = DBHelper.getCreditStatus();
        Assertions.assertEquals(statusExpected, statusActual);
    }

    public void creditDeclinedStatusAssertion() {
        String statusExpected = "DECLINED";
        String statusActual = DBHelper.getCreditStatus();
        Assertions.assertEquals(statusExpected, statusActual);
    }

    public void creditAcceptCountAssertion() {
        long countExpected = 1;
        long countActual = DBHelper.getCreditCount();
        Assertions.assertEquals(countExpected, countActual);
    }

    public void creditDenialCountAssertion() {
        long countExpected = 0;
        long countActual = DBHelper.getCreditCount();
        Assertions.assertEquals(countExpected, countActual);
    }

    public void orderAcceptCountAssertion() {
        long countExpected = 1;
        long countActual = DBHelper.getOrderCount();
        Assertions.assertEquals(countExpected, countActual);
    }

    public void orderDenialCountAssertion() {
        long countExpected = 0;
        long countActual = DBHelper.getOrderCount();
        Assertions.assertEquals(countExpected, countActual);
    }
}