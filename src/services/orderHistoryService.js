import api from "./api/axios";

/**
 * Fetch user's order history
 * @returns {Promise<{ data: Order[] }>}
 */
export const getOrderHistory = async()=>{
    return api.get('/orders/history')
}
