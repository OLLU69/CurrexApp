package ua.dp.ollu.currex.currex_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Config for Jackson
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    ObjectMapper jsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        setUpMapper(mapper);

        return mapper;
    }

    @Bean
    XmlMapper xmlMapper() {
        XmlMapper mapper = new XmlMapper();
        setUpMapper(mapper);
        return mapper;
    }

    private void setUpMapper(ObjectMapper mapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}

