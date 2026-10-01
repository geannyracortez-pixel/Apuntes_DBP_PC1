# EventPass UTEC — Simulacro PC1 (Desarrollo Basado en Plataformas)

API REST para administrar **eventos**, **tipos de entrada** e **inscripciones**.
Java 17+ · Spring Boot · Maven · H2 · Spring Security + JWT · Lombok.

## Cómo ejecutarlo

1. Abrir esta carpeta en IntelliJ (File → Open → elegir la carpeta que contiene `pom.xml`).
2. Esperar a que Maven descargue las dependencias.
3. Ejecutar `EventpassApplication` (botón ▶ verde).
   - O en terminal: `mvn spring-boot:run`
4. La API queda en `http://localhost:8080`.
5. Importar en Postman `postman/EventPass.postman_collection.json` y ejecutar las peticiones en orden.

> H2 está en memoria: al apagar la app se borran los datos.

## Mapa del proyecto

```
src/main/java/com/utec/eventpass/
├── EventpassApplication.java   ← punto de arranque
├── entity/        ← tablas de la BD (User, Event, TicketType, Registration, Role)
├── repository/    ← acceso a BD con Spring Data JPA
├── dto/           ← lo que entra (Request) y sale (Response) por la API
├── service/       ← lógica de negocio y validaciones de reglas
├── controller/    ← endpoints HTTP (reciben, delegan al service, responden)
├── exception/     ← excepciones propias + GlobalExceptionHandler
└── security/      ← SecurityConfig, JwtService, JwtAuthenticationFilter, CustomUserDetailsService
```

Flujo de una petición:

```
Postman ─► JwtAuthenticationFilter ─► Controller ─► Service ─► Repository ─► H2
                                         ▲             │
                                         └── DTO ◄─────┘   (errores → GlobalExceptionHandler)
```

## Endpoints

| Método | Ruta | Acceso | Respuesta OK |
|---|---|---|---|
| POST | `/auth/register` | público | 201 + token |
| POST | `/auth/login` | público | 200 + token |
| GET | `/events` | público | 200 |
| GET | `/events/{id}` | público | 200 |
| POST | `/events` | ADMIN | 201 |
| POST | `/events/{eventId}/ticket-types` | ADMIN | 201 |
| POST | `/registrations` | autenticado | 201 |
| GET | `/registrations/me` | autenticado | 200 |

Errores: 400 (validación), 401 (sin token / token inválido / login incorrecto),
403 (USER intentando acción de ADMIN), 404 (no existe), 409 (duplicado o sin entradas).
