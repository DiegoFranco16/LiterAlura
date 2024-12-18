package com.alura.literalura.principal;

import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.DatosRespuesta;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private List<Libro> libros;
    Optional<Libro> libroBuscado;

    private LibroRepository repositorio;
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    private DatosLibro getDatosLibroPorNombre() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        //System.out.println(json);
        DatosRespuesta datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class);
        DatosLibro datos = datosRespuesta.resultados().get(0);
        return datos;

    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibroPorNombre();
        System.out.println("--------------------------------");
        System.out.println(datos);
        //Libro libro = new Libro(datos);

    }
}
