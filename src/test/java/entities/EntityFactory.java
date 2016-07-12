package entities;

import org.apache.log4j.Logger;
import org.lamzin.eshop.entites.OrderCard;
import org.lamzin.eshop.entites.Person;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 15.06.2016.
 */
@Component
@Scope (BeanDefinition.SCOPE_PROTOTYPE)
public class EntityFactory {
    private List<Category> categories;
    private List<SubCategory> subCategories;
    private List<Product> products;

    private List<OrderCard> orderCards;
    private List<Person> persons;

    private final Logger log = Logger.getLogger(this.getClass());

    public EntityFactory() {
        createSubCategories();
        createCategories(subCategories);
        createProducts(subCategories);
        createPersons();
        createOrderCards(persons);
    }

    private void createOrderCards(List<Person> persons) {
        orderCards = new ArrayList<OrderCard>();
        for (int i = 0; i < 3; i++) {
            OrderCard orderCard = new OrderCard();
            orderCard.setPerson(persons.get(i));
            orderCard.setId(i);
            orderCard.setProducts(products);
            orderCards.add(orderCard);
        }
        log.info(orderCards);
    }

    private void createPersons() {
        persons = new ArrayList<Person>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person();
            person.setEmail("email@" + String.valueOf(i));
            persons.add(person);
        }
        log.info(persons);
    }

    private void createProducts(List<SubCategory> subCategories) {
        products = new ArrayList<Product>();
        int k = 0;
        for (SubCategory s : subCategories) {

            for (int i = k*50; i < (k*50) + 50; i++) {
                Product product = new Product();
                product.setSubCategory(s);
                product.setName("name_" + String.valueOf(i));
                product.setPrice(i*100 + 1);
                product.setId(i);
                product.setProducer("producer_" + String.valueOf(i));
                products.add(product);
            }
            k++;
        }
        log.info(products);
    }

    private void createCategories(List<SubCategory> subCategories) {
        categories = new ArrayList<Category>();
        for (int i = 0; i < 2; i++) {
            Category category = new Category();
            category.setSubCategories(subCategories.subList((i * 9),(i+1)*9));
            category.setCategoryId("id_" + String.valueOf(i));
            category.setName("name_" + String.valueOf(i));
            categories.add(category);
        }
        log.info(categories);
    }

    private void createSubCategories() {
        subCategories = new ArrayList<SubCategory>();
        for (int i = 0; i < 20; i++) {
            SubCategory subCategory = new SubCategory();
            subCategory.setSubCategoryId("id_" + String.valueOf(i));
            subCategory.setSubCategoryName("name_" + String.valueOf(i));
            subCategories.add(subCategory);
        }
        log.info(subCategories);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<OrderCard> getOrderCards() {
        return orderCards;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
