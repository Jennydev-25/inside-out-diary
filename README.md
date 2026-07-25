# 📔 Inside Out Diary – Diario de Emociones en Java

> No hay emoción de más: cada recuerdo merece su lugar.

Aplicación de consola en **Java 21** con **Maven** para registrar los **momentos vividos** y la **emoción** que los acompaña, inspirada en _Inside Out_. Desarrollada siguiendo **TDD** (JUnit 5 + Hamcrest), con persistencia en memoria mediante `Map` y cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Enunciado](#-enunciado)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

**Inside Out Diary** es una aplicación de consola que funciona como un diario personal de emociones. El usuario puede registrar cada momento vivido junto con la emoción que sintió y la fecha en que ocurrió, para luego repasarlo, filtrarlo por emoción o por mes, y eliminarlo.

El proyecto pone en práctica:

- **Persistencia en memoria** con la interfaz `Map`.
- **Arquitectura por capas** con separación de responsabilidades (la _S_ de SOLID): modelo, repositorio, servicio (lógica) y presentación por consola.
- **TDD** (Red-Green-Refactor) sobre la lógica, con una cobertura mínima del 70 %.

---

## 📝 Enunciado

Crear una aplicación de consola, _Mi Diario_, con la que el usuario pueda gestionar los momentos vividos. Cada momento tiene una **emoción** asignada y la **fecha** en la que ocurrió.

Cada momento vivido tiene: identificador, título, descripción, emoción, fecha del momento, fecha de creación y fecha de modificación.

### Emociones disponibles

1. Alegría
2. Tristeza
3. Ira
4. Asco
5. Miedo
6. Ansiedad
7. Envidia
8. Vergüenza
9. Aburrimiento
10. Nostalgia

### Historias de usuario

1. **COMO** usuario **QUIERO** añadir un momento vivido **PARA** poder recordarlo cuando lo necesite.
2. **COMO** usuario **QUIERO** recuperar la lista de momentos registrados **PARA** poder repasarlos.
3. **COMO** usuario **QUIERO** eliminar un momento vivido **PARA** evitar duplicados y mantener la lista organizada.
4. **COMO** usuario **QUIERO** obtener los momentos según su emoción **PARA** poder visualizarlos.
5. **COMO** usuario **QUIERO** obtener los momentos vividos en un mes determinado.
6. **COMO** usuario **QUIERO** salir del programa.

---

## 🛠️ Tecnologías

- **[Java 21](https://www.oracle.com/java/technologies/downloads/)** — Lenguaje de programación del proyecto
- **[Apache Maven](https://maven.apache.org/)** — Gestor de dependencias y construcción del proyecto
- **[JUnit 5](https://junit.org/junit5/)** — Framework de tests unitarios
- **[Hamcrest](https://hamcrest.org/JavaHamcrest/)** — Librería de _matchers_ para aserciones legibles (`assertThat`)
- **[JaCoCo](https://www.jacoco.org/jacoco/)** — Medición de la cobertura de tests
- **[Visual Studio Code](https://code.visualstudio.com/)** — Editor usado para desarrollar y gestionar el proyecto
- **[Markdown](https://www.markdownguide.org/)** — Lenguaje de marcado para el README
- **[Git](https://git-scm.com/)** / **[GitHub](https://github.com/)** — Control de versiones y alojamiento del proyecto

---

## 👩‍💻 Autora

**[Jenny Sánchez Requejo](https://github.com/Jennydev-25)**
