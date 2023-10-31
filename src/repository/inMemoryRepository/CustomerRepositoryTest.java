//package repository.inMemoryRepository.tests;
//
//public class CustomerRepositoryTest {
//}
package repository.inMemoryRepository;

import domain.persons.Customer;
import domain.persons.Gender;
import org.junit.jupiter.api.Test;
import repository.exceptions.ObjectAlreadyContained;
import repository.exceptions.ObjectNotContained;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTest {
    private final CustomerRepository customerRepository = CustomerRepository.getInstance();

    @Test
    public void testAdd() throws ObjectAlreadyContained {
        Customer customer = new Customer("testCustomer", "Test", LocalDate.of(1990, 5, 5), Gender.male);
        customerRepository.add(customer);
        ArrayList<Customer> customers = customerRepository.getAll();
        assertTrue(customers.contains(customer));
        System.out.println("Test add in CustomerRepo passed, bravo!");
    }

    @Test
    public void testUpdate() throws ObjectAlreadyContained, ObjectNotContained {
        Customer customer = new Customer("testCustomer", "Test", LocalDate.of(1990, 5, 5), Gender.male);
        customerRepository.add(customer);

        customerRepository.update(customer);

        ArrayList<Customer> customers = customerRepository.getAll();
        assertTrue(customers.contains(customer));
        System.out.println("Test update in CustomerRepo passed, bravo!");
    }

    @Test
    public void testDelete() throws ObjectAlreadyContained, ObjectNotContained {
        Customer customer = new Customer("testCustomer", "Test", LocalDate.of(1990, 5, 5), Gender.male);
        customerRepository.add(customer);

        customerRepository.delete(customer);

        ArrayList<Customer> customers = customerRepository.getAll();
        assertFalse(customers.contains(customer));
        System.out.println("Test delete in CustomerRepo passed, bravo!");
    }

    @Test
    public void testGetAll() {
        ArrayList<Customer> customers = customerRepository.getAll();
        assertNotNull(customers);
        System.out.println("Test getAll in CustomerRepo passed, bravo!");
    }
}
