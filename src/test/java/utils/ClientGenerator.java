package utils;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class ClientGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void createUser(IBankClient user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @UtilityClass
    public static class Client {
        public static IBankClient NewUser(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new IBankClient(newLogin(locale), newPassword(locale), status);
        }
    }

    public static IBankClient regUser(String locale, String status) {
        IBankClient user = Client.NewUser(locale, status);
        createUser(user);
        return user;
    }

    public static IBankClient changeUserStatus(IBankClient user) {
        String status = user.getStatus();
        switch (status) {
            case "active":
                user.setStatus("blocked");
                break;
            case "blocked":
                user.setStatus("active");
                break;
        }
        createUser(user);
        return user;
    }
    public static String newLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String password = faker.name().username();
        return password;
    }
    public static String newPassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String password = faker.internet().password();
        return password;
    }

}
