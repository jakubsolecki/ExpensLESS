package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CategoryBudget> categoryBudgetList = new LinkedList<>();

    public void addCategoryBudget(CategoryBudget categoryBudget){
        categoryBudgetList.add(categoryBudget);
    }

    @NonNull
    @NotNull
    private int year;

    @NonNull
    @NotNull
    private Month month;
}
