-- Crear base de datos para SQL Server
CREATE DATABASE HospitalDB;
GO

-- Usar base de datos
USE HospitalDB;
GO

-- Tabla: Hospitales
CREATE TABLE Hospitales (
    Id INT NOT NULL IDENTITY(1, 1),
    Nombre VARCHAR(100) NOT NULL,
    Direccion VARCHAR(150),
    Telefono VARCHAR(20),
    FechaRegistro DATE NOT NULL DEFAULT GETDATE(),
    Activo BIT NOT NULL DEFAULT 1,
    CONSTRAINT PK_Hospital_Id PRIMARY KEY (Id)
);
GO

-- Tabla: Pacientes
CREATE TABLE Pacientes (
    Id INT NOT NULL IDENTITY(1, 1),
    NombrePila VARCHAR(100) NOT NULL,
    Apellido1 VARCHAR(100) NOT NULL,
    Apellido2 VARCHAR(30),
    Edad INT,
    Genero VARCHAR(10) CHECK (
        Genero IN ('Masculino', 'Femenino', 'Otro')
    ),
    FechaRegistro DATE NOT NULL DEFAULT GETDATE(),
    Activo BIT NOT NULL DEFAULT 1,
    HospitalId INT NOT NULL,
    CONSTRAINT PK_Paciente_Id PRIMARY KEY (Id),
    CONSTRAINT CK_Paciente_Edad CHECK (Edad >= 0),
    CONSTRAINT FK_Paciente_Hospital FOREIGN KEY (HospitalId) REFERENCES Hospitales(Id)
);
GO


-- Insertar datos de ejemplo en la tabla Hospitales
INSERT INTO Hospitales (Nombre, Direccion, Telefono)
VALUES 
('Santa Monica', 'Av. Reforma 100', '555-1000'),
('Los Olivos', 'Calle Cedros 23', '555-1001'),
('Del Carmen', 'Blvd. Hidalgo 450', '555-1002'),
('Buen Samaritano', 'Calle Salud 88', '555-1003'),
('Virgen del Socorro', 'Av. Esperanza 12', '555-1004'),
('La Esperanza', 'Av. Central 55', '555-1005'),
('San José', 'Calle Juárez 19', '555-1006'),
('Cristo Rey', 'Av. Libertad 5', '555-1007'),
('General Hidalgo', 'Calle Independencia 30', '555-1008'),
('Maternidad Feliz', 'Calle Nacimientos 11', '555-1009');
GO

-- Insertar datos de ejemplo en la tabla Pacientes
INSERT INTO Pacientes (NombrePila, Apellido1, Apellido2, Edad, Genero, HospitalId)
VALUES 
('Luis', 'García', 'Cruz', 18, 'Masculino', 1),
('Ana', 'Martínez', 'López', 25, 'Femenino', 2),
('Carlos', 'Ramírez', '', 32, 'Masculino', 3),
('María', 'González', 'Díaz', 22, 'Femenino', 4),
('Jorge', 'Hernández', 'Pérez', 29, 'Masculino', 5),
('Lucía', 'López', '', 30, 'Femenino', 6),
('Pablo', 'Reyes', 'Torres', 40, 'Masculino', 7),
('Fernanda', 'Ruiz', 'Nava', 19, 'Femenino', 8),
('José', 'Mendoza', 'Salas', 45, 'Masculino', 9),
('Daniela', 'Flores', 'Mora', 21, 'Femenino', 10),
('Elena', 'Ortega', '', 27, 'Femenino', 1),
('Manuel', 'Silva', 'Lopez', 35, 'Masculino', 2),
('Isabel', 'Santos', '', 28, 'Femenino', 3),
('Raúl', 'Campos', 'Vega', 33, 'Masculino', 4),
('Valeria', 'Morales', 'Cano', 20, 'Femenino', 5),
('Ricardo', 'Serrano', '', 36, 'Masculino', 6),
('Paula', 'Aguilar', 'Ortiz', 38, 'Femenino', 7),
('Hugo', 'Chávez', 'Ramírez', 50, 'Masculino', 8),
('Sandra', 'Nieto', '', 24, 'Femenino', 9),
('Emilio', 'Bravo', 'Mejía', 42, 'Masculino', 10),
('Carmen', 'Carrillo', 'Pérez', 31, 'Femenino', 1),
('Mateo', 'Vargas', '', 37, 'Masculino', 2),
('Sofía', 'Cruz', 'López', 23, 'Femenino', 3),
('Iván', 'Navarro', 'Rosales', 26, 'Masculino', 4),
('Julia', 'Luna', 'Martínez', 39, 'Femenino', 5),
('Erick', 'Durán', 'Castañeda', 41, 'Masculino', 6),
('Renata', 'Figueroa', '', 29, 'Femenino', 7),
('Samuel', 'Arias', 'Romero', 46, 'Masculino', 8),
('Mónica', 'Ríos', '', 34, 'Femenino', 9),
('David', 'Peña', 'Gallardo', 30, 'Masculino', 10);
GO
