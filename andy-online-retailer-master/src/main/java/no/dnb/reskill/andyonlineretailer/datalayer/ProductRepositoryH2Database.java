package no.dnb.reskill.andyonlineretailer.datalayer;


import no.dnb.reskill.andyonlineretailer.models.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public class ProductRepositoryH2Database implements ProductRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Product> getAllProducts() {
        String sql = "SELECT p FROM Product p";
        
        // FROM ANDY: I would rename this variable as "query" (for example). It's a query (which will yield a collection when executed).
        TypedQuery<Product> collection = entityManager.createQuery(sql, Product.class);
        return collection.getResultList();
    }

    @Override
    public Product getProductById(long id) {
        return entityManager.find(Product.class, id);
    }

    @Transactional
    @Override
    public Product insertProduct(Product product) {

        // FROM ANDY: Yes indeed. I'll explain how this works shortly...
        if(product.getId() != -1) {
            throw new IllegalArgumentException("Product id must be -1 to be inserted");
        }
        entityManager.merge(product);
        return product;
    }

    @Transactional
    @Override
    public boolean updateProduct(Product product) {

        Product p = entityManager.find(Product.class, product.getId());
        if(p!=null) {
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setInStock(product.getInStock());
            return true;
        }
        else
            return false;
    }

    @Transactional
    @Override
    public boolean deleteProduct(long id) {
        Product p = entityManager.find(Product.class, id);
        if(p!=null) {
            entityManager.remove(p);
            return true;
        }
        else
            return false;

    }
}
