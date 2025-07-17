import React from 'react';
import { useState, useEffect } from 'react';
import { Box, Typography, Grid, Button, Rating, Divider, } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { getProductById } from '../services/productService';
import { getAllProducts } from '../services/productService';
import { getUserCart } from '../services/cartService';
import { useParams } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import Alert from '@mui/material/Alert';
import NavBar from '../Components/NavBar';

  /**
 * @typedef {Object} CartItem
 * @property {number} productId
 * @property {string} productName
 * @property {string} imageUrl
 * @property {number} price
 * @property {number} quantity
 */
/**
 * @typedef {Object} Product
 * @property {number} productId
 * @property {string} productName
 * @property {number} price
 * @property {string} imageUrl
 * @property {string} category
 */
/** @type {[Product|null, Function]} */



export default function ProductDetailsScreen() {
  const { id } = useParams(); // ✅ gets productId from URL
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [cartCount, setCartCount] = useState(0);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  
  const [showLoginAlert, setShowLoginAlert] = useState(false);
  
  useEffect(() => {
    getAllProducts()
      .then((res) => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Error fetching products:', err);
        setLoading(false);
      });
  }, []);
 
  useEffect(() => {
    getProductById(id)
      .then((res) => setProduct(res.data))
      .catch((err) => console.error('Error fetching product:', err));
  }, [id]);
  
    if (!product) return <Typography>Loading...</Typography>;
  
    const handleAddToCart = async () => {
      const token = localStorage.getItem("token");
  
      if (!token) {
        // <Alert variant="filled" severity="warning">
        // Please log in to add items to cart.
        // </Alert>
        //alert("Please log in to add items to cart.");

        setShowLoginAlert(true);
        localStorage.setItem("pendingProductId", product.productId);
        setTimeout(() => {
        navigate("/login");
        }, 4000);
        
        return;
      }
  
      let userId;
      try {
        const decoded = jwtDecode(token);
        userId = decoded.userId; // adjust key if needed
      } catch (err) {
        console.error("Invalid token", err);
        alert("Session expired. Please login again.");
        navigate("/login");
        return;
      }
  
      const cartItem = {
        userId,
        productId: product.productId,
        quantity
      };
  
      try {
        await addToCart(cartItem);
        alert("Product added to cart!");
        navigate("/cart");
      } catch (err) {
        console.error("Add to cart failed", err);
        alert("Failed to add product to cart.");
      }
    };
  
  
    
  return (
    <>
    <NavBar cartCount={cartCount}/>
    <Box
      sx={{
        
        width: '100vw',
        minHeight: '100vh',
         backgroundImage:
        'url(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPdAIWtBsarRLR7POa5LniU5yf-uCbFh2xGQ&s)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed',
        display: 'flex',
        justifyContent: 'flex-start',
        alignItems: 'flex-start',
        py: 8,
        px: 3,
        overflowX: 'hidden',
      }}
    >
      
      <Box
        sx={{
          my: 4,
          backgroundColor: 'rgba(255,255,255,0.95)',
          borderRadius: 4,
          p: 4,
          width: '20vw',
          //maxWidth: '500px',
        }}
        
      >
        {showLoginAlert && (
                <Alert variant="filled" severity="warning">
                  Please log in to add items to cart.
                </Alert>
              )}
        <Grid container spacing={4}>
          {/* Image */}
          <Grid item xs={12} md={5}>
            <Box
              component="img"
              src={product.imageUrl}
              alt={product.productName}
              sx={{
                width: '100%',
                borderRadius: 3,
                objectFit: 'contain',
                boxShadow: 3,
              }}
            />
          </Grid>

          {/* Product Info */}
          <Grid /*item xs={12} md={7}*/>
            <Typography variant="h4" gutterBottom color='black'>
              {product.productName}
            </Typography>
            <Typography variant="h6" gutterBottom color='black'>
              {product.description}
            </Typography>
            <Typography variant="h6" color="secondary" gutterBottom>
              ₹{product.price.toLocaleString()}
            </Typography>

            {/* <Rating
              value={product.rating}
              readOnly
              precision={0.5}
              sx={{ mb: 1 }}
            /> */}

            

            <Divider sx={{ my: 2 }} />

            <Typography variant="body1" paragraph>
              {product.description}
            </Typography>

            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
              Availability: {product.stockQuantity  > 0 ? 'In stock ' : 'Out of stock '}
            </Typography>
              

            <Button
              variant="contained"
              color="primary"
              disabled={product.stockQuantity  <= 0}
              onClick={handleAddToCart}
            >
              Add to Cart
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Box>
    </>
  )
}
