La aplicacion ha sido creada con Springboot 3 y Java 17
Se usa base de datos h2
Se exponen dos apis para pruebas (Se adjunta por correo en collecion de postman)
Se realizan 2 pruebas unitarias pero quedan faltando

Se debe agregar a variables de entorno passworExpresion=^[0-9]+$ en este caso se esta validando que la contraseña sea solo numeros
Se esta ejecutando en el puerto 8080
http://localhost:8080/api/swagger-ui/index.html
Esta es la url de swagger en caso de cambiar el puerto aqui tambien se debe cambiar

Se agrega funcionalidad Jwt se debe primero crear el usuario, luego obtener el token y con este token pegarlo en el authorization de postman para el get de usuarios y retorna el valor 
Se envia nuevo postman para que funcione
