package models;

import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import jakarta.persistence.*;

@Entity
@Table(name = "accountdetails")
@Indexed
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountnumber", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private UsersModel usersModel;

    @Column(name = "account_type", nullable = false)
    @FullTextField
    private String accountType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "interest_rate")
    private double interestRate;

    @Builder
    public AccountsModel(double balance, UsersModel usersModel, String accountType,
                         String status, String currency, double interestRate) {
        this.balance = balance;
        this.usersModel = usersModel;
        this.accountType = accountType;
        this.status = status;
        this.currency = currency;
        this.interestRate = interestRate;
    }
}
