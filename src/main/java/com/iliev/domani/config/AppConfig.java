package com.iliev.domani.config;

import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Set;

@Configuration
public class AppConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new Converter<Set, Integer>() {
            @Override
            public Integer convert(MappingContext<Set, Integer> mappingContext) {
                return mappingContext.getSource().size();
            }
        });

        modelMapper.addConverter(new Converter<CategoryEntity, CategoryNameEnum>() {
            @Override
            public CategoryNameEnum convert(MappingContext<CategoryEntity, CategoryNameEnum> mappingContext) {
                return mappingContext.getSource().getName();
            }
        });
        return modelMapper;
    }
}
