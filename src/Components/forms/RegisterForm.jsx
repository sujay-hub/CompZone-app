import React, { useState } from 'react';
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Grid
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../../services/authService'; // ✅ import the API function

function RegisterForm() {
  const navigate = useNavigate();

  // ✅ Step 1: Add state variables
  const [userName, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);

  // ✅ Step 2: Update registration handler
  const handleRegistration = async () => {
    if (!userName || !email || !password || !confirmPassword) {
      alert('Please fill in all fields');
      return;
    }
    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    try {
      setLoading(true);
      await registerUser({ userName, email, password });
      setLoading(false);
      navigate('/login');
    } catch (err) {
      console.error('Registration failed:', err.response?.data?.error || err.message);
      alert('Registration failed');
    }
  };

  return (
    <Box
      sx={{
        width: '100vw',
        height: '100vh',
        position: 'absolute',
        left: '0',
        top: '0',
        backgroundImage:
          'url(https://static.vecteezy.com/system/resources/previews/003/570/649/non_2x/touching-virtual-screen-online-shopping-to-digital-cart-with-global-network-connection-intelligent-ecommerce-blue-background-free-photo.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        overflow: 'hidden',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 2,
      }}
    >
      <Paper
        elevation={8}
        sx={{
          maxWidth: 400,
          width: '100%',
          p: 4,
          borderRadius: 3,
          backdropFilter: 'blur(10px)',
          backgroundColor: 'rgba(255,255,255,0.85)',
          position: 'absolute',
          left: '120px'
        }}
      >
        <Typography variant="h5" gutterBottom textAlign="center">
          Create an Account
        </Typography>

        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Username"
              variant="outlined"
              value={userName}
              onChange={(e) => setName(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Email"
              type="email"
              variant="outlined"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Password"
              type="password"
              variant="outlined"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Confirm Password"
              type="password"
              variant="outlined"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              fullWidth
              variant="contained"
              color="primary"
              sx={{ mt: 1 }}
              onClick={handleRegistration}
              disabled={loading}
            >
              {loading ? 'Registering...' : 'Register'}
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}

export default RegisterForm;