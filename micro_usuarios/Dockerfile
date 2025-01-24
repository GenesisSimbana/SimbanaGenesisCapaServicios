# Usar una imagen base de Java
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado por tu aplicación al contenedor
COPY target/micro_usuarios-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el contenedor (asegúrate de coincidir con el puerto de tu aplicación)
EXPOSE 8004

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
