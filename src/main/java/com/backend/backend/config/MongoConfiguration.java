package com.backend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext,
            MongoCustomConversions conversions
    ) {
        MappingMongoConverter converter = new MappingMongoConverter(
                mongoDatabaseFactory,
                mongoMappingContext
        );
        converter.setCustomConversions(conversions);
        //disable _class in the Database
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

}
