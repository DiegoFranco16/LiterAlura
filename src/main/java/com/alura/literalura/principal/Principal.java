package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    Optional<Libro> libroBuscado;

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
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
                    6 - Listar libros con descargas mayores a un número
                    7 - Top 10 libros más descargados
                    8 - Mostrar estadísticas de descargas
                                  
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
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    listarLibrosConDescargasMayores();
                    break;
                case 7:
                    mostrarTop10LibrosMasDescargados();
                    break;
                case 8:
                    mostrarEstadisticasDescargas();
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
        //var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        String nombreLibroCodificado = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        String json = consumoApi.obtenerDatos(URL_BASE + nombreLibroCodificado);
        //System.out.println(json);
        DatosRespuesta datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class); // Deserializar la respuesta

        // Validar que existan resultados
        if (datosRespuesta.resultados() == null || datosRespuesta.resultados().isEmpty()) {
            throw new RuntimeException("No se encontraron libros con ese nombre.");
        }
        DatosLibro datos = datosRespuesta.resultados().get(0);
        return datos;

    }


    //@Transactional
    public void buscarLibroWeb() {
        try {
            // Obtener datos del libro desde la API
            DatosLibro datosLibro = getDatosLibroPorNombre();
            System.out.println(datosLibro);

            // Verificar si el libro ya existe en la BD por título (ignorando mayúsculas)
            if (libroRepository.findByTituloIgnoreCase(datosLibro.titulo()).isPresent()) {
                System.out.println("El libro ya está registrado");
                return;
            }

            // Obtener el primer autor desde los datos del libro
            DatosAutor datosAutor = datosLibro.autores().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún autor en los datos del libro"));

            // Buscar si el autor ya existe en la BD. Si no existe, se crea y persiste de inmediato.
            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.saveAndFlush(new Autor(datosAutor)));

            // Crear el objeto Libro a partir de los DatosLibro (sin asignar el autor aún)
            Libro nuevoLibro = new Libro(datosLibro);

            // Asignar el autor persistido (ya en estado managed) al libro
            nuevoLibro.setAutor(autor);

            // Guardar el nuevo libro en la base de datos
            libroRepository.save(nuevoLibro);

            System.out.println("Libro y autor registrados exitosamente.");
        } catch (RuntimeException e){
            System.out.println("No se ha encontrado un libro relacionado a ese nombre.");
        }
    }

    public void listarLibrosRegistrados() {
        System.out.println("Libros registrados:");
        libroRepository.findAll().forEach(System.out::println);
    }

    public void listarAutoresRegistrados() {
        System.out.println("Autores registrados:");
        autorRepository.findAll().forEach(System.out::println);
    }

    public void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        Scanner scanner = new Scanner(System.in);
        int anio = scanner.nextInt();

        List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(System.out::println);
        }
    }

    public void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar libros:");
        var opcionesIdiomas = """
                en - Inglés
                es - Español
                fr - Francés
                de - Alemán
                it - Italiano
                pt - Portugués
                """;
        System.out.println(opcionesIdiomas);
        Scanner scanner = new Scanner(System.in);
        String idioma = scanner.nextLine();

        List<Libro> librosPorIdioma = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            System.out.println("Libros en el idioma " + idioma + ":");
            librosPorIdioma.forEach(System.out::println);
        }
    }

    public void listarLibrosConDescargasMayores() {
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario el número mínimo de descargas
        System.out.println("Ingrese el número mínimo de descargas:");
        int minDescargas = scanner.nextInt();

        // Usar el método del repositorio para buscar los libros
        List<Libro> libros = libroRepository.findLibrosConDescargasMayores(minDescargas);

        // Mostrar los resultados
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con más de " + minDescargas + " descargas.");
        } else {
            System.out.println("Libros con más de " + minDescargas + " descargas:");
            libros.forEach(System.out::println);
        }
    }

    public void mostrarTop10LibrosMasDescargados() {
        System.out.println("Top 10 libros más descargados:");

        // Obtener los 10 libros más descargados
        List<Libro> librosTop10 = libroRepository.findTop10Libros();

        if (librosTop10.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            librosTop10.stream()
                    .limit(10) // Asegurarnos de que solo mostramos los primeros 10
                    .forEach(System.out::println);
        }
    }

    public void mostrarEstadisticasDescargas() {
        System.out.println("Estadísticas sobre descargas de libros:");

        // Obtener todas las descargas de los libros
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados para generar estadísticas.");
            return;
        }

        // Usar DoubleSummaryStatistics para calcular estadísticas
        DoubleSummaryStatistics estadisticas = libros.stream()
                .mapToDouble(Libro::getNumeroDeDescargas) // Extraer las descargas como double
                .summaryStatistics();

        // Mostrar las estadísticas
        System.out.println("Número total de libros: " + estadisticas.getCount());
        System.out.println("Total de descargas: " + (int) estadisticas.getSum());
        System.out.println("Promedio de descargas: " + (int) estadisticas.getAverage());
        System.out.println("Máximo de descargas: " + (int) estadisticas.getMax());
        System.out.println("Mínimo de descargas: " + (int) estadisticas.getMin());
    }

}
