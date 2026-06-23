# Paryceos — Microservicios

Sistema de gestion de eventos sociales con 10 microservicios de negocio + autenticador JWT + API Gateway.

## Arquitectura

| Componente | Puerto | Base de datos |
|---|---|---|
| gateway | 8080 | — |
| ms-clientes | 8081 | paryceos_clientes |
| ms-trabajadores | 8082 | paryceos_trabajadores |
| ms-inventario | 8083 | paryceos_inventario |
| ms-pagos | 8084 | paryceos_pagos |
| ms-eventos | 8085 | paryceos_eventos |
| ms-proveedores | 8086 | paryceos_proveedores |
| ms-ubicaciones | 8087 | paryceos_ubicaciones |
| ms-menus | 8088 | paryceos_menus |
| ms-reservas | 8089 | paryceos_reservas |
| ms-resenas | 8090 | paryceos_resenas |
| ms-auth | 8091 | paryceos_auth |

## Autenticacion (JWT)
- `ms-auth` registra usuarios (contrasena encriptada con BCrypt) y genera un token JWT al hacer login.
- El `gateway` valida el token con un filtro (`AuthenticationFilter`) antes de dejar pasar la solicitud.
- La ruta `/auth/**` es publica; todas las rutas `/api/**` requieren el header `Authorization: Bearer <token>`.
- La clave secreta del token (`jwt.secret`) es la misma en `ms-auth` y en el `gateway`.

## Comunicacion entre microservicios (WebClient)
- ms-eventos → ms-clientes (valida cliente al crear evento)
- ms-eventos → ms-trabajadores (valida trabajador al crear evento)
- ms-pagos → ms-eventos (valida evento al registrar pago)
- ms-menus → ms-proveedores (valida proveedor al crear menu)
- ms-reservas → ms-eventos y ms-ubicaciones (valida ambos al crear reserva)
- ms-resenas → ms-clientes y ms-eventos (valida ambos al crear resena)

## Requisitos
- Java 21
- Maven 3.9+
- MySQL en localhost:3306, usuario root sin password
- Las BD se crean automaticamente

## Ejecucion (orden recomendado)
```bash
cd ms-auth && ./mvnw spring-boot:run
cd ms-clientes && ./mvnw spring-boot:run
cd ms-trabajadores && ./mvnw spring-boot:run
cd ms-inventario && ./mvnw spring-boot:run
cd ms-eventos && ./mvnw spring-boot:run
cd ms-pagos && ./mvnw spring-boot:run
cd ms-proveedores && ./mvnw spring-boot:run
cd ms-ubicaciones && ./mvnw spring-boot:run
cd ms-menus && ./mvnw spring-boot:run
cd ms-reservas && ./mvnw spring-boot:run
cd ms-resenas && ./mvnw spring-boot:run
cd gateway && ./mvnw spring-boot:run
```

## Como autenticarse (Postman)

### 1. Registrar usuario
POST http://localhost:8080/auth/registrar
```json
{
  "nombreUsuario": "admin",
  "contrasena": "1234",
  "correo": "admin@paryceos.cl",
  "rol": "ADMIN"
}
```

### 2. Login (devuelve el token JWT)
POST http://localhost:8080/auth/login
```json
{
  "nombreUsuario": "admin",
  "contrasena": "1234"
}
```
La respuesta es el token. En las demas peticiones agregar el header:
```
Authorization: Bearer <token>
```

## Endpoints (via Gateway en :8080)

Todas las rutas `/api/**` requieren el header Authorization con el token JWT.

| Recurso | Ruta base |
|---|---|
| Clientes | /api/clientes |
| Trabajadores | /api/trabajadores |
| Inventario | /api/inventario |
| Eventos | /api/eventos |
| Pagos | /api/pagos |
| Proveedores | /api/proveedores |
| Ubicaciones | /api/ubicaciones |
| Menus | /api/menus |
| Reservas | /api/reservas |
| Resenas | /api/resenas |

Cada recurso expone: GET (listar), GET /{id}, POST, PUT /{id}, DELETE /{id}.

## Datos de prueba (Postman)

### Cliente
POST http://localhost:8080/api/clientes
```json
{ "nombre": "Juan Perez", "telefono": "+56912345678", "correo": "juan@example.cl" }
```

### Trabajador
POST http://localhost:8080/api/trabajadores
```json
{ "nombre": "Maria Gonzalez", "rol": "DJ", "tarifaPorEvento": 150000 }
```

### Inventario
POST http://localhost:8080/api/inventario
```json
{ "articulo": "Mesa redonda", "stock": 20 }
```

### Evento (valida cliente + trabajador)
POST http://localhost:8080/api/eventos
```json
{ "tipoFiesta": "Matrimonio", "idCliente": 1, "idTrabajador": 1, "costoReserva": 850000 }
```

### Pago (valida evento)
POST http://localhost:8080/api/pagos
```json
{ "idEvento": 1, "montoTotal": 850000 }
```

### Proveedor
POST http://localhost:8080/api/proveedores
```json
{ "nombre": "Catering Gourmet", "tipoServicio": "Catering", "telefono": "+56922334455", "precioBase": 300000 }
```

### Ubicacion
POST http://localhost:8080/api/ubicaciones
```json
{ "nombre": "Salon Las Palmas", "direccion": "Av. Principal 123", "capacidad": 200, "precioArriendo": 500000 }
```

### Menu (valida proveedor)
POST http://localhost:8080/api/menus
```json
{ "nombre": "Menu Premium", "descripcion": "Entrada, plato de fondo y postre", "precioPorPersona": 18000, "idProveedor": 1 }
```

### Reserva (valida evento + ubicacion)
POST http://localhost:8080/api/reservas
```json
{ "idEvento": 1, "idUbicacion": 1, "fecha": "2025-12-20" }
```

### Resena (valida cliente + evento)
POST http://localhost:8080/api/resenas
```json
{ "idCliente": 1, "idEvento": 1, "puntuacion": 5, "comentario": "Excelente organizacion" }
```
