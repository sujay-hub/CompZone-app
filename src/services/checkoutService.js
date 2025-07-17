import api from "./api/axios";

/**
 * @param {{ userId: number, cartItems: { productId: number, quantity: number }[] }} orderRequest
 */
export const placeOrder = async (orderRequest) => {
    return api.post('/place-order', orderRequest);
  };