import React from 'react';
import {
  Box,
  Typography,
  Grid,
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Button,
  IconButton,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { useNavigate } from 'react-router-dom';
import NavBar from '../Components/NavBar';
import { useState, useEffect } from 'react';
import { getUserCart, updateCartQuantity, removeFromCart, clearCart } from '../services/cartService';
import { jwtDecode } from 'jwt-decode';


function CartScreen() {
  /**
 * @typedef {Object} CartItem
 * @property {number} productId
 * @property {string} productName
 * @property {string} imageUrl
 * @property {number} price
 * @property {number} quantity
 */

/** @type {[CartItem[], Function]} */
  const [cartItems, setCartItems] = useState([]);
  const [cartCount, setCartCount] = useState(1);
  const navigate = useNavigate();

  //common code for token generation during crud
  const getUserIdFromToken = () => {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Please log in to view your cart.");
      navigate("/login");
      return;
    }
    try {
      const decoded = jwtDecode(token);
      return decoded.userId;
    } catch (err) {
      console.error("Invalid token:", err);
      return null;
    }
  };

  // Fetch cart items, Cart config for Navbar
  const fetchCartItems=()=>{
    getUserCart()
      .then((res) => {
        const items = res.data;
        setCartItems(items);

        const count = items.reduce((sum, item) => sum + item.quantity, 0);
        setCartCount(count);
      })
      .catch((err) => console.error("Cart fetch error:", err));
  };
  useEffect(()=>{
    fetchCartItems();
  }, []);

  /**
 * @param {number} productId
 * @param {number} quantity
 */
  // Handle quantity increase (just a demo – can add decrease/edit input)
  const handleCartQuantityChange = (productId, quantity) => {
    const userId = getUserIdFromToken();
    if (!userId) return;
    const updateRequest = {
      userId,
      productId,
      quantity
    };
  
    updateCartQuantity(updateRequest)
      .then(() => fetchCartItems())
      .catch((err) => console.error(err));
  };

  /**
 * @param {number} productId
 */
  //Remove Cart Items
  const handleRemoveCartItem = (productId) => {
    const userId = getUserIdFromToken();
    if (!userId) return;
    const request = {
      userId,
      productId
    };
  
    removeFromCart(request)
      .then(() => fetchCartItems())
      .catch((err) => console.error(err));
  };
  //Clear cart
  const handleClearCart = () => {
    clearCart()
      .then(() => fetchCartItems())
      .catch(err => console.error(err));
  };

  // Total price
  const total = cartItems.reduce(
    (acc, item) => acc + item.price * item.quantity,0
  );
  
  
  const handleCheckout = () =>{
    if (cartItems.length === 0) {
      alert("Your cart is empty.");
      return;
    }
    navigate('/checkout')
  }

  return (
    <Box
      sx={{
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100vw',
        height: '100vh',
        overflow: 'hidden',
        //backgroundImage:
        //  'url(https://img.freepik.com/premium-photo/shopping-cart-abstract-background-concept-e-commerce-technology-blue-background-3d-rendering_41204-21704.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed',
        backgroundColor: '#ffff',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 3,
      }}
    >
      <NavBar cartCount={cartCount}/>
      <Box
        sx={{
          width: '95%',
          height: '85vh',
         
          borderRadius: 4,
          overflowY: 'auto',
          p: 4,
          scrollbarWidth: 'auto',
          '&::-webkit-scrollbar': {
            width: '10px',
          },
          '&::-webkit-scrollbar-thumb': {
            backgroundColor: '#888',
            borderRadius: '5px',
          },
        }}
      >
        <Typography variant="h4" gutterBottom textAlign="center">
          Your Cart
        </Typography>

        <Grid container spacing={3}>
          {cartItems.map(/** @param {CartItem} item */(item, index) => (
            <Grid item xs={12} sm={6} md={4} key={`${item.productId}-${index}`}>
              <Card sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                <CardMedia
                  component="img"
                  height="160"
                  image={item.imageUrl}
                  alt={item.productName}
                  sx={{ objectFit: 'contain' }}
                />
                <CardContent>
                  <Typography variant="h6">{item.productName}</Typography>
                  <Typography variant="body2">Quantity: {item.quantity || 0}</Typography>
                  <Typography variant="body2" color="text.secondary">
                  ₹{(item.price || 0).toLocaleString()}
                  </Typography>
                </CardContent>
                <CardActions sx={{ justifyContent: 'space-between', mt: 'auto' }}>
                  <Button variant="outlined" size="small" onClick={()=> handleCartQuantityChange(item.productId, item.quantity + 1)}>
                    +
                  </Button>
                  <Button variant="outlined" size="small" onClick={()=> handleCartQuantityChange(item.productId, item.quantity - 1)}>
                    -
                  </Button>
                  <IconButton color="error" onClick={()=> handleRemoveCartItem(item.productId)}>
                    <DeleteIcon />
                  </IconButton>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>

        <Box mt={4} textAlign="right">
          <Typography variant="h6">Total: ₹{total.toLocaleString()}</Typography>
          <Button variant="contained" size="large" sx={{ mt: 2 }} onClick={handleCheckout}>
            Proceed to Checkout
          </Button>
          <Button variant="text" color="error" sx={{ ml: 2 }} onClick={handleClearCart}>
            Clear Cart
          </Button>
        </Box>
      </Box>
    </Box>
  );
}

export default CartScreen;






















// import React from 'react'
// import { Container, Typography, List, ListItem, ListItemText, Button } from '@mui/material';



// export default function CartScreen() {
//   const dummyCart = [
//     { name: 'Product A', price: 1000 },
//     { name: 'Product B', price: 2000 },
//   ];
//   return (
//     <Container>
//     <Typography variant="h5" gutterBottom>Your Cart</Typography>
//     <List>
//       {dummyCart.map((item, idx) => (
//         <ListItem key={idx}>
//           <ListItemText primary={item.productName} secondary={`₹${item.price}`} />
//         </ListItem>
//       ))}
//     </List>
//     <Button variant="contained">Checkout</Button>
//   </Container>
//   )
// }
