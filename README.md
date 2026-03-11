# Red Comunitaria para Emprendimiento e Innovación

## Descripción

Este proyecto consiste en una plataforma web para gestionar y visualizar datos de emprendimientos e innovaciones a nivel global y local, con especial énfasis en la región de Colombia. Utilizando Spring Boot para el backend y Angular para el frontend, el sistema permite la gestión de usuarios, el análisis de datos mediante consultas SQL y la visualización interactiva de estos datos.

## Tecnologías Utilizadas

- **Backend**: Spring Boot, PostgreSQL, JPA (Java Persistence API)
- **Frontend**: Angular, Bootstrap
- **Base de Datos**: PostgreSQL

## Funcionalidades

### Login de Usuario
- Los usuarios pueden registrarse, iniciar sesión y acceder al dashboard.


### Gestión de Emprendimientos
- Los usuarios pueden crear, editar y eliminar registros de emprendimientos.
- Los emprendimientos están asociados a personas, ubicaciones y usuarios.

### Consultas SQL
- **Producción Total de Personas**: Obtiene la producción total de personas, agrupada por tipo de emprendimiento e innovación, y por región.
- **Porcentaje de Emprendimientos por Región**: Calcula el porcentaje de emprendimientos e innovaciones por región.
- **Top 10 Países con Mayor Emprendimiento o Innovación**: Devuelve los 10 países con más emprendimientos.
- **Filtrado de Emprendimientos**: Permite filtrar los emprendimientos por estado, tipo o región.

### Visualización de Datos
- Los resultados de las consultas SQL se visualizan tanto en tablas como en gráficos interactivos.

