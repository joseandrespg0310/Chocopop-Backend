const express = require('express');
const Product = require('../models/Product');  // Asegúrate de que el modelo esté correctamente importado
const router = express.Router();

// Ruta para obtener todos los productos
router.get('/products', async (req, res) => {
    try {
        const products = await Product.find();
        console.log('Productos obtenidos:', products);  // Log para verificar datos
        res.json(products);
    } catch (error) {
        console.error(error);
        res.status(500).send('Error al obtener los productos');
    }
});


module.exports = router;
