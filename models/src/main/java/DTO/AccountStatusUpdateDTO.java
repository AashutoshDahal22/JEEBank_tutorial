package DTO;

import accounts.AccountsStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountStatusUpdateDTO {
    private AccountsStatus status;
}
