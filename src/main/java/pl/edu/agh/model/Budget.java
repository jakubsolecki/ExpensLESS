package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.Month;
import java.util.List;

@Entity(name = "Budgets")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CategoryBudget> categoryBudgetList;

    @NonNull
    @NotNull
    private int year;

    @NonNull
    @NotNull
    private Month month;
}
