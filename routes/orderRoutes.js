// routes/orderRoutes.js
const express = require('express');
const Order = require('../models/Order');
const User = require('../models/User');
const router = express.Router();

// Ruta para registrar un pedido
router.post('/create-order', async (req, res) => {
  const { productos, total, direccionDestinatario, nombreDestinatario, telefonoDestinatario } = req.body;
  const userId = req.user.id; // El ID del usuario autenticado

  try {
    const user = await User.findById(userId);
    if (!user) {
      return res.status(400).json({ message: 'Usuario no encontrado' });
    }

    // Crear el nuevo pedido
    const newOrder = new Order({
      usuario: userId,
      productos: productos,
      total: total,
      direccionDestinatario,
      nombreDestinatario,
      telefonoDestinatario,
    });

    // Guardar el pedido en la base de datos
    await newOrder.save();
    res.status(201).json({ message: 'Pedido realizado con éxito', order: newOrder });
  } catch (error) {
    console.error('Error al guardar el pedido:', error);
    res.status(500).json({ message: 'Error al registrar el pedido' });
  }
});

module.exports = router;
