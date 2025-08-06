# 🏥 Proyecto de Gestión Hospitalaria - POO

Este es un proyecto académico desarrollado para la materia de Programación Orientada a Objetos (POO), cuyo objetivo es implementar un sistema de gestión hospitalaria que permita llevar el control de **Hospitales** y sus **Pacientes**, con operaciones CRUD y base de datos MySQL.

---

## 📁 Estructura del Proyecto

```
ProyectoPOO/
│
├── database/
│   └── hospital.sql         # Script de la base de datos en MySQL
├── lib/                     # Librerías externas JavaFX y JPA
├── src/
│   ├── controllers/         # Lógica y controladores de la aplicación
│   ├── META-INF/            # Configuración de persistencia (JPA)
│   ├── models/              # Clases entidad Hospital y Paciente
│   ├── styles/              # Archivos CSS para estilizar JavaFX
│   ├── utils/               # Utilidades adicionales
│   └── views/               # Interfaces gráficas (FXML o JavaFX)
├── App.java                 # Clase principal (main)
├── .gitignore
└── README.md
```

---

## 💾 Base de Datos

El proyecto utiliza **MySQL** para almacenar la información.  
Puedes importar el archivo `hospital.sql` desde el directorio `database/` para crear las tablas requeridas. Las restricciones (`CONSTRAINT`) ya están incluidas para mantener la integridad referencial.

---

## ⚙️ Tecnologías Utilizadas

- Java 22
- JavaFX
- SQL SERVER
- JDBC
- JPA (Java Persistence API)
- Visual Studio Code

---

## 🔄 Funcionalidades Principales

- Crear, leer, actualizar y eliminar hospitales.
- Registrar pacientes asociados a un hospital.
- Validación de datos.
- Interfaz gráfica con JavaFX.
- Estilo visual personalizado con CSS.
- Conexión con base de datos SQL SERVER.

---

## 👨‍💻 Equipo de Desarrollo

Proyecto realizado por un equipo de 6 integrantes como parte de la asignatura POO.  

- *HECTOR BADILLO GARCIA*
- *MEGAN ARIANET HOWARD*
- *JUAN MIGUEL REYES RESENDIZ*
- *CHRISTIAN ISAAC MARTINEZ HERNANDEZ*
- *MARIA GUADALUPE ALPIZAR ALVAREZ*
- *LUIS GARCIA CRUZ*

---

## 🚀 ¿Cómo ejecutar el proyecto?

1. Asegúrate de tener Java con el jdk 22, SQL SERVER y VS Code instalados.
2. Clona el repositorio:

```bash
git clone https://github.com/XzAEiT-Murft/ProyectoPOO.git
```

3. Importa el archivo `hospital.sql` en tu gestor de bases de datos MySQL.
4. Abre el proyecto en VS Code.
5. Ejecuta `App.java` desde el explorador lateral o terminal.

---

## 🖇️ Link de la presentacion del proyecto

https://www.canva.com/design/DAGvKW5d9zI/p60B7zc5tEL50NH_B7HPRg/edit?utm_content=DAGvKW5d9zI&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

---



## 📝 Licencia

Proyecto académico. Todos los derechos reservados por el equipo Anthsoft (equipo académico temporal).

---

¡Listo para ser evaluado y mejorar! 💙
