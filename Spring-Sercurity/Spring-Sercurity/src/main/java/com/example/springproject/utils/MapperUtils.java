package com.example.springproject.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between entities and DTOs using ModelMapper.
 */
public class MapperUtils {

  private MapperUtils() {} 

  public static final ModelMapper MODEL_MAPPER = new ModelMapper();

  /**
   * Maps a list of entities to a list of DTOs.
   *
   * @param entities  The list of entities to be mapped.
   * @param dtoClass  The class of the DTO to map to.
   * @param <T>       The type of entities.
   * @param <R>       The type of DTOs.
   * @return A list of DTOs.
   */
  public static <T, R> List<R> toDTOs(List<T> entities, Class<R> dtoClass) {
    return entities
          .stream()
          .map(entity -> MODEL_MAPPER.map(entity, dtoClass))
          .collect(Collectors.toList());
  }

  /**
   * Maps a list of DTOs to a list of entities.
   *
   * @param requestDTOs The list of DTOs to be mapped.
   * @param entityClass The class of the entity to map to.
   * @param <T>         The type of DTOs.
   * @param <R>         The type of entities.
   * @return A list of entities.
   */
  public static <T, R> List<R> toEntities(List<T> requestDTOs, Class<R> entityClass) {
    return requestDTOs
          .stream()
          .map(dto -> MODEL_MAPPER.map(dto, entityClass))
          .collect(Collectors.toList());
  }

  /**
   * Maps an entity to a DTO.
   *
   * @param entity   The entity to be mapped.
   * @param dtoClass The class of the DTO to map to.
   * @param <T>      The type of entity.
   * @param <R>      The type of DTO.
   * @return The mapped DTO.
   */
  public static <T, R> R toDTO(T entity, Class<R> dtoClass) {
    return MODEL_MAPPER.map(entity, dtoClass);
  }

  /**
   * Maps a DTO to an entity.
   *
   * @param dto        The DTO to be mapped.
   * @param entityClass The class of the entity to map to.
   * @param <T>         The type of DTO.
   * @param <R>         The type of entity.
   * @return The mapped entity.
   */
  public static <T, R> R toEntity(T dto, Class<R> entityClass) {
    return MODEL_MAPPER.map(dto, entityClass);
  }

}
