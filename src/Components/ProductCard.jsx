import React from 'react';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import CardActions from "@mui/material/CardActions"
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import { addToCart } from '../services/cartService';
import { jwtDecode } from 'jwt-decode';
import { useState } from 'react';

/**
 * @param {{ product: Product }} props
 */
function ProductCard({ product }) {
  const [quantity, setQuantity] = useState(1);
  const navigate = useNavigate();

  const handleProductDetails = () =>{
    navigate(`/product/${product.productId}`)
  }

  const handleAddToCart = () => {
    const token = localStorage.getItem('token');

    if (!token) {
      <Alert variant="filled" severity="warning">
        Please log in to add items to cart.
      </Alert>
      //alert('Please log in to add items to cart.');
      localStorage.setItem('pendingProductId', product.productId);
      navigate('/login');
      return;
    }

    let userId;
    try {
      const decoded = jwtDecode(token);
      userId = decoded.userId; // Adjust the key if your token uses something like `sub` or `id`
    } catch (err) {
      console.error('Invalid token', err);
      alert('Session expired. Please login again.');
      navigate('/login');
      return;
    }

    const cartItem = {
      userId,
      productId: product.productId,
      quantity,
    };

    addToCart(cartItem, token)
      .then(() => {
        console.log('Added to cart');
        alert('Added to cart successfully')
        //navigate('/checkout');
      })
      .catch(err => {
        console.error('Failed to add to cart:', err);
        alert('Failed to add product to cart.');
      });
  };
  return (
    <Card sx={{ maxWidth: 300, ml: 5}}>
      <CardMedia
        component="img"
        height="160"
        image={product.imageUrl}
        alt={product.productName}
        sx={{objectFit: 'contain'}}
      />
      <CardContent>
        <Typography variant="h6" gutterBottom>
          {product.productName}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          ₹{product.price}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" variant="contained" onClick={handleAddToCart}>Add to Cart</Button>
        <Button size="small" onClick={handleProductDetails}>Details</Button>
      </CardActions>
    </Card>
  );
}
ProductCard.propTypes = {
  product: PropTypes.shape({
    productId: PropTypes.number,
    productName: PropTypes.string,
    price: PropTypes.number,
    imageUrl: PropTypes.string
  }).isRequired
};


export default ProductCard;