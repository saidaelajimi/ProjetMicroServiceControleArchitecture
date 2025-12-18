import axios from 'axios';

// Create an Axios instance with default config
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Gateway URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add a request interceptor (useful for auth tokens later)
api.interceptors.request.use(
    (config) => {
        // const token = localStorage.getItem('token');
        // if (token) {
        //   config.headers.Authorization = `Bearer ${token}`;
        // }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;
