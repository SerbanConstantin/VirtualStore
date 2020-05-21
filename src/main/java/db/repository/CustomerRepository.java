package db.repository;

import model.Customer;
import model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements CrudRepository<Customer, Integer> {
    private EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c from Customer c").getResultList();
    }

    @Override
    public Customer save(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    @Override
    public Customer deleteById(Integer id) {
        Customer customer = findById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            return Optional.of(customer);
        }

        return Optional.empty();
    }
}