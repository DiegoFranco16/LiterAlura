package com.alura.literalura.model;

import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    private String idioma;
    private Integer numeroDeDescargas;


    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
//        DatosAutor primerAutor = datosLibro.autores().stream()
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún autor en el DatosLibro"));
        //this.autor = new Autor(primerAutor);
        this.idioma = datosLibro.idiomas().stream().findFirst().orElse(null);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
