import React from 'react'

import { AppBar, Toolbar, Typography, Button, Badge, Box, Tooltip, IconButton, Avatar } from '@mui/material';
import TextField from '@mui/material/TextField';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Link, useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import { useState } from 'react';
import HomeIcon from '@mui/icons-material/Home';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import HistoryIcon from '@mui/icons-material/History';
import { getUserInitial } from '../services/authService';
import { useEffect } from 'react';

export default function NavBar({cartCount}) {
  const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem("token"));
  const[searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();
  const initial = getUserInitial();
  useEffect(() => {
  const token = localStorage.getItem("token");
  setLoggedIn(!!token);
}, []);

  const handleLogout = () =>{
    localStorage.removeItem('token');
    localStorage.removeItem('userId'); 
    navigate('/');
    window.location.reload();
  }
  return (
    <AppBar position="fixed" color="primary" sx={{bgcolor: '#333333', width: '100%'}}>
    <Toolbar sx={{ justifyContent: 'space-between', flexWrap: 'wrap'}}>
      <Typography variant="h6" component={Link} to="/" sx={{ textDecoration: 'none', color: 'white'}}>
        CompZone
      </Typography>

      <Box>
        <Tooltip title = 'Home'>
        <Button color="inherit" component={Link} to="/">
        <HomeIcon/>
        </Button>
        </Tooltip>
        {/* <Button color="inherit" component={Link} to="/products">Products</Button> */}
        {!loggedIn && (
          <Tooltip title='Login'>
            <Button color="inherit" component={Link} to="/login">
              <LoginIcon />
            </Button>
          </Tooltip>
        )}
        <Tooltip title='Cart Quantity'>
        <Button color="inherit" component={Link} to="/cart">
          <Badge badgeContent={cartCount} color="secondary">
            <ShoppingCartIcon />
          </Badge>
        </Button>
        </Tooltip>
        <Tooltip title='Order History'>
        <Button color='inherit' component={Link} to="/orders/history">
        <HistoryIcon/>
        </Button>
        </Tooltip>
        {loggedIn && (
          <Tooltip title='Logout'>
            <Button color="inherit" onClick={handleLogout}>
              <LogoutIcon />
            </Button>
          </Tooltip>
        )}
        <Tooltip title="Account">
          <IconButton>
            <Avatar sx={{ bgcolor: '#3f51b5', padding: '2px 2px' }}>{initial}</Avatar>
          </IconButton>
        </Tooltip>
      </Box>
    </Toolbar>
  </AppBar>
  );
}

NavBar.propTypes = {
  cartCount: PropTypes.number.isRequired,
};
