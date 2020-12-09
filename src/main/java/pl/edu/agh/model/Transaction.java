package pl.edu.agh.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private double price;

    private String description;

    @Column(nullable = false)
    private Date date;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subCategory;

    public Transaction(String name, double price, Date date, Account account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.account = account;
    }

    public Transaction(String name, double price, Date date, Account account, Subcategory subcategory) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.account = account;
        this.subCategory = subcategory;
    }

    public Transaction(String name, double price, Date date, String description, Account account, Subcategory subcategory) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.account = account;
        this.subCategory = subcategory;
    }

    public Transaction(String name, double price, Date date, String description, Account account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.account = account;
    }
}
