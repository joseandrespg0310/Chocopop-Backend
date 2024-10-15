const mongoose = require('mongoose');

// Definir el esquema del usuario
const userSchema = new mongoose.Schema({
  nombres: { type: String, required: true },
  apellidos: { type: String, required: true },
  nombreUsuario: { type: String, required: true, unique: true },
  correo: { type: String, required: true, unique: true },
  contraseña: { type: String, required: true }
});

// Crear el modelo de usuario
const User = mongoose.model('User', userSchema);

module.exports = User;
