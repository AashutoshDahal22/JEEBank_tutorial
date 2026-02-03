package DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import users.ClientRoles;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsersPatchDTO {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate birthdate;
    private ClientRoles roles;
}
