const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Definir el esquema de usuario con los nuevos campos
const userSchema = new Schema({
  email: {
    type: String,
    required: true,
    unique: true,
    trim: true,
    lowercase: true
  },
  nombre: {
    type: String,
    required: true,
    trim: true
  },
  password: {
    type: String,
    required: true
  },
  direccion: {
    type: String,
    required: false,
    trim: true
  },
  telefono: {
    type: String,
    required: true,
    trim: true
  },
  token: {
    type: String,
    default: '',
  },
  confirmado: {
    type: Boolean,
    default: false,
  },
}, {
  timestamps: true
});

// Exportar el modelo de Usuario especificando la colección 'usuarios'
module.exports = mongoose.model('User', userSchema, 'usuarios'); // Asegúrate de que esto esté correcto
