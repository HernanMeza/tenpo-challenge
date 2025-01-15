# Imagen base de Node.js para construir la app
FROM node:20 as build

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos los archivos necesarios
COPY package*.json ./
COPY . .

# Instalamos las dependencias y construimos la aplicación
RUN npm install && npm run build

# Imagen base para servir la app
FROM nginx:alpine

# Copiamos la build de React al contenedor NGINX
COPY --from=build /app/dist /usr/share/nginx/html

# Exponemos el puerto 80 para servir la app
EXPOSE 80

# Arrancamos el servidor NGINX
CMD ["nginx", "-g", "daemon off;"]
