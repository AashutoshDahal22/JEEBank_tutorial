package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsModel {

    private Long transactionId;
    private Long userId;
    private Double amount;
    private Long targetAccountUserId;
    private String transactionType;

}