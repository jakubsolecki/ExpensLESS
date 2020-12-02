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
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

//    private List<Transaction> transactions; // TODO do we need this? Bart: Oh I dont think so


    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
        category.addSubcategory(this);
    }
}
