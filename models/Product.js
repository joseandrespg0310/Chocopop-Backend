const mongoose = require('mongoose');

const productSchema = new mongoose.Schema({
  nombre: {
    type: String,
    required: true,
  },
  precio: {
    type: Number,
    required: true,
  },
  descripcion: {
    type: String,
    required: true,
  },
  imagen: {
    type: String,  // URL de la imagen
    required: true,
  },
  categoria: [{
    type: String,
  }],
  codigo_de_barras: {
    type: String,
    required: true,
  },
  ingredientes: {
    type: String,
  },
  pais: {
    type: String, 
    required: true,
  },
  peso: {
    type: Number,  // Agregado campo peso
    required: true,
  },
  fechaRegistro: {
    type: Date,
    default: Date.now,
  }
}, { collection: 'productos' });

const Product = mongoose.model('Product', productSchema);

module.exports = Product;
