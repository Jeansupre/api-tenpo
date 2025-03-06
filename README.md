# 📌 Proyecto Tenpo API

Este es un proyecto de API desarrollado con **Spring Boot 3.4.3** que expone endpoints básicos y utiliza **caché con Caffeine**, **registro de llamadas**, **OpenFeign para consumo de servicios externos** y **Swagger para documentación**.

---

[![Static Badge](https://img.shields.io/badge/Swagger-API%20Documentation-brightgreen)](http://localhost:8080/tenpo/swagger-ui/index.html#)

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Data JPA** (con PostgreSQL)
- **Spring Cache (Caffeine)**
- **Spring Cloud OpenFeign** (para consumo de APIs externas)
- **Spring Boot Actuator** (monitoreo de la aplicación)
- **Swagger (OpenAPI 3.0)** (para documentación de endpoints)
- **JUnit 5 y Mockito** (para pruebas unitarias)
- **Docker y Docker Compose** (para contenedorización)

---

## 📦 Instalación y configuración

### 1️⃣ **Clonar el repositorio**
```bash
git clone https://github.com/Jeansupre/tenpo-api.git
cd tenpo-api
```

### 2️⃣ **Ejecutar la aplicación con Docker Compose**
Si deseas ejecutar la aplicación junto con la base de datos y el servicio mock, usa:
```bash
docker-compose up -d --build
```
Esto levantará:
- La API con Spring Boot
- PostgreSQL
- Un mock de la API externa con WireMock

### 3️⃣ **Ejecutar la aplicación localmente (sin Docker)**
Si prefieres correr la aplicación en local, primero asegúrate de que tienes **PostgreSQL en ejecución**, luego usa:
```bash
mvn spring-boot:run
```

---

## 📖 Endpoints disponibles

Puedes acceder a la documentación Swagger desde:
```
http://localhost:8080/tenpo/swagger-ui.html
```

Algunos endpoints clave:

| Método  | URL | Descripción |
|---------|------------------------------|----------------|
| `GET`   | `/tenpo/api/v1/operaciones/suma` | Obtiene la suma de dos números con un porcentaje extra |
| `GET`   | `/tenpo/api/v1/historial` | Obtiene el historial de llamadas |

---

## 🛠️ Pruebas

Para ejecutar las pruebas unitarias y de integración:
```bash
mvn test
```
Si quieres ver más detalles del contexto en caso de errores:
```bash
mvn test -X
```

---

## 📌 Autor
👤 **Jean Carlo Rodriguez Sanchez**
📧 Contacto: [j.carlo0123@gmail.com](mailto:j.carlo0123@gmail.com)
📂 Repositorio: [GitHub](https://github.com/Jeansupre/api-tenpo)

¡Contribuciones y mejoras son bienvenidas! 🚀

