const mongoose = require('mongoose');

const productOrderSchema = new mongoose.Schema({
    productoId: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
    nombre: { type: String, required: true },
    precio: { type: Number, required: true },
    cantidad: { type: Number, required: true },
    subtotal: { type: Number, required: true }
});

const ProductOrder = mongoose.model('ProductOrder', productOrderSchema);
module.exports = ProductOrder;
