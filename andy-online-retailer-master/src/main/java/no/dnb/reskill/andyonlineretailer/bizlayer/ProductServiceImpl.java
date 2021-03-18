package no.dnb.reskill.andyonlineretailer.bizlayer;

import no.dnb.reskill.andyonlineretailer.configuration.VatLevels;
import no.dnb.reskill.andyonlineretailer.models.Product;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository repository;
    private VatLevels vatLevels;
    @Autowired
    public ProductServiceImpl(@Qualifier("productRepositoryH2Database") ProductRepository repository, @Qualifier("vatSpecifications") VatLevels vatLevels ){
        this.repository = repository;
        this.vatLevels = vatLevels;
        repository.insertProduct(new Product("Lamborghini Sian", 2.2e6, 2));
        repository.insertProduct(new Product("Bugatti Divo", 4e6,1));
        repository.insertProduct(new Product("Jaguar I-Pacen", 80000, 4));
        repository.insertProduct(new Product("Mitten", 10, 10));
        repository.insertProduct(new Product("Hat", 100, 11));
        repository.insertProduct(new Product("Fender Jaguar 1965", 3000, 1));
        repository.insertProduct(new Product("Fender Champ 1962", 4000, 1));

    }



    @Override
    public double calculateTotalValue() {
        return repository.getAllProducts()
                         .stream()
                         .mapToDouble(p -> p.getPrice() * p.getInStock())
                         .sum();
    }
    @Override
    public Collection<Product> getLowStockProducts(long threashhold) {
        return repository.getAllProducts()
                         .stream()
                         .filter(p -> p.getInStock() < threashhold)
                         .collect(Collectors.toList());

    }

    @Override
    public double getAveragePrice() {
        return repository.getAllProducts()
                         .stream()
                         .mapToDouble(p -> p.getPrice() * p.getInStock())
                         .average()
                         .orElse(0.0);
    }

    @Override
    public void adjustPriceByPercent(long id, double byPercent) {
        Product theProduct = repository.getProductById(id);
        if(theProduct == null){
            return;
        } else{
            theProduct.adjustPriceByPersent(byPercent);
            repository.updateProduct(theProduct);
        }

    }
    @Override
    public Collection<Product> calculateIncludedVATPrices() {
        return repository.getAllProducts()
                .stream()
                .peek(product -> product.adjustPriceByPersent(getVatAccordingToPrice(product.getPrice())))
               .collect(Collectors.toList());
    }

    public double getVatAccordingToPrice(double price){
        if (price < vatLevels.getLowestPriceLevel()){
            return vatLevels.getLowestVAT();
        }
        if (price < vatLevels.getMediumPriceLevel()){
            return vatLevels.getMediumVat();
        }
        else{
            return vatLevels.getRidiculousVAT();
        }
    }

}
