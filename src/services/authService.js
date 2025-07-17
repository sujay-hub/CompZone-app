import api from "./api/axios";

/**
 * @typedef {Object} LoginRequest
 * @property {string} email
 * @property {string} password
 */

/**
 * @typedef {Object} LoginResponse
 * @property {string} token
 */

/**
 * Sends login request to the backend.
 * @param {LoginRequest} credentials
 * @returns {Promise<{ data: LoginResponse }>}
 */
export const login = async (credentials) => {
    return api.post('/login', credentials);
  };

export const registerUser = (data) => {
    return axios.post('/api/auth/register', data);
  };

export const getUserInitial = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const emailOrName = payload.sub || payload.email;
    return emailOrName ? emailOrName[0].toUpperCase() : null;
  } catch (err) {
    return null;
  }
};