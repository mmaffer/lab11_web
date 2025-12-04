# SEMANA11 – Spring Boot CRUD con Seguridad, Exportación y AOP

Aplicación Spring Boot 3 (Java 17) con:
- CRUD de cursos (listar, crear, eliminar) con Thymeleaf.
- Seguridad con Spring Security y roles `ADMIN` y `USER`.
- Exportación de cursos a PDF y XLSX.
- Logging AOP para medir tiempos y registrar errores.
- Perfiles `dev` (H2 in-memory) y `mysql` (MySQL local) listos.

## Requisitos
- Java 17
- Maven Wrapper (`mvnw.cmd`)
- Opcional: MySQL 8.x (para perfil `mysql`)

## Ejecutar en desarrollo (H2)
Por defecto el perfil activo es `dev`.

```
./mvnw.cmd spring-boot:run
```

Accesos:
- App: `http://localhost:8080/`
- Login: usuarios de prueba
  - `admin/admin` (rol ADMIN y USER)
  - `user/user` (rol USER)
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

## Cambiar a MySQL
1. Crea la BD: `semana11`.
2. Ajusta credenciales en `src/main/resources/application-mysql.properties`.
3. Activa el perfil:
   - Edita `src/main/resources/application.properties` y usa `spring.profiles.active=mysql`.
4. Ejecuta:
   ```
   ./mvnw.cmd spring-boot:run
   ```

## Exportación
- PDF: `GET /cursos/export/pdf` → descarga `cursos.pdf`.
- XLSX: `GET /cursos/export/xls` → descarga `cursos.xlsx`.

## Logging AOP
`pe.edu.tecsup.s09.aop.LoggingAspecto` registra:
- `[INICIO] Clase.metodo(..)`
- `[FIN] Clase.metodo(..) (xx ms)`
- `[ERROR] Clase.metodo(..) (xx ms) - mensaje`

## Estructura relevante
- Controlador: `pe.edu.tecsup.s09.web.CursoController`
- Servicio: `pe.edu.tecsup.s09.services.CursoServiceImpl`
- Entidades: `pe.edu.tecsup.s09.entities.*`
- Vistas: `src/main/resources/templates/*`
- Estáticos: `src/main/resources/static/*`

## Conclusiones del laboratorio
1. Integrar Spring Security con Thymeleaf es directo y potente: las vistas pueden mostrar/ocultar acciones según roles con `thymeleaf-extras-springsecurity6`, simplificando el control de permisos sin lógica compleja en la UI.
2. Trabajar con perfiles (`dev` y `mysql`) permite alternar entre H2 y MySQL sin cambiar código, acelerando el desarrollo y facilitando despliegues; la clave es aislar configuración por entorno.
3. Exportar datos (PDF/XLSX) se integra fácilmente escribiendo directamente en la respuesta HTTP; OpenPDF y Apache POI cubren la mayoría de casos de reporte sin necesidad de herramientas pesadas.
4. El uso de AOP para logging transversal mejora la observabilidad sin ensuciar controladores y servicios; medir tiempos ayuda a identificar cuellos de botella tempranos.
5. Mantener una base de vistas consistente con un layout sencillo evita errores de renderizado y acelera cambios de UI; pequeñas reglas CSS (fondo y contraste) evitan problemas de visualización según el tema del visor.

---
Si deseas que deje activo `mysql` por defecto o agregar más campos/validaciones al export, avísame y lo ajusto."# lab11_web" 
