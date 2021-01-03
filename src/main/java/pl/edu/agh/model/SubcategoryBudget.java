package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "SubcategoryBudgets")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubcategoryBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Subcategory subcategory;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private BigDecimal plannedBudget;

    @NonNull
    @NotNull
    @ManyToOne
    private Budget budget;

    public SubcategoryBudget(Subcategory subcategory, BigDecimal bigDecimal) {
        this.subcategory = subcategory;
        this.plannedBudget = bigDecimal;
    }
}
