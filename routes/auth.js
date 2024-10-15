const express = require('express');
const router = express.Router();
const User = require('../models/User');

// Ruta para registrar un usuario
router.post('/register', async (req, res) => {
  try {
    const { nombres, apellidos, nombreUsuario, correo, contraseña } = req.body;

    // Crear un nuevo usuario con los datos recibidos
    const newUser = new User({
      nombres,
      apellidos,
      nombreUsuario,
      correo,
      contraseña
    });

    // Guardar el usuario en la base de datos
    await newUser.save();

    res.status(201).json({ message: 'Usuario registrado exitosamente' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Error al registrar el usuario' });
  }
});

module.exports = router;
    