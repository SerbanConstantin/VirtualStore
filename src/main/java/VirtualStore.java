import db.repository.CartRepository;
import db.repository.CustomerRepository;
import db.repository.EmployeeRepository;
import db.repository.ProductRepository;
import model.Cart;
import model.Customer;
import model.Employee;
import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


public class VirtualStore {

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("Virtual_Store");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
        ProductRepository productRepository = new ProductRepository(entityManager);
        CartRepository cartRepository = new CartRepository(entityManager);
        CustomerRepository customerRepository = new CustomerRepository(entityManager);

        String menu = "1. Find by id\n" +
                "2. Insert employee\n" +
                "3. Delete by id \n" +
                "4. Show all employees \n" +
                "5. Show employee by name\n" +
                "6. Delete records from table by name \n" +
                "11. Find product by id\n" +
                "12. Insert product\n" +
                "13. Delete product by id \n" +
                "14. Show all products \n" +
                "21. Find customer by id\n" +
                "22. Insert customer\n" +
                "23. Delete customer by id \n" +
                "24. Show all customers \n" +
                "25. Add products in cart \n" +
                "31. Find cart by id\n" +
                "32. Insert cart\n" +
                "33. Delete cart by id \n" +
                "34. Show all carts \n";

        System.out.println(menu);
        String option = bufferedReader.readLine();
        while (!option.equals("-1")) {

            int chosenOption = Integer.parseInt(option);

            switch (chosenOption) {
                case 1:
                    System.out.print("Give id to search for: ");
                    String searchId = bufferedReader.readLine();

                    Optional optional = employeeRepository.findById(Integer.parseInt(searchId));
                    Employee search_employee = (Employee) optional.get();

                    System.out.print("Give new name: ");
                    String new_name = bufferedReader.readLine();
                    search_employee.setName(new_name);
                    employeeRepository.save(search_employee);

                    System.out.println(search_employee.getId() + " " + search_employee.getName());
                    break;
                case 2:
//                    System.out.println(entityManager.getClass());
                    Employee employee = new Employee();
                    System.out.println(employee.getClass());
                    System.out.print("Give name: ");
                    String employee_name = bufferedReader.readLine();
                    employee.setName(employee_name);

                    employeeRepository.save(employee);
                    break;
                case 3:
                    System.out.print("Give id to delete: ");
                    String deleteId = bufferedReader.readLine();
                    employeeRepository.deleteById(Integer.parseInt(deleteId));
                    break;
                case 4:
                    for (Employee e : employeeRepository.findAll()) {
                        System.out.println(e.getId() + " " + e.getName());
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Give name: ");
                    String readName = bufferedReader.readLine();
                    for (Employee e : employeeRepository.findByName(readName)) {
                        System.out.println(e.getId() + " " + e.getName());
                    }
                    System.out.println();
                    break;
                case 6:
                    System.out.print("Give name to delete: ");
                    String deleteName = bufferedReader.readLine();
                    employeeRepository.deleteByName(deleteName);
                    break;
                case 11:
                    System.out.print("Give product id to search for: ");


                    Product product = productRepository.findById(Integer.parseInt(bufferedReader.readLine())).get();

                    System.out.println(product.getCode() + " " + product.getName());
                    break;
                case 12:
//                    System.out.println(entityManager.getClass());
                    Product new_product = new Product();

                    System.out.print("Give code: ");
                    new_product.setCode(Integer.parseInt(bufferedReader.readLine()));
                    System.out.print("Give name: ");
                    new_product.setName(bufferedReader.readLine());

                    productRepository.save(new_product);
                    break;
                case 13:
                    System.out.print("Give product id to delete: ");
                    productRepository.deleteById(Integer.parseInt(bufferedReader.readLine()));
                    break;
                case 14:
                    for (Product p : productRepository.findAll()) {
                        System.out.println(p);
                    }
                    System.out.println();
                    break;

                case 21:
                    System.out.print("Give customer id to search for: ");
                    System.out.println(customerRepository.findById(Integer.parseInt(bufferedReader.readLine())).get());
                    break;
                case 22:
//                    System.out.println(entityManager.getClass());
                    Customer customer = new Customer();
                    Cart cart = new Cart();
                    customer.setCart(cart);
                    System.out.print("Give name: ");
                    customer.setName(bufferedReader.readLine());

                    customerRepository.save(customer);
                    break;
                case 23:
                    System.out.print("Give customer id to delete: ");
                    customerRepository.deleteById(Integer.parseInt(bufferedReader.readLine()));
                    break;
                case 24:
                    for (Customer c : customerRepository.findAll()) {
                        System.out.println(c);
                    }
                    System.out.println();
                    break;
                case 25:
                    System.out.println("Which customer are you ");
                    List<Customer> customers = customerRepository.findAll();
                    for (Customer c : customers) {
                        System.out.println(c);
                    }
                    Customer customer1 = customers.get(Integer.parseInt(bufferedReader.readLine()));

                    System.out.println("Chose product:");
                    Product product1;
                    List<Product> products = productRepository.findAll();
                    for (Product p : products) {
                        System.out.println(p);
                    }
                    product1 = products.get(Integer.parseInt(bufferedReader.readLine()));
                    product1.setCart(customer1.getCart());
                    if (customer1.getCart().getProducts() == null) {
                        customer1.getCart().setProducts(new HashSet<>());
                    }
                    customer1.getCart().getProducts().add(product1);
                    cartRepository.save(customer1.getCart());
                    productRepository.save(product1);
                    customerRepository.save(customer1);


                    System.out.println();
                    break;


            }
            System.out.println(menu);
            option = bufferedReader.readLine();
        }
    }
}