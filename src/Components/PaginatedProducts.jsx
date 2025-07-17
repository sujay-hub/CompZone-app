import React from 'react'
import {
    Box,
    CircularProgress,
    Grid,
    Pagination,
    Typography,
    Card,
    CardContent
  } from '@mui/material';
  import { fetchPaginatedProducts } from '../services/productService'
  import { useState, useEffect } from 'react';
  import axios from '../services/api/axios';

const PaginatedProducts = ({ products, totalPages, page, onPageChange }) => {
  // const [products, setProducts] = useState([]);
  // const [page, setPage] = useState(0); // Backend is 0-based
  // const [totalPages, setTotalPages] = useState(0);
  // const [loading, setLoading] = useState(true);

  // const fetchData = async (pageNumber) => {
  //   setLoading(true);
  //   try {
  //     const res = await fetchPaginatedProducts(pageNumber);
  //     setProducts(res.data.content); // content holds products
  //     setTotalPages(res.data.totalPages);
  //     setLoading(false);
  //   } catch (error) {
  //     console.error('Error fetching paginated products:', error);
  //     setLoading(false);
  //   }
  // };
  // useEffect(() => {
  //   fetchData(page);
  // }, [page]);

  // const handlePageChange = (event, value) => {
  //   setPage(value - 1); // UI uses 1-based, backend is 0-based
  // };

  return (
    <>
      <Grid container spacing={3}>
        {products.map((product) => (
          <Grid item xs={12} sm={6} md={4} key={product.productId}>
            <Card>
              <CardContent>
                <Typography variant="h6">{product.productName}</Typography>
                <Typography variant="body2" color="text.secondary">
                  ₹{Number(product.price).toFixed(2)}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Box mt={4} display="flex" justifyContent="center">
        <Pagination
          count={totalPages}
          page={page + 1} // frontend 1-based
          onChange={(e, value) => onPageChange(value - 1)} // backend 0-based
          color="primary"
        />
      </Box>
    </>
  )
}

export default PaginatedProducts