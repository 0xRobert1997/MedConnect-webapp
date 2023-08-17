package code.medconnect.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String address;


}
