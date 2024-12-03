const express = require('express');
const router = express.Router();
const User = require('../models/User');
const bcrypt = require('bcryptjs');

// Ruta para registrar un usuario
router.post('/register', async (req, res) => {
  console.log(req.body);

  const { email, nombre, password, direccion, telefono } = req.body;

  // Validar campos vacíos
  if (!email || !nombre || !password || !direccion || !telefono) {
    return res.status(400).json({ message: 'Por favor, completa todos los campos.' });
  }

  // Validar formato de email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    return res.status(400).json({ message: 'El correo electrónico no es válido.' });
  }

  // Validar contraseña
  if (password.length < 8 || !/[a-zA-Z]/.test(password) || !/\d/.test(password)) {
    return res.status(400).json({ message: 'La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.' });
  }

  // Validar teléfono
  const phoneRegex = /^\d{7,15}$/; // Ajustar según la longitud de número deseada
  if (!phoneRegex.test(telefono)) {
    return res.status(400).json({ message: 'El teléfono debe contener solo números y tener entre 7 y 15 dígitos.' });
  }

  // Validar dirección
  if (/^\d+$/.test(direccion)) {
    return res.status(400).json({ message: 'La dirección no puede contener solo números.' });
  }

  try {
    const newUser = new User({
      email,
      nombre,
      password,
      direccion,
      telefono,
      confirmado: true,
      token: ''
    });

    const salt = await bcrypt.genSalt(10);
    newUser.password = await bcrypt.hash(newUser.password, salt);

    await newUser.save();

    res.status(201).json({ message: 'Usuario registrado exitosamente' });
  } catch (error) {
    console.error(error);
    if (error.code === 11000) {
      return res.status(400).json({ message: 'El correo electrónico ya está en uso.' });
    }
    
    res.status(500).json({ message: 'Error al registrar el usuario' });
  }
});

// Ruta para iniciar sesión
router.post('/login', async (req, res) => {
  try {
    const { identificador, password } = req.body;
    const user = await User.findOne({
      $or: [
        { email: identificador },
        { nombre: identificador }
      ]
    });

    if (!user) {
      return res.status(400).json({ success: false, message: 'Usuario no encontrado. Por favor, regístrese.' });
    }

    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.status(400).json({ success: false, message: 'Contraseña incorrecta' });
    }

    res.status(200).json({ success: true, message: 'Inicio de sesión exitoso', user });

  } catch (error) {
    console.error(error);
    res.status(500).json({ success: false, message: 'Error al iniciar sesión' });
  }
});

module.exports = router;
