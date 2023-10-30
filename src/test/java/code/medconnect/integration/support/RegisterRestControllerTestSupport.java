package code.medconnect.integration.support;

import code.medconnect.api.controller.rest.RegisterRestController;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface RegisterRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default String register(
            String username,
            String password,
            String name,
            String surname,
            String pesel,
            String email,
            String phoneNumber,
            String dateOfBirthStr,
            String country,
            String city,
            String postalCode,
            String address
    ) {
        String endpoint = RegisterRestController.REGISTER_API_BASE_PATH;

        return requestSpecification()
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("name", name)
                .queryParam("surname", surname)
                .queryParam("pesel", pesel)
                .queryParam("email", email)
                .queryParam("phoneNumber", phoneNumber)
                .queryParam("dateOfBirth", dateOfBirthStr)
                .queryParam("country", country)
                .queryParam("city", city)
                .queryParam("postalCode", postalCode)
                .queryParam("address", address)
                .post(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
    }
}
