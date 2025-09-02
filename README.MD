Se realiza la solucion solicitada

Se trabaja con Java 11, springboot 2.5.14 y gradle 7.4

Se recomienda abrir con intellij ya que este indexa si necesidad de configurar

La base de datos es la h2

no requiere correr querys

el sonar queda aproximadamente a un 100% quitando los configs y dto
no se logra configurar por falta de tiempo

se usa handler exception para manejo de excepciones personalizadamente

Se agrega un enum de Roles el cual puede ser USER o ADMIN
con mas logica le agregaria validaciones de apis con authorizacion segun el role


Para su ejecucion solo es configurar el java, el gradle en caso de no ser intellij
y ir a UserApplication y darle en el boton de run

Se adjuntan los curl en el correo