package pl.edu.agh.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Accounts")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}
