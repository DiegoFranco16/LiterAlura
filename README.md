# Literalura

![Java](https://img.shields.io/badge/Java%2017-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Status](https://img.shields.io/badge/Status-En%20Desarrollo-yellow?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

## Índice

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Funcionalidades](#funcionalidades)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Uso](#uso)
- [Contribuyentes](#contribuyentes)
- [Licencia](#licencia)

---

## Descripción del Proyecto

**Literalura** es una aplicación de consola desarrollada en Java que permite gestionar información literaria mediante el registro y consulta de libros y autores. Integra operaciones avanzadas para la consulta de datos y ofrece herramientas estadísticas para analizar tendencias literarias.

El proyecto utiliza **Spring Framework** para una arquitectura robusta, **Hibernate** para el manejo de persistencia y relaciones, y **PostgreSQL** como base de datos principal.

---

## Funcionalidades

- **Buscar libro por título**: Consulta la API externa de [Gutendex](https://gutendex.com/), guarda la información del libro y su autor en la base de datos.
- **Listar libros registrados**: Recupera y muestra todos los libros almacenados en la base de datos.
- **Listar autores registrados**: Muestra los autores registrados junto con los libros asociados.
- **Listar autores vivos en un determinado año**: Filtra y muestra autores que estaban vivos en un año específico.
- **Listar libros por idioma**: Muestra libros según su idioma.
- **Listar libros con descargas mayores a un número**: Filtra libros según su popularidad.
- **Top 10 libros más descargados**: Presenta los diez libros más populares según el número de descargas.
- **Mostrar estadísticas de descargas**: Proporciona estadísticas detalladas (suma, promedio, máximo, mínimo) sobre las descargas.

---

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal en su versión 17.
- **Spring Framework**: Para la gestión de dependencias y configuración de la aplicación.
- **Spring Data JPA**: Para interacción con la base de datos.
- **Hibernate**: Framework ORM para manejo de persistencia y relaciones.
- **PostgreSQL**: Sistema de gestión de bases de datos.
- **Maven**: Herramienta de gestión de proyectos y dependencias.

---

## Instalación y Ejecución

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu_usuario/literalura.git
   ```

2. **Configurar la base de datos**:
   - Asegúrate de tener PostgreSQL instalado y configura una base de datos llamada `literatura`.
   - Crea un archivo `application.properties` en `src/main/resources/` con las siguientes propiedades:
     ```properties
     spring.datasource.url=jdbc:postgresql://${DB_HOST}/literatura
      spring.datasource.username=${DB_USER}
      spring.datasource.password=${DB_PASSWORD}
      spring.datasource.driver-class-name=org.postgresql.Driver
      hibernate.dialect=org.hibernate.dialect.HSQLDialect

      spring.jpa.hibernate.ddl-auto=update
     ```
   - Recuerda crear las variables de entorno `DB_HOST` (por lo general es localhost), `DB_USER` y `DB_PASSWORD`
3. **Compilar y ejecutar la aplicación**:
  Luego de clonar el repositorio y configurar las variables de entorno, se puede abrir el proyecto en [IntelliJ IDEA](https://www.jetbrains.com/idea/) y ejecutar sin inconveniente.

---

## Uso

Al ejecutar la aplicación, se mostrará un menú interactivo en la consola:

```
_____________________________________________________
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
6 - Listar libros con descargas mayores a un número
7 - Top 10 libros más descargados
8 - Mostrar estadísticas de descargas

0 - Salir
_____________________________________________________
```

Selecciona la opción deseada ingresando el número correspondiente y sigue las instrucciones.

---

## Contribuyentes

- [Diego Alejandro Franco Alvarez](https://www.linkedin.com/in/diego-alejandro-franco-alvarez/) - Desarrollador principal

---

## Licencia

Este proyecto está bajo la Licencia MIT.
