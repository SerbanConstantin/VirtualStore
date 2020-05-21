package db.repository;

import model.Cart;
import model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CartRepository implements CrudRepository<Cart, Integer> {
    private EntityManager entityManager;

    public CartRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Cart> findAll() {
        return entityManager.createQuery("SELECT c from Cart p").getResultList();
    }

    @Override
    public Cart save(Cart cart) {
        entityManager.getTransaction().begin();
        entityManager.persist(cart);
        entityManager.getTransaction().commit();
        return cart;
    }

    @Override
    public Cart deleteById(Integer id) {
        Cart cart = findById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(cart);
        entityManager.getTransaction().commit();
        return cart;
    }

    @Override
    public Optional<Cart> findById(Integer id) {
        Cart cart = entityManager.find(Cart.class, id);
        if (cart != null) {
            return Optional.of(cart);
        }

        return Optional.empty();
    }
}