package code.medconnect.integration.rest;

import code.medconnect.integration.configuration.RestAssuredIntegrationTestBase;
import code.medconnect.integration.support.RegisterRestControllerTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterRestControllerTest
        extends RestAssuredIntegrationTestBase
        implements RegisterRestControllerTestSupport {


    @Test
    void registerTest() {
        //given
        String username = "tester";
        String password = "test";
        String name = "Tester";
        String surname = "Testowsky";
        String pesel = "12312312399";
        String email = "test@test.com";
        String phoneNumber = "123-456-789";
        String dateOfBirthStr = "2007-12-03";
        String country = "Poland";
        String city = "Warsaw";
        String postalCode = "00-000";
        String address = "Ziemniakowa 12";

        //when
        String response = register(
                username, password, name, surname, pesel, email, phoneNumber,
                dateOfBirthStr, country, city, postalCode, address
        );

        //then
        Assertions.assertEquals("Patient successfully registered", response);
    }
}
