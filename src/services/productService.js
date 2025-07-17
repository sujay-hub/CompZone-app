import api from "./api/axios";

/**
 * @returns {Promise<{ data: Product[] }>}
 */
export const getAllProducts = async () => {
    return api.get('/products'); // This hits the backend /products
  };
  
  /**
 * @param {number} productId
 * @returns {Promise<{ data: Product }>}
 */
  export const getProductById = async (productId) => {
    return api.get(`/products/${productId}`);
  };

  //admin screens
  export const createProduct = (productData) => api.post('/admin/products', productData);

export const updateProduct = (id, updatedData) =>
  api.put(`/admin/products/${id}`, updatedData);

export const updateProductStock = (productId, newStock, token) => {
  return api.put(`/admin/products/${productId}/stock?newStock=${newStock}`, // 👈 pass as query param
    {}, // 👈 no body
    {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }
  );
};


export const deleteProduct = (id) => api.delete(`/admin/products/${id}`);

export const fetchPaginatedProducts = (page, size = 5, categoryId = '') => {
  const params = new URLSearchParams({
    page,
    size,
    sortField: 'productName',
    sortDir: 'asc',
  });

  if (categoryId) {
    params.append('categoryId', categoryId);
  }

  return api.get(`http://localhost:8080/pagination?${params.toString()}`);
};