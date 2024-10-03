import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

export const loginUser = async (data, navigate) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/auth/login",
      {
        username: data.username,
        passwordHash: data.password,
      }
    );

    const userData = {
      token: response.data,
    };

    localStorage.setItem("user", JSON.stringify(userData));

    handleLogin(navigate);
    alert(response.data.message || "Login successful!");

    return userData;
  } catch (error) {
    if (error.response) {
      console.error("Login failed", error.response.data);
      alert(error.response.data);
      throw new Error(error.response.data); 
    } else {
      console.error("Login failed", error);
      alert("An error occurred. Please try again.");
      throw new Error("An error occurred. Please try again."); 
    }
  }
};

export const registerUser = async (data, navigate) => {
  console.log(data);
  try {
    const response = await axios.post(
      "http://localhost:8080/api/auth/register",
      {
        username: data.fullname,
        email: data.email,
        passwordHash: data.password,
      }
    );

    const userData = {
      token: response.data,
    };

    localStorage.setItem("user", JSON.stringify(userData));

    handleLogin(navigate);
    alert(response.data.message || "Login successful!");

    return userData;
  } catch (error) {
    if (error.response) {
      console.error("Login failed", error.response.data);
      alert(error.response.data);
      throw new Error(error.response.data); 
    } else {
      console.error("Login failed", error);
      alert("An error occurred. Please try again.");
      throw new Error("An error occurred. Please try again."); 
    }
  }
};

export const handleLogin = (navigate) => {
  const user = localStorage.getItem("user");
  console.log(user);

  if (user) {
    try {
      const parsedUser = JSON.parse(user); 
      const token = parsedUser.token; 

      if (token) {
        const decoded = jwtDecode(token); 
        console.log("Decoded token:", decoded);


        if (decoded.role !== null) {
          navigate('/'); 
        }
      } else {
        console.log("Token not found in user object");
      }
    } catch (error) {
      console.error("Invalid token or user data", error);
    }
  } else {
    console.log("No user found in local storage");
  }
};