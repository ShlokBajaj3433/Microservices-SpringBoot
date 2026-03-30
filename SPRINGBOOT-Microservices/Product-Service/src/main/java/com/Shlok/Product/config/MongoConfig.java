package com.Shlok.Product.config;

import java.util.List;

import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig {

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(new LegacyBigDecimalToDoubleConverter()));
    }

    @ReadingConverter
    static class LegacyBigDecimalToDoubleConverter implements Converter<Document, Double> {

        @Override
        public Double convert(Document source) {
            Object rawValue = source.get("_value");

            if (rawValue instanceof Decimal128 decimal128) {
                return decimal128.doubleValue();
            }

            if (rawValue instanceof Number number) {
                return number.doubleValue();
            }

            if (rawValue instanceof String value) {
                return Double.valueOf(value);
            }

            return null;
        }
    }
}
