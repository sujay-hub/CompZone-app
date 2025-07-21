import axios from 'axios';
const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    withCredentials: true, 
    headers: {
      'Content-Type': 'application/json',
    },
  });

  api.interceptors.request.use((config) => {
      const publicRoutes = [
        {method: 'get', pathPrefix: '/products'},
        {method: 'get', pathPrefix: '/categories'},
        {method: 'post', pathPrefix: '/login'},
        {method: 'post', pathPrefix: '/register'}
      ];

      const isPublic = publicRoutes.some(
        (route) => 
          config.method === route.method &&
          config.url?.includes(route.pathPrefix)
      );
      
      if(!isPublic) {
      const token = localStorage.getItem('token');
      if(token){
        config.headers.Authorization = `Bearer ${token}`;
        }
      }
      return config;
    },
    (error) => Promise.reject(error)
  );


  export default api;