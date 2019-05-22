package ua.dp.ollu.currex.currex_app.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class DataSourceConfig {
    private Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    //    @Bean
//    public DataSource dataSource() {
//        return new DriverManagerDataSource(
//                env.getProperty("spring.datasource.url"),
//                env.getProperty("spring.datasource.username"),
//                env.getProperty("spring.datasource.password"));
//    }
//
    @Bean
    public DataSource commonDataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        source.setUrl(env.getProperty("spring.datasource.url"));
        source.setUsername(env.getProperty("spring.datasource.username"));
        source.setPassword(env.getProperty("spring.datasource.password"));
        return source;
    }

    @Bean
    @DependsOn(value = "commonDataSource")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    RestTemplate restTemplate() {
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(Arrays.asList(
//                new MappingJackson2HttpMessageConverter(),
//                new MappingJackson2XmlHttpMessageConverter()
//        ));
        return new RestTemplate();
    }
}
