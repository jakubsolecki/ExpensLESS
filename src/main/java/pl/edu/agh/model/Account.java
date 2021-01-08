package pl.edu.agh.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Accounts")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new LinkedList<>();

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        transactions.add(Transaction.builder()
                .name("Saldo początkowe")
                .price(balance)
                .date(LocalDate.now())
                .account(this)
                .build());
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        if (transaction.getType() == Type.EXPENSE){
            setBalance(balance.add(transaction.getPrice().multiply(BigDecimal.valueOf(-1))));
        } else {
            setBalance(balance.add(transaction.getPrice()));
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
