import React, { useEffect, useState } from 'react';
import {
  Box,
  Typography,
  TextField,
  Button,
  IconButton,
  List,
  ListItem,
  ListItemText
} from '@mui/material';
import Edit from '@mui/icons-material/Edit';
import Delete from '@mui/icons-material/Delete';
import {
  getAllCategories,
  createCategory,
  updateCategory,
  deleteCategory
} from '../../services/categoryService';
import { useNavigate } from 'react-router-dom';

export default function ManageCategoriesScreen() {
  const [categories, setCategories] = useState([]);
  const [newCategory, setNewCategory] = useState('');
  const [newDescription, setNewDescription] = useState('');
  const [editId, setEditId] = useState(null);
  const navigate = useNavigate();

  const handleGoHome = () =>{
    navigate('/admin')
  }

  const fetchCategories = () => {
    getAllCategories()
      .then((res) => setCategories(res.data))
      .catch((err) => console.error('Failed to fetch categories:', err));
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleAdd = () => {
    if (!newCategory.trim()) return;

    if (editId !== null) {
      updateCategory(editId, { 
        categoryId: editId,
        categoryName: newCategory,
        description: newDescription
       })
        .then(() => {
          fetchCategories();
          setEditId(null);
          setNewCategory('');
          setNewDescription('');
        })
        .catch((err) => console.error('Update error:', err));
    } else {
      createCategory({ categoryName: newCategory, description: newDescription })
        .then(() => {
          fetchCategories();
          setNewCategory('');
          setNewDescription('');
        })
        .catch((err) => console.error('Create error:', err));
    }
  };

  const handleEdit = (categoryId, categoryName, description) => {
    setNewCategory(categoryName);
    setNewDescription(description)
    setEditId(categoryId);
  };

  const handleDelete = (categoryId) => {
    deleteCategory(categoryId)
      .then(() => fetchCategories())
      .catch((err) => console.error('Delete error:', err));
  };

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
          width: '30vw',
          
          bgcolor: 'rgba(255,255,255,0.95)',
          borderRadius: 4,
          p: 4,
          overflowY: 'auto',
          
          boxShadow: 5,
          position: 'absolute',
          left:8,
          top:8
        }}
      >
        <Box sx={{
                      display: 'flex',
                      justifyContent: 'space-between',
                      alignItems: 'baseline',
                    }}>
        <Typography variant="h5" gutterBottom textAlign="center" sx={{color: 'black'}}>
          Manage Product Categories
        </Typography>
        <Button sx={{pt: '3px'}} size="small" variant="contained" color="primary" onClick={handleGoHome}>
          Home
        </Button>
        </Box>
        <Box display="flex" gap={2} mt={2}>
          
          <TextField
            fullWidth
            label={editId !== null ? 'Edit Category' : 'New Category'}
            value={newCategory}
            onChange={(e) => setNewCategory(e.target.value)}
          />
          <TextField
            fullWidth
            label={editId !== null ? 'Edit Category' : 'New Description'}
            value={newDescription}
            onChange={(e) => setNewDescription(e.target.value)}
          />
          <Button variant="contained" onClick={handleAdd}>
            {editId !== null ? 'Update' : 'Add'}
          </Button>
        </Box>

        <List sx={{ mt: 3, color: 'black' }}>
          {categories.map((cat) => (
            <ListItem
              key={cat.categoryId}
              sx={{
                bgcolor: '#f5f5f5',
                mb: 1,
                borderRadius: 2,
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
              }}
            >
              <ListItemText primary={cat.categoryName} />
              <Box>
                <IconButton onClick={() => handleEdit(cat.categoryId, cat.categoryName, cat.description)}>
                  <Edit />
                </IconButton>
                <IconButton onClick={() => handleDelete(cat.categoryId)}>
                  <Delete color="error" />
                </IconButton>
              </Box>
            </ListItem>
          ))}
        </List>
      </Box>
    </Box>
  );
}