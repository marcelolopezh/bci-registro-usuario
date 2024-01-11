# Registro Usuario API

![Diagrama flujo de datos](https://raw.githubusercontent.com/marcelolopezh/bci-registro-usuario/539973188d10d71e6408e1bf8b49e05e545d977b/Diagrama%20Flujo%20Datos.png)

### Características 

- Registro de Usuario
- Autenticación con JWT
- Modificación de Registros
- Eliminación de Registros
- Obtención de Registros


### Configuración del proyecto

En el archivo application.properties está la configuración para la base de datos, en este caso se ha utilizado una base de datos en memoria H2. Configurar según necesidad (user, pass, show-sql).

```properties
spring.datasource.url=jdbc:h2:mem:registrousuario
spring.h2.console.enabled=true
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=user
spring.datasource.password=userpass
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Registro de Usuario
Method : POST

Url : http://localhost:8080/usuario/

Payload : 

```json
{
    "nombre" : "Marcelo",
    "correo" : "marcelolopezhernandez2@gmail.com",
    "contrasena" : "aSdW10119!",
    "telefonos" : [
        {
            "numero" : "993677499",
            "codigo" : "1",
            "pais" : "4"
        },
        {
            "numero" : "993675215",
            "codigo" : "51",
            "pais" : "101"
        }
    ]
}
```
Response OK : 
```json
{
    "usuario": {
        "id": 4,
        "nombre": "Marcelo",
        "correo": "marcelolopezhernandez2@gmail.com",
        "contrasena": "$2a$10$yor88o32It40ByOsqGSqYuTizbQxJE2ClxRFK9xZV871E0jRXgU82",
        "token": null,
        "activo": false,
        "telefonos": [
            {
                "id": 5,
                "numero": "993677499",
                "codigoCiudad": null,
                "codigoPais": null
            },
            {
                "id": 6,
                "numero": "993675215",
                "codigoCiudad": null,
                "codigoPais": null
            }
        ],
        "creado": "2024-01-11T04:20:01.308+00:00",
        "modificado": "2024-01-11T04:20:01.308+00:00",
        "ultimoLogin": null
    },
    "mensaje": "Usuario creado correctamente"
}
```

### Login - Obtención y uso JWT

Al estar registrados dentro de la aplicación, es posible realizar un ingreso de sesión para acceder a todas las operaciones que se puedan realizar y que requieran autenticación.

Method : POST

URL : http://localhost:8080/login

Payload : 

```json
{
    "correo": "marcelolopezhernandez2@gmail.com",
    "contrasena": "aSdW10119!"
}
```

Response OK :

```json

{
    "usuario": {
        "id": 4,
        "creado": "2024-01-11 01:20:01.308",
        "modificado": "Thu Jan 11 01:25:32 CLST 2024",
        "ultimoLogin": "2024-01-11 01:25:32.812",
        "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI4ODE3OGE0ZS0wMWMyLTQ2YmYtOGFhNC02MjMxOWJlNmM0YmMiLCJzdWIiOiI0IDIwMjQtMDEtMTEgMDE6MjU6MzIuODEyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTcwNDk0NzEzMiwiZXhwIjoxNzA0OTQ3NDMyfQ.6m-Mr51dsTS5L-awxq0vn5nrvxYhmvbWWjCHi2b85rYqohNMJq2Ndz7sLrCJZFZXJje3qwXR18VMJK-eXwYWJw",
        "activo": true
    }
}
```

Se debe utilizar el token que responder el endpoint de inicio de sesión y colocar el atributo "Authorization" dentro del header del request.


### Obtención de Usuarios
Method : GET
URL : http://localhost:8080/usuarios/

Response 403 : 

```json
{
    "timestamp": "2024-01-11T04:42:00.533+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/usuarios/"
}
```

Response OK (Utilizando token válido) : 
```json
[
    {
        "id": 1,
        "nombre": "Marcelo",
        "correo": "marcelolopezhernandez1@gmail.com",
        "contrasena": "$2a$10$yA1dYRwA8SURVzTD2X5Y3.qGERs1yWSMs/yyo293VcCu6HhJdm4rm",
        "token": null,
        "activo": false,
        "telefonos": [
            {
                "id": 1,
                "numero": "993677499",
                "codigoCiudad": 1,
                "codigoPais": 4
            },
            {
                "id": 2,
                "numero": "993675215",
                "codigoCiudad": 51,
                "codigoPais": 101
            }
        ],
        "creado": "2024-01-11T04:45:18.501+00:00",
        "modificado": "2024-01-11T04:45:18.501+00:00",
        "ultimoLogin": null
    },
    {
        "id": 2,
        "nombre": "Marcelo",
        "correo": "marcelolopezhernandez2@gmail.com",
        "contrasena": "$2a$10$cDexahhHqPScbwZKkaMPB.hJJn2rs3fCQt.nLO2DUeNy.ubMeQG0e",
        "token": null,
        "activo": false,
        "telefonos": [
            {
                "id": 3,
                "numero": "993677499",
                "codigoCiudad": 1,
                "codigoPais": 4
            },
            {
                "id": 4,
                "numero": "993675215",
                "codigoCiudad": 51,
                "codigoPais": 101
            }
        ],
        "creado": "2024-01-11T04:45:28.289+00:00",
        "modificado": "2024-01-11T04:45:28.289+00:00",
        "ultimoLogin": null
    }
]
```

### Modificar Usuario
Es posible modificar atributos correo, nombre, contraseña y activo. 

Method : PUT

URL : http://localhost:8080/usuarios/{id}

Payload : 

```json
{
    "nombre": "Marcelo Lopez",
    "correo": "marcelolopezhernandez2@gmail.com",
    "contrasena": "aASD1255F",
    "activo": false
}
```

Response : 
```json
{
    "usuario": {
        "id": 1,
        "nombre": "Marcelo Lopez",
        "correo": "marcelolopezhernandez2@gmail.com",
        "contrasena": "$2a$10$Ig.JzuLXs1nRs8jOhrPl/elsVFL3.RYRlnZUkyey3cg1iVfidvaMy",
        "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI5MmJiMTgwYS1mZTgxLTQ5YmMtOWUyYS1kZTg2MDE4MmUxNjIiLCJzdWIiOiIxIDIwMjQtMDEtMTEgMDE6NTA6NDAuOTE0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTcwNDk0ODY0MCwiZXhwIjoxNzA0OTQ4OTQwfQ.gP5CQavQr_1-grA0gAmgSvznraN9U28VVgljXdjnGeBXGozr3EVru6xF1WuvklYVsVAFhUBME8TrnXhTnd_UwA",
        "activo": true,
        "telefonos": [
            {
                "id": 1,
                "numero": "993677499",
                "codigoCiudad": 1,
                "codigoPais": 4
            },
            {
                "id": 2,
                "numero": "993675215",
                "codigoCiudad": 51,
                "codigoPais": 101
            }
        ],
        "creado": "2024-01-11T04:50:34.943+00:00",
        "modificado": "2024-01-11T04:52:17.283+00:00",
        "ultimoLogin": "2024-01-11T04:50:40.914+00:00"
    },
    "mensaje": "Se ha modificado el usuario"
}
```


### Eliminar Usuario
METHOD : DELETE

URL : http://localhost:8080/usuario/{id}

Reponse : 

```json
{
    "mensaje": "Se ha eliminado el usuario"
}
```
