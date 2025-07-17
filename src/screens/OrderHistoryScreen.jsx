import React from 'react'
import { Box, Typography, Card,  CardContent,  CardActions,  Button,  Divider,  Grid, Chip } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import NavBar from '../Components/NavBar';
import { useState, useEffect } from 'react';
import { getOrderHistory } from '../services/orderHistoryService';
import api from '../services/api/axios';

/**
 * @typedef {Object} Order
 * @property {string} orderId
 * @property {string} createdAt
 * @property {number} total
 * @property {string} status
 * @property {string[]} items
 */
export default function OrderHistoryScreen() {
  /** @type {[Order[], Function]} */
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getOrderHistory()
      .then((res) => setOrders(res.data))
      .catch((err) => console.error("Order history fetch failed:", err));
      
  },
   []);
  

  const handleGoHome=()=>{
    navigate('/');
  }

  const handleDownloadInvoice= async (orderId)=>{
    try{
      const response = await api.get(`orders/${orderId}/invoice`, {
        responseType: 'blob',
      });
      
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download',`invoice_${orderId}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    }
    catch(err){
      console.error('Error downloading invoice:', err);
      alert('Failed to download invoice');
    }
  }



  return (
    <Box
      sx={{
        width: '100vw',
        minHeight: '100vh',
        backgroundImage: 'url(https://images.unsplash.com/photo-1586281380349-632531db7ed4)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'flex-start',
        position: 'absolute',
          left: 0,
          top: 0,
        py: 6,
        px: 2,
        overflowX: 'hidden'
      }}
    >
      <NavBar/>
      <Box
        sx={{
          backgroundColor: 'rgba(255,255,255,0.96)',
          borderRadius: 4,
          p: 4,
          width: '90%',
          maxWidth: '1200px',
          overflowY: 'auto',
          maxHeight: '85vh',
          scrollbarWidth: 'thin',
          '&::-webkit-scrollbar': {
            width: '10px'
          },
          '&::-webkit-scrollbar-thumb': {
            backgroundColor: '#999',
            borderRadius: '5px'
          }
        }}
      > 
      <Box sx={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'baseline',
      }}>
        <Typography variant="h4" gutterBottom align="center" color='black'>
          Your Order History
        </Typography>
        <Button sx={{pt: '3px'}} size="small" variant="contained" color="primary" onClick={handleGoHome}>
                    Home
        </Button>
        </Box>
        <Divider sx={{ mb: 4 }} />
        <Grid container spacing={3}>
          {orders.map((order) => (
            <Grid /*item xs={12} md={6}*/ key={order.orderId}>
              <Card elevation={3} sx={{ borderRadius: 3 }}>
                <CardContent>
                  <Typography variant="h6">
                    Order ID: {order.orderId}
                  </Typography>
                  <Typography color="text.secondary">
                    {/* Date: {order.createdAt} */}
                    Date: {order.createdAt}
                    {/* Date: {new Date(order.createdAt).toLocaleString("en-IN", {
                      year: "numeric", month: "short", day: "numeric",
                      hour: "2-digit", minute: "2-digit"
                    })} */}
                  </Typography>
                  <Typography>
                    Total: ₹{order.totalAmount}
                  </Typography>
                  <Typography>
                    Items: {order.items.map(item=>`${item.productName} (x${item.quantity})`).join(', ')}
                  </Typography>
                  <Chip
                    label={order.status}
                    color={
                      order.status === 'Delivered'
                        ? 'success'
                        : order.status === 'Shipped'
                        ? 'primary'
                        : 'warning'
                    }
                    sx={{ mt: 1 }}
                  />
                </CardContent>
                <CardActions>
                  <Button size="small" variant="outlined">
                    View Details
                  </Button>
                  <Button size="small" variant="contained" color="secondary">
                    Reorder
                  </Button>
                  <Button size="small" variant="outlined" color = "primary" onClick={() => handleDownloadInvoice(order.orderId)}>
                    Download Invoice
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Box>
  )
}
