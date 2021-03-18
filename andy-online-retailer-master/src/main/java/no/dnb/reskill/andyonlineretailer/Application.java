package no.dnb.reskill.andyonlineretailer;

import no.dnb.reskill.andyonlineretailer.bizlayer.ProductService;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepository;
import no.dnb.reskill.andyonlineretailer.models.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        // use interface on the rihgt side, so you can use all the implementations

        ProductService service = ctx.getBean(ProductService.class);
        ProductRepository repo = ctx.getBean("productRepositoryH2Database", ProductRepository.class);
        System.out.println("ALL PRODUCTS");
        repo.getAllProducts()
             .stream()
             .forEach(p -> System.out.println(p));

        repo.deleteProduct(1);

        System.out.println("one deleted");
        repo.getAllProducts()
                .stream()
                .forEach(p -> System.out.println(p));

        Product testProduct = new Product("testproduct",456.00,60);
        System.out.println("one product inserted");
        repo.insertProduct(testProduct);
        repo.getAllProducts()
                .stream()
                .forEach(p -> System.out.println(p));


        /*

        service.adjustPriceByPercent(1, 10);
        double totalValue = service.calculateTotalValue();
        System.out.println("Total value of the stock: " + totalValue);


        double averagePrice = service.getAveragePrice();
        System.out.println("The average price: " + averagePrice);

        ProductRepository repo = ctx.getBean("productRepositoryMemory", ProductRepository.class);

        repo.getAllProducts()
                .stream()
                .forEach(p -> System.out.println(p));

        service.calculateIncludedVATPrices().stream().forEach(product -> System.out.println("After added VAT: " + product));

*/
    }

}
