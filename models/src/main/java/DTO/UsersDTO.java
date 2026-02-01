package DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String address;
    private LocalDate birthdate;
    private String phoneNumber;
    private String role;
}
