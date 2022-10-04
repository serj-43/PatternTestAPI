
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ClientGenerator;
import utils.IBankClient;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ClientTest {

    @BeforeEach
    void initOperations() {
        open("http://localhost:9999");
    }

    @Test
    void shouldHaveActiveUser() {
        IBankClient person = ClientGenerator.regUser("en", "active");
        $("[data-test-id='login'] input").setValue(person.getLogin());
        $("[data-test-id='password'] input").setValue(person.getPassword());
        $("[data-test-id='action-login']").click();
        $("#root").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldValidateLogin() {
        IBankClient person = ClientGenerator.regUser("en", "active");
        $("[data-test-id='login'] input").setValue(ClientGenerator.newLogin("en"));
        $("[data-test-id='password'] input").setValue(person.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(exactText("Ошибка Ошибка! Неверно указан логин" +
                " или пароль"));
    }

    @Test
    void shouldValidatePassword() {
        IBankClient person = ClientGenerator.regUser("en", "active");
        $("[data-test-id='login'] input").setValue(person.getLogin());
        $("[data-test-id='password'] input").setValue(ClientGenerator.newPassword("en"));
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(exactText("Ошибка Ошибка! Неверно указан логин" +
                " или пароль"));
    }

    @Test
    void shouldNotHaveSuchUser() {
        ClientGenerator.regUser("en", "active");
        IBankClient person = ClientGenerator.Client.newUser("en","active");
        $("[data-test-id='login'] input").setValue(person.getLogin());
        $("[data-test-id='password'] input").setValue(person.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(exactText("Ошибка Ошибка! Неверно указан логин" +
                " или пароль"));
    }

    @Test
    void shouldCheckBlockedUser() {
        IBankClient person = ClientGenerator.regUser("en", "active");
        ClientGenerator.changeUserStatus(person);
        $("[data-test-id='login'] input").setValue(person.getLogin());
        $("[data-test-id='password'] input").setValue(person.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(exactText("Ошибка Ошибка! Пользователь заблокирован"));
    }
}
