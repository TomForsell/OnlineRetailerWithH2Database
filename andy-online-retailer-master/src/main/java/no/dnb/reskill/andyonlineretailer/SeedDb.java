package no.dnb.reskill.andyonlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class SeedDb {

    @Autowired
    JdbcTemplate template;

    @Autowired
    public SeedDb(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("insert into PRODUCTS (description, price, in_Stock) values (?, ?, ?)", new Object[]{"Genser", 20.00, 4});
    }
}
