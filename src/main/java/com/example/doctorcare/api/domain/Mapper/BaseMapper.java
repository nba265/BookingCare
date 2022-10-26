package com.example.doctorcare.api.domain.Mapper;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public abstract class BaseMapper<E,T> {

    public abstract E convertToEntity(T dto, Object... args);

    public abstract T convertToDto(E entity, Object... args);

    public Collection<E> convertToEntity(Collection<T> dto, Object... args) {
        return dto.stream().map(d -> convertToEntity(d, args)).collect(Collectors.toList());
    }

    public Collection<T> convertToDto(Collection<E> entity, Object... args) {
        return entity.stream().map(d -> convertToDto(d, args)).collect(Collectors.toList());
    }

    public Set<E> convertToEntitySet(Collection<T> dto, Object... args) {
        return convertToEntity(dto, args).stream().collect(Collectors.toSet());
    }

    public Set<T> convertToDtoSet(Collection<E> entity, Object... args) {
        return convertToDto(entity, args).stream().collect(Collectors.toSet());
    }

    public List<E> convertToEntityList(Collection<T> dto, Object... args) {
        return convertToEntity(dto, args).stream().collect(Collectors.toList());
    }

    public List<T> convertToDtoList(Collection<E> entity, Object... args) {
        return convertToDto(entity, args).stream().collect(Collectors.toList());
    }

}
