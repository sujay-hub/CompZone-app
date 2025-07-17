import { useState } from 'react'
import { BrowserRouter, Routes, Route} from "react-router-dom";
import './App.css'
import HomeScreen from "./screens/HomeScreen"
import CartScreen from "./screens/CartScreen"
import CheckoutScreen from "./screens/CheckoutScreen"
import OrderHistoryScreen from "./screens/OrderHistoryScreen"
import ProductDetailsScreen from "./screens/ProductDetailsScreen"
import AdminDashboard from './screens/admin/AdminDashboard';
import AllOrdersScreen from "./screens/admin/AllOrdersScreen"
import ManageCategoriesScreen from "./screens/admin/ManageCategoriesScreen"
import ManageProductsScreen from "./screens/admin/ManageProductsScreen"
import RegisterForm from "./Components/forms/RegisterForm"
import LoginScreen from './screens/LoginScreen';
import PaginatedProducts from './Components/PaginatedProducts';
import { Navigate } from 'react-router-dom';

function App() {
  
  const isAdmin = localStorage.getItem("ROLE") === "ADMIN";

  return (
    <>
     <BrowserRouter>
        <Routes>
          <Route path='/' element={<HomeScreen/>} />//✅1
          <Route path='/cart' element={<CartScreen/>} />//✅2
          <Route path='/checkout' element={<CheckoutScreen/>} />//✅3
          <Route path='/orders/history' element={<OrderHistoryScreen/>} />//✅4
          {/* <Route path='/products/id/:productId' element={<ProductDetailsScreen/>} />//✅2 */}
          <Route path='/product/:id' element={<ProductDetailsScreen/>} />//✅2 
          <Route path='/register' element={<RegisterForm/>} />//✅3
          <Route path='/login' element={<LoginScreen/>} />//✅2
          {/* Admin Screens below */}
          <Route path='/admin' element={<AdminDashboard/>}/>
          <Route path='/admin/orders' element={<AllOrdersScreen/>} />//✅
          <Route path='/admin/categories' element={<ManageCategoriesScreen/>} />//✅
          <Route path='/admin/products/:productId/stock' element={<ManageProductsScreen/>} />//✅
          {/* <Route path='/pagination' element={<PaginatedProducts/>} />//✅ */}
        </Routes>
     </BrowserRouter> 
    </>
  )
}

export default App
