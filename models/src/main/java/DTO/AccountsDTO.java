package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTO {
    private String accountNumber;
    private double balance;
    private Long userId;
    private String accountType;
    private String status;
    private String currency;
    private double interestRate;
}
