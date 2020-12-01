package pl.edu.agh.model;

import com.sun.istack.NotNull;
import lombok.*;
import pl.edu.agh.dao.CategoryDao;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Categories")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Subcategory> subcategories = new LinkedList<>();

    public Category(String name) {
        this.name = name;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
    }
}
