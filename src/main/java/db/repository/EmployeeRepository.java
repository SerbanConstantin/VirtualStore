package db.repository;

import model.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements CrudRepository<Employee, Integer> {

    private EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        //JPQL query - Java Persistence Query Language
        return entityManager.createQuery("SELECT e FROM Employee e ").getResultList();

    }

    public List<Employee> findByName(String name) {
        //JPQL query - Java Persistence Query Language
        return entityManager.createNamedQuery("Employee.findByName", Employee.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Employee save(Employee employee) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
            return employee;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public Employee deleteById(Integer id) {


        try {
            Employee employee = findById(id).get();
            entityManager.getTransaction().begin();
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
            return employee;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteByName(String given_name) {
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("DELETE FROM Employee e WHERE  e.name = :given_name")
                .setParameter("given_name", given_name)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            return Optional.of(employee);
        }

        return Optional.empty();

    }

}
