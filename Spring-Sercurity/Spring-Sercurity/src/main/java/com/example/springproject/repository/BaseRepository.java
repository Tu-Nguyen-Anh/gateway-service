package com.example.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface that extends JpaRepository. This interface is annotated with @NoRepositoryBean,
 * indicating that it should not be treated as a repository to create concrete instances during component scanning.
 * Instead, it serves as a common interface for other repositories to extend.
 *
 * @param <T> The type of the entity managed by the repository.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String> {
}