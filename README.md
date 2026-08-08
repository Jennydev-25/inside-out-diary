# 📔 Inside Out Diary – Diario de Emociones en Java

> No hay emoción de más: cada recuerdo merece su lugar.

Aplicación de consola en **Java 21** con **Maven** para registrar los **momentos vividos** y la **emoción** que los acompaña, inspirada en _Inside Out_. Desarrollada siguiendo **TDD** (JUnit 5 + Hamcrest), con persistencia en memoria mediante `Map` y cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Enunciado](#-enunciado)
- [Cómo reproducir el proyecto](#-cómo-reproducir-el-proyecto)
- [Historias de usuario y criterios de aceptación](#-historias-de-usuario-y-criterios-de-aceptación)
  - [Refinamiento](#-refinamiento-historias-adicionales)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

**Inside Out Diary** es una aplicación de consola que funciona como un diario personal de emociones, protegido por contraseña. El usuario puede registrar cada momento vivido junto con la emoción que sintió y la fecha en que ocurrió, para después repasarlo, modificarlo, filtrarlo por emoción, mes o fecha exacta, exportarlo a CSV, o eliminarlo.

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

El enunciado incluye además **seis historias de usuario** (correspondientes a las **HU-01 a HU-06**) y, posteriormente, un **refinamiento** con **tres historias adicionales** (**HU-07 a HU-09**), dadas sin sus criterios de aceptación. Parte del ejercicio consiste precisamente en **redactar los criterios de aceptación y los escenarios** de cada historia, que se recogen en el apartado [Historias de usuario y criterios de aceptación](#-historias-de-usuario-y-criterios-de-aceptación).

**Requisitos de entrega:**

- Persistencia de datos en memoria mediante la interfaz `Map`.
- Código estructurado por capas, con correcta separación de responsabilidades (la _S_ de SOLID).
- Tests con una cobertura mínima del **70 %**, incluyendo la captura del informe de cobertura en el README.
- Tres diagramas: **diagrama UML de casos de uso**, **diagrama de secuencia** y **diagrama UML de clases**.
- README debidamente trabajado (descripción, pre-requisitos, pasos de instalación, ejecución de tests).

---

## 🚀 Cómo reproducir el proyecto

### Requisitos previos

- **[JDK 21](https://www.oracle.com/java/technologies/downloads/)** instalado — [guía de instalación](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html)
- **[Apache Maven](https://maven.apache.org/download.cgi)** instalado y en el `PATH` — [guía de instalación](https://maven.apache.org/install.html)
- **[Git](https://git-scm.com/downloads)** para clonar el repositorio — [guía de instalación](https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalaci%C3%B3n-de-Git)

### Pasos

**1. Comprueba que tienes Java y Maven instalados** (si algún comando no se reconoce, instálalo desde los enlaces de _Requisitos previos_):

```bash
java --version
mvn --version
```

**2. Clona el repositorio:**

```bash
git clone https://github.com/Jennydev-25/inside-out-diary.git
```

**3. Entra en la carpeta del proyecto:**

```bash
cd inside-out-diary
```

**4. Ejecuta los tests** (compila y genera el reporte de cobertura de JaCoCo):

```bash
mvn test
```

El reporte de cobertura se genera en `target/site/jacoco/index.html`, que puedes abrir en el navegador.

**5. Ejecuta la aplicación:**

```bash
mvn exec:java
```

La aplicación pide una contraseña antes de dar acceso al diario. Usa `diary2026` por defecto, o el valor que configures en la variable de entorno `DIARY_PASSWORD`. **Solo hay tres intentos antes de que la aplicación se cierre.**

---

## 👤 Historias de usuario y criterios de aceptación

Estas son las historias de usuario del proyecto con sus criterios de aceptación. Las **HU-01 a HU-06** corresponden al enunciado base y las **HU-07 a HU-09** al refinamiento. Cada historia recoge sus escenarios en formato Gherkin (**Dado / Cuando / Entonces**), desplegables en «Criterios de aceptación».

### HU-01 — Añadir un momento vivido

- **Como** usuario
- **Quiero** añadir un momento vivido con su título, descripción, emoción y fecha
- **Para** poder recordarlo cuando lo necesite

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Añadir un momento con datos válidos**
  - **Dado** que estoy en el menú principal
  - **Cuando** selecciono "Añadir momento" e introduzco un título, una fecha válida (dd/mm/aaaa), una descripción y una emoción del 1 al 10
  - **Entonces** el momento se guarda con un identificador único y sus fechas de creación y modificación, y se muestra "Momento vivído añadido correctamente."
- **Escenario 2: Fecha con formato inválido**
  - **Dado** que estoy añadiendo un momento
  - **Cuando** introduzco la fecha en un formato distinto de dd/mm/aaaa
  - **Entonces** el momento no se guarda y se muestra un mensaje de error indicando el formato correcto
- **Escenario 3: Emoción fuera de rango**
  - **Dado** que estoy añadiendo un momento
  - **Cuando** selecciono una emoción que no está entre 1 y 10
  - **Entonces** el momento no se guarda y se muestra un mensaje de error

</details>

### HU-02 — Ver todos los momentos

- **Como** usuario
- **Quiero** recuperar la lista de los momentos registrados
- **Para** poder repasarlos

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Listar momentos existentes**
  - **Dado** que hay al menos un momento registrado
  - **Cuando** selecciono "Ver todos los momentos disponibles"
  - **Entonces** se muestra la lista con identificador, fecha, título, descripción y emoción de cada momento
- **Escenario 2: Listar cuando no hay momentos**
  - **Dado** que no hay ningún momento registrado
  - **Cuando** selecciono "Ver todos los momentos disponibles"
  - **Entonces** se muestra un mensaje indicando que todavía no hay momentos registrados

</details>

### HU-03 — Eliminar un momento

- **Como** usuario
- **Quiero** suprimir un momento vivido
- **Para** evitar duplicados y mantener la lista organizada

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Eliminar un momento existente**
  - **Dado** que existe un momento con identificador 1
  - **Cuando** selecciono "Eliminar un momento" e introduzco el identificador 1
  - **Entonces** el momento se elimina y se muestra "Momento vivído eliminado correctamente."
- **Escenario 2: Eliminar un identificador inexistente**
  - **Dado** que no existe ningún momento con identificador 99
  - **Cuando** introduzco el identificador 99 para eliminar
  - **Entonces** no se elimina nada y se muestra un mensaje indicando que no existe un momento con ese identificador
- **Escenario 3: Identificador no numérico**
  - **Dado** que estoy eliminando un momento
  - **Cuando** introduzco un identificador que no es un número
  - **Entonces** se muestra un mensaje de error y no se elimina nada

</details>

### HU-04 — Filtrar por emoción

- **Como** usuario
- **Quiero** obtener los momentos vividos según su emoción
- **Para** poder visualizarlos agrupados por lo que sentí

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Filtrar por una emoción con resultados**
  - **Dado** que existen momentos con la emoción "Alegría"
  - **Cuando** selecciono "Filtrar los momentos", elijo filtrar por emoción y selecciono "Alegría"
  - **Entonces** se muestran únicamente los momentos con emoción Alegría
- **Escenario 2: Filtrar por una emoción sin resultados**
  - **Dado** que no existe ningún momento con la emoción "Envidia"
  - **Cuando** filtro por la emoción "Envidia"
  - **Entonces** se muestra un mensaje indicando que no hay momentos con esa emoción
- **Escenario 3: Emoción fuera de rango**
  - **Dado** que estoy filtrando por emoción
  - **Cuando** selecciono una opción que no está entre 1 y 10
  - **Entonces** se muestra un mensaje de error

</details>

### HU-05 — Filtrar por mes

- **Como** usuario
- **Quiero** obtener los momentos vividos en un mes determinado
- **Para** poder repasar lo que viví ese mes

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Filtrar por un mes con resultados**
  - **Dado** que existen momentos con fecha en mayo de 2024
  - **Cuando** selecciono "Filtrar los momentos", elijo filtrar por fecha e indico el mes "05/2024"
  - **Entonces** se muestran únicamente los momentos ocurridos en mayo de 2024
- **Escenario 2: Filtrar por un mes sin resultados**
  - **Dado** que no existe ningún momento en el mes indicado
  - **Cuando** filtro por el mes "01/2020"
  - **Entonces** se muestra un mensaje indicando que no hay momentos en ese mes
- **Escenario 3: Mes inválido**
  - **Dado** que estoy filtrando por mes
  - **Cuando** introduzco un mes fuera de 1–12 o con formato incorrecto
  - **Entonces** se muestra un mensaje de error

</details>

### HU-06 — Salir del programa

- **Como** usuario
- **Quiero** salir del programa
- **Para** poder cerrarlo o iniciar otro

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Salir del programa**
  - **Dado** que estoy en el menú principal
  - **Cuando** selecciono "Salir"
  - **Entonces** se muestra "Hasta la próxima!!!" y el programa termina de forma ordenada

</details>

---

### ✨ Refinamiento (historias adicionales)

Historias planteadas en la fase de **refinamiento** del proyecto; siguen el mismo formato de criterios de aceptación y escenarios.

### HU-07 — Modificar un momento vivido

- **Como** usuario
- **Quiero** modificar cualquier dato de un momento vivido (título, descripción, emoción o fecha del momento)
- **Para** mantener mi diario actualizado cuando necesite corregir o ampliar información

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Modificar la emoción de un momento existente**
  - **Dado** que existe un momento con identificador 1 y emoción "Tristeza"
  - **Cuando** selecciono modificar el momento 1 y cambio la emoción a "Alegría"
  - **Entonces** el momento pasa a tener emoción Alegría y se actualiza su fecha de modificación, manteniendo su identificador y su fecha de creación
- **Escenario 2: Modificar un momento inexistente**
  - **Dado** que no existe ningún momento con identificador 50
  - **Cuando** intento modificar el momento 50
  - **Entonces** no se modifica nada y se muestra un mensaje indicando que no existe ese momento

</details>

### HU-08 — Exportar los momentos a CSV

- **Como** usuario
- **Quiero** generar la lista completa de momentos vividos en un archivo CSV
- **Para** guardar, compartir o analizar mis recuerdos fuera de la aplicación

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Exportar momentos a CSV**
  - **Dado** que hay momentos registrados
  - **Cuando** selecciono la opción de exportar a CSV
  - **Entonces** se genera un archivo .csv con una fila de cabecera y una fila por momento, y se muestra la ruta del archivo generado
- **Escenario 2: Exportar cuando no hay momentos**
  - **Dado** que no hay ningún momento registrado
  - **Cuando** selecciono la opción de exportar a CSV
  - **Entonces** se muestra un mensaje indicando que no hay momentos que exportar

</details>

### HU-09 — Acceso protegido por contraseña

- **Como** usuario
- **Quiero** acceder a mi diario únicamente mediante una contraseña
- **Para** asegurar que mis momentos estén protegidos y solo yo pueda consultarlos

<details>
<summary>Criterios de aceptación</summary>

- **Escenario 1: Acceso con contraseña correcta**
  - **Dado** que la aplicación está protegida por contraseña
  - **Cuando** introduzco la contraseña correcta
  - **Entonces** accedo al menú principal del diario
- **Escenario 2: Acceso con contraseña incorrecta**
  - **Dado** que la aplicación está protegida por contraseña
  - **Cuando** introduzco una contraseña incorrecta
  - **Entonces** se me deniega el acceso y se me permite reintentar hasta agotar el número máximo de intentos
- **Escenario 3: Denegación definitiva por intentos agotados**
- **Dado** que he agotado los intentos permitidos
- **Cuando** intento acceder de nuevo
- **Entonces** se me deniega el acceso y la aplicación se cierra

</details>

---

## 🛠️ Tecnologías

- **[Java 21](https://www.oracle.com/java/technologies/downloads/)** — Lenguaje de programación del proyecto
- **[Apache Maven](https://maven.apache.org/)** — Gestor de dependencias y construcción del proyecto
- **[JUnit 5](https://junit.org/junit5/)** — Framework de tests unitarios
- **[Hamcrest](https://hamcrest.org/JavaHamcrest/)** — Librería de _matchers_ para aserciones legibles (`assertThat`)
- **[JaCoCo](https://www.jacoco.org/jacoco/)** — Medición de la cobertura de tests
- **[Mockito](https://site.mockito.org/)** — Mockeo de dependencias estáticas en los tests de vistas y singletons
- **[Visual Studio Code](https://code.visualstudio.com/)** — Editor usado para desarrollar y gestionar el proyecto
- **[Markdown](https://www.markdownguide.org/)** — Lenguaje de marcado para el README
- **[Git](https://git-scm.com/)** / **[GitHub](https://github.com/)** — Control de versiones y alojamiento del proyecto

---

## 👩‍💻 Autora

**[Jenny Sánchez Requejo](https://github.com/Jennydev-25)**
