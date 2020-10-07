#Microservicio ApiStream

El microservicio fue hecho con spring boot, y su respectivo repositorio esta ubicado en git@github.com:gimnasialp/streamApi.git  o bien por https://github.com/gimnasialp/streamApi.git .
Para la persistencia de informacion, se utilizo la base de datos H2, ya que la misma es bantante liviana y es de utilidad para agilizar el proceso de desarrollo.

##1)Aclaracion:

Para el microservicio se uso la libreria lombok, asi que segun el IDE que se utilice, previamente se debe instalar tal libreria.
Para agilidad, solo se trabajar con horarios TIME. La aplicacion usara datos tipos Date pero de ellos solo se usara las horas de la misma.
Para la devolucion de datos, se usara un objeto BitcoinDTO. En caso de error o para casos de datos no encontrados, se usara un
objeto Exception personal para, pero desde la aplicacion de mensajeria(Postman por ej.) se mostrar un error 500 Internal Server Error.

##2)Ejecucion:

No hay configuracion hecha a base de container o dockerizacion, solo se debe agregar la configuracion tipica "Run COnfigurations" del IDE a usar.

##3)Utilizacion:

Los tres endpoints

a)Importante!  Iniciacion de tareas de consulta y persistencia interna.

endpoint: localhost:{port}/

De esta manera, a partir del momento en que se consulta el endpoint, comienza la consulta periodica de 10seg al servicio https://cex.io/api/last_price/BTC/USD
y la posterior persistencia a la base H2 montada en memoria.

Sin la ejecucion de este endpoint, no habra datos en la DB.

b)Consulta de Bitcoin por horario.

endpoint: localhost:{port}/streamApi/bitcoin/price/time/{time}

time en formato hh:mm:ss

Response(Ejemplo): 
```json
{
  "createAt": "18:04:02",
  "price": "20000,40"
}
```

c)Para un intervalo de horas(firstime y secondtime), se toma todos los bitcoins obtenidos y se calcula el precio promedio y la diferencia diferencia porcentual entre ese valor promedio y el valor máximo.

firstime y secondtime en formato hh:mm:ss

endpoint: localhost:8080/streamApi/bitcoin/values/firstime/{firstime}/secondtime/{secondtime}

Response(Ejemplo): 
```json
{
    "average": 10815.6,
    "percentageDifference": "0.42"
}
```
##4) Documentacion tecnica (Swagger)

endpoint : localhost:{port}/swagger-ui.html
