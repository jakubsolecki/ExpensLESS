package pl.edu.agh.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Transactions")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subCategory;

    public Transaction(String name, BigDecimal price, LocalDate date, Account account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.account = account;
    }

    public Transaction(String name, BigDecimal price, LocalDate date, Account account, Subcategory subcategory) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.account = account;
        this.subCategory = subcategory;
    }

    public Transaction(String name, BigDecimal price, LocalDate date, String description, Account account, Subcategory subcategory) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.account = account;
        this.subCategory = subcategory;
    }

    public Transaction(String name, BigDecimal price, LocalDate date, String description, Account account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.account = account;
    }
}
