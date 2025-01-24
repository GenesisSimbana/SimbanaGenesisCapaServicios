  # Usar imagen base de OpenJDK
FROM openjdk:17-jdk-slim

  # Establecer directorio de trabajo
WORKDIR /app

  # Copiar el archivo JAR del proyecto al contenedor
COPY target/micro_cursos-0.0.1-SNAPSHOT.jar app.jar

  # Exponer el puerto 8002
EXPOSE 8002

  # Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
