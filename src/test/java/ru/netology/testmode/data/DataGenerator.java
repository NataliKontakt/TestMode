package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(RegistrationDto user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationDto(user.getLogin(), user.getPassword(), user.getStatus())) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user
        //  и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
    }

    public static String getRandomLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName(); //тут будет другое (логин)
        return login;
    }

    public static String getRandomPassword() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password(); //тут будет другое (пароль)
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
            // TODO: создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);
            // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
            // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
            return registeredUser;
        }
    }


    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}
