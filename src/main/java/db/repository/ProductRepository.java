package db.repository;

import model.Employee;
import model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements CrudRepository<Product, Integer> {

    private EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p").getResultList();

    }

//    public List<Product> findByName(String name) {
//        //JPQL query - Java Persistence Query Language
//        return entityManager.createNamedQuery("Product.findByName", Product.class)
//                .setParameter("name", name)
//                .getResultList();
//    }

    @Override
    public Product save(Product product) {

            entityManager.getTransaction().begin();
            entityManager.persist(product);
            entityManager.getTransaction().commit();
            return product;

    }

    @Override
    public Product deleteById(Integer id) {
            Product product = findById(id).get();
            entityManager.getTransaction().begin();
            entityManager.remove(product);
            entityManager.getTransaction().commit();
            return product;


    }

//    public void deleteByName(String given_name) {
//        entityManager.getTransaction().begin();
//        entityManager
//                .createQuery("DELETE FROM Product e WHERE  p.name = :given_name")
//                .setParameter("given_name", given_name)
//                .executeUpdate();
//        entityManager.getTransaction().commit();
//    }

    @Override
    public Optional<Product> findById(Integer id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            return Optional.of(product);
        }

        return Optional.empty();


    }
}