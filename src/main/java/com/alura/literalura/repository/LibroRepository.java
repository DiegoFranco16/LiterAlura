package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdiomaIgnoreCase(String idioma);
    @Query("SELECT l FROM Libro l WHERE l.numeroDeDescargas > :minDescargas")
    List<Libro> findLibrosConDescargasMayores(@Param("minDescargas") int minDescargas);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDeDescargas DESC")
    List<Libro> findTop10Libros();
}
