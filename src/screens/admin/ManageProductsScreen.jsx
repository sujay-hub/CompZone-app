import React from 'react'
import {
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TableContainer,
  TextField,
  Paper,
  Button,
  IconButton,
  Tooltip,
  Chip
} from '@mui/material';
import { useState, useEffect } from 'react';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import InventoryIcon from '@mui/icons-material/Inventory';
import {
  getAllProducts,
  createProduct,
  updateProduct,
  deleteProduct,
  updateProductStock
} from '../../services/productService';
import { getAllCategories } from '../../services/categoryService';
import { useNavigate } from 'react-router-dom';

export default function ManageProductsScreen() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const navigate = useNavigate();


  const token = localStorage.getItem('token'); // or wherever you're storing it


  

  const handleGoHome=()=>{
    navigate('/admin');
  }
  const [currentProduct, setCurrentProduct] = useState({
    productName: '',
    price: '',
    imageUrl: '',
    stockQuantity: ''
  });
  

  const fetchProducts = () => {
    getAllProducts()
      .then((res) => setProducts(res.data))
      .catch((err) => console.error('Error fetching products:', err));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchCategories = () => {
    getAllCategories()
      .then((res) => setCategories(res.data))
      .catch((err) => console.error('Error fetching categories:', err));
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleOpenDialog = (product = null) => {
    if (product) {
      setEditMode(true);
      setCurrentProduct({
        ...product,
        stockQuantity: product.stockQuantity ?? '' // fallback in case backend doesn't send it
      });
    } else {
      setEditMode(false);
      setCurrentProduct({ productName: '', price: '', imageUrl: '', stockQuantity: '' });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setCurrentProduct({ productName: '', price: '', imageUrl: '', stockQuantity: '' });
  };

  const handleInputChange = (e) => {
    setCurrentProduct({ ...currentProduct, [e.target.name]: e.target.value });
  };

  const handleSaveProduct = () => {
    const data = {
      productName: currentProduct.productName,
      price: parseFloat(currentProduct.price),
      imageUrl: currentProduct.imageUrl,
      stockQuantity: parseInt(currentProduct.stockQuantity),
    };

    if (editMode) {
      updateProduct(currentProduct.productId, data)
        .then(() => {
          fetchProducts();
          handleCloseDialog();
        })
        .catch((err) => console.error('Update error:', err));
    } else {
      createProduct(data)
        .then(() => {
          fetchProducts();
          handleCloseDialog();
        })
        .catch((err) => console.error('Create error:', err));
    }
  };

  const handleDelete = (productId) => {
    deleteProduct(productId)
      .then(() => fetchProducts())
      .catch((err) => console.error('Delete error:', err));
  };

  const handleStockUpdate = (productId) => {
    const newStock = prompt("Enter new stock quantity:");
    if (!newStock || isNaN(newStock)) return alert("Invalid stock value");
  
   updateProductStock(productId, newStock, token)
      .then(() => {
        alert("Stock updated");
        fetchProducts();
      })
      .catch((err) => {
        console.error("Stock update failed:", err);
        alert("Error updating stock");
      });
  };
  
  
  return (
    <Box
      sx={{
        width: '95vw',
        minHeight: '95vh',
        p: 4,
        backgroundImage:
              'url(https://img.freepik.com/free-vector/abstract-technology-digital-background_53876-110700.jpg)',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center'
      }}
    >
      <Box sx={{
             width: '70vw',
             minHeight: '95vh',
          //maxWidth: '600px',
          bgcolor: 'rgba(255,255,255,0.95)',
          borderRadius: 4,
          p: 4,
          overflowY: 'auto',
          maxHeight: '85vh',
          boxShadow: 5,
          position: 'absolute',
          left: 0,
          top: 0
            }}>
      <Box  sx={{
                      display: 'flex',
                      justifyContent: 'space-between',
                      alignItems: 'baseline',
                    }}>
      <Typography variant="h4" gutterBottom align="center" color='black'>
        Manage Products
      </Typography>
      <Button sx={{pt: '3px'}} size="small" variant="contained" color="primary" onClick={handleGoHome}>
                          Home
      </Button>
      </Box>
      <TableContainer component={Paper} sx={{ mt: 3 }}>
        <Table>
          <TableHead sx={{ backgroundColor: '#1976d2' }}>
            <TableRow>
              <TableCell sx={{ color: 'white' }}>Product ID</TableCell>
              <TableCell sx={{ color: 'white' }}>Name</TableCell>
              <TableCell sx={{ color: 'white' }}>Category</TableCell>
              <TableCell sx={{ color: 'white' }}>Price (₹)</TableCell>
              <TableCell sx={{ color: 'white' }}>Stock</TableCell>
              <TableCell sx={{ color: 'white' }}>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.map((product) => (
              <TableRow key={product.productId}>
                <TableCell>{product.productId}</TableCell>
                <TableCell>{product.productName}</TableCell>
                <TableCell>{categories.find(cat => cat.categoryId === product.categoryId)?.categoryName || 'Unknown'}</TableCell>
                <TableCell>₹{Number(product.price).toFixed(2)}</TableCell>
                <TableCell>
                    {product.stockQuantity > 0 ? (
                    <Chip label={`In Stock (${product.stockQuantity})`} color="success" size="small" />
                  ) : (
                    <Chip label="Out of Stock" color="error" size="small" />
                  )}
                </TableCell>
                <TableCell>
                  <Tooltip title="Edit Product">
                    <IconButton color="primary" onClick={()=>handleOpenDialog(product)}>
                      <EditIcon />
                    </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete Product">
                    <IconButton color="error" onClick={() => handleDelete(product.productId)}>
                      <DeleteIcon /> {/* use DeleteIcon instead of InventoryIcon for clarity */}
                    </IconButton>
                  </Tooltip>
                  <Tooltip title="Manage Stock">
                    <IconButton color="secondary" onClick={()=> handleStockUpdate(product.productId)}>
                      <InventoryIcon />
                    </IconButton>
                  </Tooltip>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Dialog
        open={openDialog}
        onClose={handleCloseDialog}
        fullWidth
        maxWidth="sm"
       >
        <DialogTitle>{editMode ? 'Edit Product' : 'Add Product'}</DialogTitle>

        <DialogContent>
          <TextField
            margin="dense"
            name="productName"
            label="Product Name"
            fullWidth
            value={currentProduct.productName}
            onChange={handleInputChange}
          />

          <TextField
            margin="dense"
            name="price"
            label="Price"
            type="number"
            fullWidth
            value={currentProduct.price}
            onChange={handleInputChange}
          />

          <TextField
            margin="dense"
            name="imageUrl"
            label="Image URL"
            fullWidth
            value={currentProduct.imageUrl}
            onChange={handleInputChange}
          />

          <TextField
            fullWidth
            label="Stock Quantity"
            name="stockQuantity"
            type="number"
            value={currentProduct.stockQuantity}
            onChange={handleInputChange}
            margin="normal"
          />
        </DialogContent>

        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button
            onClick={handleSaveProduct}
            variant="contained"
            color="primary"
          >
            {editMode ? 'Update' : 'Save'}
          </Button>
        </DialogActions>
      </Dialog>
      </Box>
    </Box>
  );
}
