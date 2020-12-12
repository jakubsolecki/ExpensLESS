package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "CategoryBudgets")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotNull
//    @Column(nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Category category;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private BigDecimal plannedBudget;
}
