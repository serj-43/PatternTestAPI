
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ClientGenerator;
import utils.IBankClient;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ClientTest {

    public static IBankClient person1, person2;

    @BeforeAll
    public static void initOperations() {
        person1 = ClientGenerator.regUser("en", "active");
        person2 = ClientGenerator.regUser("en", "blocked");
        open("http://localhost:9999");
    }

    @Test
    void shouldHaveActiveUser() {
        $("[data-test-id='login'] input").setValue(person1.getLogin());
        $("[data-test-id='password'] input").setValue(person1.getPassword());
        $("[data-test-id='action-login']").click();
        $("#root").shouldHave(exactText("Личный кабинет"));
    }
}
