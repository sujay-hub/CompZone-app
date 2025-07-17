import React, { useEffect, useState } from 'react';
import { getUserId } from '../services/cartService'; 
import {
  Box,
  Typography,
  TextField,
  Button,
  Grid,
  Divider,
  Dialog,
  DialogContent,
  CircularProgress
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import NavBar from '../Components/NavBar';
import { getUserCart, clearCart } from '../services/cartService';
import { placeOrder } from '../services/checkoutService';
import { useMemo } from 'react';

function CheckoutScreen() {
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState([]);
  const [isProcessing, setIsProcessing] = useState(false);

  //const userId = 1; // Replace with logged-in user logic if needed'
  const userId = getUserId();
  const[shippingAddress, setShippingAddress] = useState({
    fullName: '',
    addressLine1: '',
    addressLine2: '',
    city: '',
    postalCode: '',
    country: ''
  })

  // Fetch cart items on mount
  useEffect(() => {
    getUserCart()
      .then((res) => setCartItems(res.data))
      .catch((err) => console.error('Error fetching cart:', err));
  }, []);

  const { subtotal, tax, shipping, total } = useMemo(() => {
    const subtotal = cartItems.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
    );
    const taxRate = 0.18;
    const tax = Math.round(subtotal * taxRate);
    const shipping = subtotal > 1000 ? 0 : 299;
    const total = subtotal + shipping + tax;
    return { subtotal, tax, shipping, total };
  }, [cartItems]);

  const handlePlaceOrder = async () => {
    try{
      setIsProcessing(true); // before delay
      

       const res = await getUserCart();
       const latestCartItems = res.data;
       // before success/failure UI
     // Optional: Show loader or "Processing Payment" dialog

      if (!Array.isArray(latestCartItems) || latestCartItems.length === 0) {
      alert("❌ Cannot place order: Your cart is empty.");
      setIsProcessing(false);
      return;
    }
    const isPaymentSuccessful = true; // Or use Math.random() < 0.8 for 80% success rate
    await new Promise(resolve => setTimeout(resolve, 1500));

    if(!isPaymentSuccessful){
      alert('Payment failed. Please try again!');
      setIsProcessing(false);
      return;
    }
    const formattedAddress = [
        shippingAddress.fullName,
        shippingAddress.addressLine1,
        shippingAddress.addressLine2,
        shippingAddress.city,
        shippingAddress.postalCode,
        shippingAddress.country,
      ]
        .filter(Boolean)
        .join(', ');

    const validCartItems = latestCartItems
      .filter(item =>
        typeof item.productId === "number" &&
        item.productId > 0 &&
        typeof item.quantity === "number" &&
        item.quantity > 0
      )
      .map(item => ({
        productId: item.productId,
        quantity: item.quantity
      }));
     // Proceed to place order
    const orderRequest = {
      userId : userId ?? 0,
      cartItems: validCartItems,
      shippingAddress: formattedAddress,
      // Optionally mock payment info
      paymentStatus: 'SUCCESS',
      
    };

    await placeOrder(orderRequest);
        

    await clearCart();

    alert('✅ Order placed successfully!');
    navigate('/orders/history');
  } catch (err) {
    console.error('Order placement failed:', err);
    alert('❌ Failed to place order');
  }

  };

  return (
    <Box
      sx={{
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100vw',
        height: '100vh',
        overflow: 'hidden',
        backgroundImage:
          'url(https://images.rawpixel.com/image_800/czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvbHIvcm0zMDktYWRqLTE0LmpwZw.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 0,
      }}
    >
      <NavBar />
      <Box
        sx={{
          width: '90%',
          height: '85vh',
          backgroundColor: 'rgba(151, 145, 145, 0.96)',
          borderRadius: 4,
          p: 3,
          overflowY: 'auto',
          scrollbarWidth: 'thin',
          '&::-webkit-scrollbar': {
            width: '8px',
          },
          '&::-webkit-scrollbar-thumb': {
            backgroundColor: '#aaa',
            borderRadius: '4px',
          },
        }}
      >
        <Typography variant="h4" gutterBottom textAlign="center">
          Checkout
        </Typography>

        <Grid container spacing={4}>
          {/* Shipping Address */}
          <Grid item xs={12} md={6}>
            <Typography variant="h6" gutterBottom>
              Shipping Address
            </Typography>
            <Box mt={1}>
              <TextField fullWidth label="Full Name" margin="normal" value={shippingAddress.fullName}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, fullName: e.target.value})
              }
              />
              <TextField fullWidth label="Address Line 1" margin="normal" value={shippingAddress.addressLine1}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, addressLine1: e.target.value})
              }/>
              <TextField fullWidth label="Address Line 2" margin="normal" value={shippingAddress.addressLine2}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, addressLine2: e.target.value})
              }/>
              <TextField fullWidth label="City" margin="normal" value={shippingAddress.city}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, city: e.target.value})
              }/>
              <TextField fullWidth label="Postal Code" margin="normal" value={shippingAddress.postalCode}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, postalCode: e.target.value})
              }/>
              <TextField fullWidth label="Country" margin="normal" value={shippingAddress.country}
              onChange={ (e)=>
                setShippingAddress({...shippingAddress, country: e.target.value})
              }/>
            </Box>
          </Grid>

          {/* Order Summary */}
          <Grid item xs={12} md={6}>
            <Typography variant="h6" gutterBottom>
              Order Summary
            </Typography>
            <Box mb={2}>
              <Typography>Subtotal: ₹{subtotal.toLocaleString()}</Typography>
              <Typography>Shipping: ₹{shipping.toLocaleString()}</Typography>
              <Typography>Tax: ₹{tax.toLocaleString()}</Typography>
              <Divider sx={{ my: 1 }} />
              <Typography variant="h6">Total: ₹{total.toLocaleString()}</Typography>
            </Box>
            <Button
              variant="contained"
              size="large"
              fullWidth
              onClick={handlePlaceOrder}
              disabled={cartItems.length === 0}
            >
              Place Order
            </Button>
            <Dialog open={isProcessing}>
              <DialogContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', p: 4 }}>
                <CircularProgress />
                <Typography mt={2}>Processing payment...</Typography>
              </DialogContent>
            </Dialog>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
}

export default CheckoutScreen;
