package models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDate;

@Entity
@Table(name = "userdetails")
@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersModel {

    @Id
    // hibernate generates the id when it runs the insert to insert our objects into the tables
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    @FullTextField
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "phonenumber", length = 15)
    private String phoneNumber;

    @Builder
    public UsersModel(String name, String email, String address, LocalDate birthdate, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }
}
