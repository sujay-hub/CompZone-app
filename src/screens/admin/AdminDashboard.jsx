import React from 'react'
import { Box, Typography, List, ListItemButton, ListItemIcon, ListItemText, Divider, Button, Tooltip, Link, Avatar, IconButton } from '@mui/material';
import { Dashboard, Inventory2, ListAlt, Category } from '@mui/icons-material';
import { Outlet, useNavigate } from 'react-router-dom';
import LogoutIcon from '@mui/icons-material/Logout';
import { getUserInitial } from '../../services/authService';


function AdminDashboard() {
    const navigate = useNavigate();
    const initial = getUserInitial();

    const handleLogout = () =>{
    localStorage.removeItem('token');
    localStorage.removeItem('userId'); 
    navigate('/');
    window.location.reload();
  }

  return (
   <Box
         sx={{
           width: '95vw',
           minHeight: '95vh',
           p: 4,
           backgroundImage:
                 'url(https://www.shutterstock.com/shutterstock/photos/416145976/display_1500/stock-photo-admin-business-team-hands-at-work-with-financial-reports-and-a-laptop-416145976.jpg)',
           backgroundSize: 'cover',
           backgroundRepeat: 'no-repeat',
           backgroundPosition: 'center'
         }}
       >
    <Box sx={{ display: 'flex', width: '40vw', minHeight: '104vh', backgroundColor: '#f9f9f9', position:'absolute', left: 0, top:0 }}>
      {/* Sidebar */}
      <Box
        sx={{
          
          background: 'linear-gradient(180deg, #2196f3, #21cbf3)',
          color: 'white',
          display: 'flex',
          flexDirection: 'column',
          //p: 2,
          flex: 1, p: 3, overflowY: 'auto',
        }}
      >
        <Box sx={{
                              display: 'flex',
                              justifyContent: 'space-between',
                              alignItems: 'baseline',
                            }}>
        <Typography variant="h5" fontWeight="bold" mb={3} textAlign="center">
          Admin Panel
        </Typography>
        <Tooltip title='Logout'>
                <Button color='inherit' component={Link} onClick={handleLogout}>
                <LogoutIcon/>
                </Button>
        </Tooltip>
        </Box>
        <List>
          <ListItemButton onClick={() => navigate('/admin/orders')}>
            <ListItemIcon sx={{ color: 'white' }}>
              <ListAlt />
            </ListItemIcon>
            <ListItemText primary="All Orders" />
          </ListItemButton>
          <ListItemButton onClick={() => navigate('/admin/categories')}>
            <ListItemIcon sx={{ color: 'white' }}>
              <Category />
            </ListItemIcon>
            <ListItemText primary="Manage Categories" />
          </ListItemButton>
          <ListItemButton onClick={() => navigate('/admin/products/1/stock')}>
            <ListItemIcon sx={{ color: 'white' }}>
              <Inventory2 />
            </ListItemIcon>
            <ListItemText primary="Manage Products" />
          </ListItemButton>
        </List>
      </Box>

      {/* Content */}
      <Box sx={{ flex: 1, p: 3 }}>
        <Typography variant="h4" mb={2} fontWeight="medium" color='black'>
          Welcome, Admin
          <Tooltip title="Account">
          <IconButton>
            <Avatar sx={{ bgcolor: '#3f51b5', padding: '2px 2px' }}>{initial}</Avatar>
          </IconButton>
        </Tooltip>
        </Typography>
        <Outlet />
      </Box>
    </Box>
    </Box>
  )
}

export default AdminDashboard