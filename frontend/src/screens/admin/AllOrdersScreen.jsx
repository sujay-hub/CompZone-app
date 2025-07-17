import React, { useEffect, useState } from 'react';
import {
  Box,
  TableContainer,
  Paper,
  Chip,
  Button,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from '@mui/material';

import { getAllOrders } from '../../services/orderService';
import { useNavigate } from 'react-router-dom';
import { getOrderHistory } from '../../services/orderHistoryService';
import api from '../../services/api/axios';


export default function AllOrdersScreen() {
  const [orders, setOrders] = useState([]);
  const navigate= useNavigate();

  const handleGoHome=()=>{
    navigate('/admin');
  }

  useEffect(() => {
    getAllOrders()
      .then((res) => setOrders(res.data))
      .catch((err) => console.error('Failed to fetch orders:', err));
  }, []);

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
        backgroundImage:
          'url(https://img.freepik.com/free-vector/abstract-technology-digital-background_53876-110700.jpg)',
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
        px: 3,
      }}
    >
      <Box
        sx={{
          backgroundColor: 'rgba(255,255,255,0.96)',
          borderRadius: 4,
          width: '90%',
          maxWidth: 1200,
          p: 4,
          boxShadow: 3,
        }}
      >
        <Box sx={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'baseline',
      }}>
        <Typography variant="h4" gutterBottom align="center" color='black'>
          All Orders
        </Typography>
        <Button sx={{pt: '3px'}} size="small" variant="contained" color="primary" onClick={handleGoHome}>
                            Home
                </Button>
        </Box>
        <TableContainer component={Paper}>
          <Table>
            <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
              <TableRow>
                <TableCell><strong>Order ID</strong></TableCell>
                <TableCell><strong>User</strong></TableCell>
                <TableCell><strong>Date</strong></TableCell>
                <TableCell><strong>Total</strong></TableCell>
                <TableCell><strong>Paid</strong></TableCell>
                <TableCell><strong>Delivered</strong></TableCell>
                <TableCell><strong>Action</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {orders.map((order) => (
                <TableRow key={order.orderId}>
                  <TableCell>{order.orderId}</TableCell>
                  <TableCell>{order.userName}</TableCell>
                  <TableCell>{new Date(order.createdAt).toLocaleString()}</TableCell>
                  <TableCell>₹{Number(order.totalAmount).toLocaleString()}</TableCell>
                  <TableCell>
                    {order.isPaid ? (
                      <Chip label="Paid" color="success" size="small" />
                    ) : (
                      <Chip label="Unpaid" color="warning" size="small" />
                    )}
                  </TableCell>
                  <TableCell>
                    {order.isDelivered ? (
                      <Chip label="Delivered" color="primary" size="small" />
                    ) : (
                      <Chip label="Pending" color="error" size="small" />
                    )}
                  </TableCell>
                  <TableCell>
                    <Button size="small" variant="outlined">
                      View
                    </Button>
                    <Button
                      size="small"
                      variant="contained"
                      color="secondary"
                      onClick={() => handleDownloadInvoice(order.orderId)}
                    >
                      Download PDF
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    </Box>
  );
}
