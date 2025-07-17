import api from "./api/axios";
import { jwtDecode } from "jwt-decode"

/**
 * @typedef {Object} CartItem
 * @property {number} productId
 * @property {string} productName
 * @property {number} price
 * @property {number} quantity
 * @property {string} imageUrl
 */

/**
 * Fetches cart items for the current user.
 * @returns {Promise<{ data: CartItem[] }>}
 */
export const getUserCart = () =>{
   const token = localStorage.getItem("token");
   if(!token){
    throw new Error("Unauthorized");
   }
 try{  
   const decoded = jwtDecode(token);
   //console.log("Sujay token",token);
   
   return api.get('/cart',{
    headers: { Authorization: `Bearer ${token}`}
   });
 }
 catch(err){
    console.error("Invalid token", err);
    throw new Error("Invalid token");
 }
    
}

/**
 * Extracts userId from token stored in localStorage
 * @returns {string | number} userId
 */
export const getUserId = () => {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Unauthorized");
    const decoded = jwtDecode(token);
    return decoded.userId;
  };

/**
 * Adds a product to the cart.
 * @param {Object} requestBody
 * @param {number} requestBody.userId
 * @param {number} requestBody.productId
 * @param {number} requestBody.quantity
 * @returns {Promise<{ data: string }>}
 */
export const addToCart = (cartItem) => {
    const token = localStorage.getItem('token');
    const userId = getUserId();
    return api.post('/cart/add', { ...cartItem, userId }, {
        headers: { Authorization: `Bearer ${token}`}
    });
}

/**
 * Updates quantity of a cart item.
 * @param {Object} requestBody
 * @param {number} requestBody.userId
 * @param {number} requestBody.productId
 * @param {number} requestBody.quantity
 * @returns {Promise<{ data: string }>}
 */
export const updateCartQuantity = (updateData) => {
    const token = localStorage.getItem('token');
    const userId = getUserId();
    return api.put('/update', { ...updateData, userId },{
        headers: { Authorization: `Bearer ${token}`}
    });
}

/**
 * Removes a product from cart.
 * @param {Object} requestBody
 * @param {number} requestBody.userId
 * @param {number} requestBody.productId
 * @returns {Promise<{ data: string }>}
 */
export const removeFromCart = (removeData) => {
    const token = localStorage.getItem('token');
    const userId = getUserId();
    return api.delete('/delete', {
        headers: { Authorization: `Bearer ${token}`},
        data: { ...removeData, userId }
    });
   
}

/**
 * Clears the entire cart for the logged-in user.
 * @returns {Promise<{ data: string }>}
 */
export const clearCart = () => {
    const token = localStorage.getItem('token');
    return api.delete('/clear', {
        headers: { Authorization: `Bearer ${token}`}
    });
}


