// models/Order.js
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Definir el esquema de pedido
const orderSchema = new Schema(
  {
    usuario: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User',
      required: true,
    },
    productos: [
      {
        productoId: {
          type: mongoose.Schema.Types.ObjectId,
          ref: 'Product',
          required: true,
        },
        nombre: { type: String, required: true },
        precio: { type: Number, required: true },
        cantidad: { type: Number, required: true },
        subtotal: { type: Number, required: true },
      },
    ],
    total: { type: Number, required: true },
    estado: { type: String, default: 'Pendiente' },
    direccionDestinatario: { type: String, required: true },
    nombreDestinatario: { type: String, required: true },
    telefonoDestinatario: { type: String, required: true },
    fecha: { type: Date, default: Date.now },
  },
  { timestamps: true }
);

module.exports = mongoose.model('Order', orderSchema);
