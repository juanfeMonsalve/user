# User Service

Este proyecto es un servicio de usuarios construido con **Java 11**, **Spring Boot 2.5.14** y **Gradle 7.4**.  
Se recomienda abrirlo en **IntelliJ IDEA**, ya que indexa sin necesidad de configuración adicional.

---

## Tecnologías utilizadas
- Java 11
- Spring Boot 2.5.14
- Gradle 7.4
- H2 Database (en memoria, no requiere queries manuales)

---

## Configuración y ejecución

1. Tener configurado **Java 11** en el sistema.
2. Si no se usa IntelliJ, configurar **Gradle 7.4**.
3. Ejecutar la clase principal:
   ```bash
   UserApplication

y presionar Run.

El servicio se levanta en:

http://localhost:8080

**Funcionalidades principales**

Registro y autenticación de usuarios con JWT.

Roles de usuario definidos en un enum (USER, ADMIN).

Manejo de excepciones personalizado mediante un HandlerException.

CRUD de usuarios protegido con autenticación.

Con más tiempo podría añadirse validación de APIs según el rol (autorización por roles).

Cobertura SonarQube
La cobertura de SonarQube alcanza aproximadamente el 100%,
excluyendo los paquetes de configuración y DTO.

No se logró configurar completamente por falta de tiempo.

Ejemplos con cURL
1. Registro de usuario

curl --location 'localhost:8080/api/auth/sing-up' \
--header 'Content-Type: application/json' \
--data-raw '{

    "email": "Jhon@gmail.com",
    "password": "a2asfGfdfdf4",
    "phones": [
        {
            "number": 31254665,
            "citycode": 1,
            "contrycode": "COL"
        }
    ]
}'

2. Login

curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "Jhon@gmail.com",
"password": "a2asfGfdfdf4"
}'
3. Obtener todos los usuarios

   curl --location 'http://localhost:8080/api/user/' \
   --header 'Authorization: Bearer <TOKEN>'
4. Actualizar un usuario (PUT)

   curl --location --request PUT 'http://localhost:8080/api/user/1' \
   --header 'Authorization: Bearer <TOKEN>' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username": "juan_actualizado",
   "email": "juan_updated@example.com",
   "password": "nuevaClave123"
   }'
5. Obtener un usuario por ID

   curl --location 'http://localhost:8080/api/user/1' \
   --header 'Authorization: Bearer <TOKEN>'
6. Eliminar un usuario

   curl --location --request DELETE 'http://localhost:8080/api/user/1' \
   --header 'Authorization: Bearer <TOKEN>'
   Notas adicionales
   La base de datos H2 se ejecuta en memoria, por lo que no es necesario correr scripts SQL.

El token JWT devuelto en el login debe ser utilizado en los endpoints protegidos.