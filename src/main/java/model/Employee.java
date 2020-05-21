package model;

import javax.persistence.*;

@NamedQueries(
        {
                @NamedQuery(
                        name = "Employee.findByName",
                        query = "SELECT e from Employee e WHERE e.name = :name" //JPQL syntax
                )
        }
)
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}