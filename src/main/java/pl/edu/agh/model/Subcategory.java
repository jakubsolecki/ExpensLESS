package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Subcategories")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    private Category category;

//    private List<Transaction> transactions; // TODO do we need this?

    public Subcategory(String name, Category category) { // TODO category name instead fo category?
        this.name = name;
        this.category = category;
        category.addSubcategory(this);
    }
}
