// Importar las dependencias necesarias
const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');

// Configurar dotenv para leer el archivo .env
dotenv.config();

// Crear una instancia de Express
const app = express();

// Middleware para analizar JSON
app.use(express.json());

// Conectar a MongoDB usando Mongoose
mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('Conexión a MongoDB exitosa'))
  .catch(err => console.error('Error al conectar a MongoDB', err));

// Definir una ruta de prueba para verificar si el servidor funciona
app.get('/', (req, res) => {
  res.send('¡Servidor funcionando!');
});

// Escuchar en el puerto 3000
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});

// Importar las rutas de autenticación
const authRoutes = require('./routes/auth');

// Usar las rutas
app.use('/api/auth', authRoutes);
