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
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CategoryBudget> categoryBudgetList = new LinkedList<>();

    @NonNull
    @NotNull
    private int year;

    @NonNull
    @NotNull
    private Month month;

    public void addCategoryBudget(CategoryBudget categoryBudget){
        categoryBudgetList.add(categoryBudget);
    }

    @Override
    public String toString() {
        return Integer.toString(year) + " " + month.toString();
    }
}
