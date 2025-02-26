import axios from "axios";




const baseURL = window.env?.REACT_APP_API_BASE_URL || "http://localhost:8080";
console.log("Base URL:", baseURL);

const apiUrl = `${baseURL}/api/products`;

export const getProducts = async () => {
  try {
    const response = await axios.get(apiUrl);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const createProduct = async (product) => {
  try {
    const response = await axios.post(apiUrl, product);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const getProductById = async (id) => {
  try {
    const response = await axios.get(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const updateProductById = async (id, product) => {
  try {
    const response = await axios.put(`${apiUrl}/${id}`, product);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const deleteProductById = async (id) => {
  try {
    const response = await axios.delete(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}