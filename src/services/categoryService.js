import api from "./api/axios";

export const getAllCategories = () => api.get('/categories');
export const createCategory = (data) => api.post('/create-category', data);
export const updateCategory = (categoryId, data) => api.put(`/update-category/id/${categoryId}`, data);
export const deleteCategory = (categoryId) => api.delete(`/delete-category/${categoryId}`);