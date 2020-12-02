package pl.edu.agh.model;


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
    private double price;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subCategory;
}
