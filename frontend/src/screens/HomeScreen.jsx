import React, { useEffect, useState } from 'react';
import ProductCard from '../Components/ProductCard';
import { Grid, Typography, Box, Container, CircularProgress, FormControl, InputLabel, MenuItem, Select, Pagination } from "@mui/material";
import { getAllProducts } from '../services/productService';
import { getUserCart } from '../services/cartService';
import { getAllCategories } from '../services/categoryService';
import NavBar from '../Components/NavBar';
import { fetchPaginatedProducts } from '../services/productService';

function HomeScreen() {
const [products, setProducts] = useState([]);
const [categories, setCategories] = useState([]);
const [selectedCategory, setSelectedCategory] = useState('');
const [page, setPage] = useState(0);
const [totalPages, setTotalPages] = useState(0);
const [loading, setLoading] = useState(true);

  // useEffect(() => {
  //   axios.get('http://localhost:8080/products')
  //     .then((res) => setProducts(res.data))
  //     .catch((err) => console.error('Error fetching products:', err));
  // }, []); Direct axios expose
//   useEffect(() => {
//   getAllProducts()
//     .then((res) => {
//       setProducts(res.data);
//       setLoading(false);
//     })
//     .catch((err) => {
//       console.error('Error fetching products:', err);
//       setLoading(false);
//     });
// }, []);

  useEffect(()=>{
    getAllCategories()
      .then((res) => setCategories(res.data))
      .catch((err) => console.error('Error fetching categories:', err));
  }, []);

  //Cart config for Navbar
  const [cartCount, setCartCount] = useState(0);
  useEffect(()=>{
    const token = localStorage.getItem("token");
    if(token){
    getUserCart()
    .then(res=>{
      const items = res.data;
      const count = items.reduce((sum, item) => sum + item.quantity, 0); //total quantity
      setCartCount(count);
    })
    .catch(err => {
      console.error("Failed to fetch cart count:", err);
    });
   }
   else{
    setCartCount(0);
   }
  }, []);

  const handleCategoryChange = async (event) => {
     const categoryId = event.target.value;
     setSelectedCategory(categoryId);
     setPage(0); // Reset to first page when category changes
  };

  const fetchData = async (pageNumber, categoryId = '') => {
  setLoading(true);
  try {
    const res = await fetchPaginatedProducts(pageNumber, 5, categoryId);
    setProducts(res.data.content); // from backend Page<Products>
    setTotalPages(res.data.totalPages);
  } catch (err) {
    console.error('Error fetching paginated products:', err);
  } finally {
    setLoading(false);
  }
};

useEffect(() => {
  fetchData(page, selectedCategory);
}, [page, selectedCategory]);

const handlePageChange = (event, newPage) => {
  setPage(newPage - 1); // Pagination is 1-based on UI
};

  return (
    <>
    <NavBar cartCount={cartCount}/>
    <Box
    sx={{
       paddingTop: '2px',
      width: '100vw',
      minHeight: '50vh',
      backgroundImage:
        'url(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPdAIWtBsarRLR7POa5LniU5yf-uCbFh2xGQ&s)',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center',
      backgroundAttachment: 'fixed',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }}
  >
    
    <Box
      sx={{
        position: 'absolute',
        left: 0,
        width: '95.4vw',
      //height: '100vh',
      backgroundImage:
        'url(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPdAIWtBsarRLR7POa5LniU5yf-uCbFh2xGQ&s)',
        backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundAttachment: 'fixed',
        backgroundColor: 'rgba(255,255,255,0.95)',
        overflowY: 'scroll', // always scroll, even if content fits
        padding: '100px',
  
        // ✅ Ensure scrollbar is visible and styled
        //scrollbarWidth: 'auto', // for Firefox
        '&::-webkit-scrollbar': {
          width: '12px', // increase width
          display: 'block', // ensure it's not hidden
        },
        // '&::-webkit-scrollbar-track': {
        //   background: '#f1f1f1',
        // },
        '&::-webkit-scrollbar-thumb': {
          backgroundColor: '#888',
          borderRadius: '6px',
          border: '3px solid #f1f1f1',
        },
        '&::-webkit-scrollbar-thumb:hover': {
          backgroundColor: '#555',
        },
      }}
    >
      <Typography variant="h4" align="center" gutterBottom color="black">
        Available Products
      </Typography>
      <FormControl fullWidth sx = {{maxWidth: 300, mb: 3, zIndex: 1500}}>
        <InputLabel id = "category-select-label">Filter by Category</InputLabel>
        <Select
        labelId = 'category-select-label'
        value = {selectedCategory}
        label= 'Filter by Category'
        onChange = {handleCategoryChange}
        >
          <MenuItem value="">All Categories</MenuItem>
         {categories.map((cat) => (
          <MenuItem key={cat.categoryId} value={cat.categoryId}>
            {cat.categoryName}
          </MenuItem>
        ))}
          
        </Select>
      </FormControl>
      
      {loading ? (
  <Box display="flex" justifyContent="center" mt={4}>
    <CircularProgress />
  </Box>
) : (
  <>
    <Grid container spacing={4} justifyContent="center">
      {products.map((product) => (
        <Grid item key={product.productId} xs={12} sm={6} md={4}>
          <ProductCard product={product} />
        </Grid>
      ))}
    </Grid>

    <Box mt={4} display="flex" justifyContent="center">
      <Pagination
        count={totalPages}
        page={page + 1}
        onChange={handlePageChange}
        color="primary"
      />
    </Box>
  </>
)}
    </Box>
  </Box>
  </>
  );
}

export default HomeScreen;
