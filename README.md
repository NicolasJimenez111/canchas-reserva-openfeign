# Descripción de esta forma de comunicación

OpenFeign (también conocido como Feign) es una librería que permite crear **clientes HTTP declarativos** para simplificar la comunicación entre microservicios. En lugar de implementar llamadas HTTP manualmente (armar URLs, manejar headers, parsear respuestas, etc.), podemos definir un cliente mediante **interfaces** y **anotaciones**, reduciendo código repetitivo y mejorando la legibilidad.

# Cambios que se deben implementar en el proyecto

1. Dado que anteriormente se trabajaba con WebClient, se deben eliminar las **dependencias** relacionadas y agregar las necesarias para utilizar **Spring Cloud OpenFeign**.

2. En la clase principal del servicio de reservas se habilita Feign con `@EnableFeignClients` e indicamos el paquete donde están los clientes (por ejemplo `com.clubdeportivo2.servicioreservas.clients`). Así, las interfaces anotadas con `@FeignClient` se registran como beans y pueden inyectarse en el resto de la aplicación.

3. En el package `clients` se crea la interfaz `CanchaClient.java` con `@FeignClient`:
   - `name = "servicio-canchas"`: nombre lógico del cliente.
   - `url = "${canchas.service.url:http://localhost:8081}"`: toma la URL desde configuración (`canchas.service.url`) y usa `http://localhost:8081` como valor por defecto si no está definida.
   - `path = "/api/canchas"`: prefijo común para todos los endpoints.
   
   Con esto, la URL base queda como: `http://localhost:8081/api/canchas` (en entorno local por defecto).

4. Finalmente, se ajusta `ReservaServicesImpl.java` para consumir el servicio de canchas a través de la interfaz `CanchaClient`.

--- 

## Ejemplos de consumo de la API

### Servicio de Canchas (`localhost:8081`)

#### Crear canchas

**POST** `http://localhost:8081/api/canchas`  
Header: `Content-Type: application/json`

Body (ejemplo 1):
```json
{
  "nombre": "Cancha 1",
  "tipo": "Pasto sintético",
  "precioPorHora": 3000
}
```

Body (ejemplo 2):
```json
{
  "nombre": "Cancha 2",
  "tipo": "Pasto real",
  "precioPorHora": 7000
}
```

#### Consultar canchas

- **GET** `http://localhost:8081/api/canchas/2`
- **GET** `http://localhost:8081/api/canchas`

---

### Servicio de Reservas (`localhost:8082`)

#### Crear reservas

**POST** `http://localhost:8082/api/reservas`  
Header: `Content-Type: application/json`

Body (ejemplo 1):
```json
{
  "canchaId": 1,
  "nombreCliente": "Ema Miau",
  "fecha": "2026-03-13",
  "horaInicio": "10:00",
  "horaFin": "11:00"
}
```

Body (ejemplo 2):
```json
{
  "canchaId": 2,
  "nombreCliente": "Juno Miau",
  "fecha": "2026-03-13",
  "horaInicio": "11:30",
  "horaFin": "12:30"
}
```

Body (ejemplo 3):
```json
{
  "canchaId": 1,
  "nombreCliente": "Dante Miau",
  "fecha": "2026-03-13",
  "horaInicio": "13:00",
  "horaFin": "14:00"
}
```

#### Consultar reservas

- **GET** `http://localhost:8082/api/reservas/cancha/1`
- **GET** `http://localhost:8082/api/reservas`
