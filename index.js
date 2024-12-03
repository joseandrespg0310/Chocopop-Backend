// Importar las dependencias necesarias
const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const orderRoutes = require('./routes/orderRoutes');

// Configurar dotenv para leer el archivo .env
dotenv.config();

// Crear una instancia de Express
const app = express();

// Middleware para analizar JSON
app.use(express.json());

// Conectar a MongoDB
mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('Conexión a MongoDB exitosa'))
  .catch(err => console.error('Error al conectar a MongoDB', err));

// Definir una ruta de prueba para verificar si el servidor funciona
app.get('/', (req, res) => {
  res.send('¡Servidor funcionando!');
});

// Importar las rutas de autenticación
const authRoutes = require('./routes/auth'); // Ruta de autenticación


// Importar las rutas de productos
const productRoutes = require('./routes/productRoutes'); // Importa correctamente las rutas de productos

// Usar las rutas
app.use('/api/auth', authRoutes); // Usar la ruta de autenticación
app.use('/api', productRoutes);   // Usar las rutas de productos

// Rutas

app.use('/api/orders', orderRoutes);

// Escuchar en el puerto 3000
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});
