const express = require('express');
const router = express.Router();
const User = require('../models/User');
const bcrypt = require('bcryptjs');

// Ruta para iniciar sesión
router.post('/login', async (req, res) => {
  try {
    const { identificador, password } = req.body; // identificador puede ser el email o nombre

    // Log para verificar que se recibe el identificador correctamente
    console.log("Buscando usuario con identificador:", identificador);

    // Buscar al usuario por correo electrónico o nombre
    const user = await User.findOne({
      $or: [
        { email: identificador.toLowerCase() },
        { nombre: identificador }
      ]
    });

    if (!user) {
      return res.status(400).json({ success: false, message: 'Usuario no encontrado. Por favor, regístrese.' });
    }

    // Verificar la contraseña
    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.status(400).json({ success: false, message: 'Contraseña incorrecta' });
    }

    res.status(200).json({ success: true, message: 'Inicio de sesión exitoso', user });

  } catch (error) {
    console.error("Error al iniciar sesión:", error);
    res.status(500).json({ success: false, message: 'Error al iniciar sesión' });
  }
});

module.exports = router;
