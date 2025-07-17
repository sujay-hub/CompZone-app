import React from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography,
  InputAdornment,
} from '@mui/material';
import LockIcon from '@mui/icons-material/Lock';
import EmailIcon from '@mui/icons-material/Email';
import { useNavigate } from 'react-router-dom';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import { login } from '../services/authService';
import { useState } from 'react';
import { addToCart } from '../services/cartService';
import { jwtDecode } from 'jwt-decode';

function LoginScreen() {
  
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [quantity, setQuantity] = useState(1);
  const navigate = useNavigate();

    const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const res = await login({ email, password }); // login API call
      const token = res.data.token;
      const role = res.data.role;

      // Store token and role
      localStorage.setItem('token', token);
      localStorage.setItem('ROLE', role);

      // Decode userId from token
      const decoded = jwtDecode(token);
      const userId = decoded.userId;
      localStorage.setItem('userId', userId);

      console.log('Login success:', decoded);

      // Check for pending product in localStorage
      const pendingProductId = localStorage.getItem('pendingProductId');
      if (pendingProductId) {
        const cartItem = {
          userId,
          productId: parseInt(pendingProductId),
          quantity,
        };
        try {
          await addToCart(cartItem);
          localStorage.removeItem('pendingProductId');
          alert('Product added to cart after login');
        } catch (err) {
          console.error('Failed to add product to cart:', err);
          alert('Failed to add product to cart');
        }
        navigate('/cart');
      } else {
        // Redirect based on role
        if (role === 'ADMIN') {
          navigate('/admin');
        } else {
          navigate('/');
        }
      }
    } catch (err) {
      console.error('Login failed:', err);
      alert('Invalid login credentials');
    }
  };

  const handleRegister=()=>{
    navigate('/register')
  }
  

  return (
    <Box
      sx={{
        position: 'absolute',
        top: '0',
        left: '0',
        minHeight: '100vh',
        width: '100vw',
        overflow: 'hidden',
        backgroundImage: 'url(https://static.vecteezy.com/system/resources/previews/003/570/649/non_2x/touching-virtual-screen-online-shopping-to-digital-cart-with-global-network-connection-intelligent-ecommerce-blue-background-free-photo.jpg)',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center center',
        backgroundAttachment: 'fixed',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 2,
      }}
    >
      <Card sx={{ maxWidth: 400, width: '100%', borderRadius: 4, boxShadow: 10, backdropFilter: 'blur(5px)',position: 'absolute', left: '120px'}}>
        <CardContent>
          <Typography variant="h5" align="center" gutterBottom>
            Welcome Back
          </Typography>
          <Typography variant="body2" align="center" sx={{ mb: 3, color: 'gray' }}>
            Please log in to continue
          </Typography>
          <form onSubmit={handleLogin}>
            <TextField
              label="Email"
              fullWidth
              required
              variant="outlined"
              margin="normal"
              value={email} onChange={(e) => setEmail(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start" >
                    <EmailIcon color="primary" />
                  </InputAdornment>
                ),
              }}
              
            />
            <TextField
              label="Password"
              fullWidth
              required
              type="password"
              variant="outlined"
              margin="normal"
              value={password} onChange={(e) => setPassword(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <LockIcon color="primary" />
                  </InputAdornment>
                ),
                endAdornment:(
                  <InputAdornment position="end" >
                  <VisibilityIcon sx={{cursor: 'pointer'}} color='primary'/>
                </InputAdornment>
                )
              }}
              
            />
            <Box display='flex' justifyContent='space-between'>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              onClick={handleLogin}
              sx={{ mt: 2, borderRadius: 2, width: 1/2.1 }}
            >
              Login
            </Button>
            <Button
              type="button"
              variant="contained"
              color="primary"
              
              sx={{ mt: 2, borderRadius: 2, width: 1/2.1}}
              onClick={handleRegister}
            >
              Register
            </Button>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
}

export default LoginScreen;

