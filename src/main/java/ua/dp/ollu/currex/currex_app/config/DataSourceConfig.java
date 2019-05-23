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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
    public DbSettings dbSettings() {
        return new DbSettings(
                env.getProperty("spring.datasource.url"),
                env.getProperty("spring.datasource.username"),
                env.getProperty("spring.datasource.password"));
    }

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
        JdbcTemplate template = new JdbcTemplate(dataSource);
        createIfNotExistDb(template);
        return template;
    }

    @Bean
    RestTemplate restTemplate() {
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(Arrays.asList(
//                new MappingJackson2HttpMessageConverter(),
//                new MappingJackson2XmlHttpMessageConverter()
//        ));
        return new RestTemplate();
    }

    private void createIfNotExistDb(JdbcTemplate template) {
        try {
            ResultSet reference = Objects.requireNonNull(template.getDataSource()).getConnection().getMetaData().getTables(null, null, "reference", null);
            if (!reference.next()) {
                createDb(template);
            }
        } catch (SQLException e) {
            createDb(template);
            e.printStackTrace();
        }
    }

    private void createDb(JdbcTemplate template) {
        try {
            String sql = sqlFromResource("/reference.sql");
            template.execute(sql);
            sql = sqlFromResource("/operations.sql");
            template.execute(sql);
            sql = sqlFromResource("/reference_data.sql");
            String[] lines = sql.split("\n");
            for (String line : lines) {
                template.execute(line);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private String sqlFromResource(String path) {
        InputStream stream = null;
        try {
            URL resource = getClass().getResource(path);
            stream = resource.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
