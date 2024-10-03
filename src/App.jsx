// import { useState } from "react";
import React from "react";
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import { Home } from "./pages/Home";
import { Navbar } from "./components/navbars/Navbar";
import { Shop } from "./pages/Shop";
import { About } from "./pages/About";
import { Contact } from "./pages/Contact";
import { Login } from "./pages/Login";
import { NavLogin } from "./components/navbars/NavLogin";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";

function App() {
  const location = useLocation();
  // const state = useState
  const renderNavbar = () => {
    switch (window.location.pathname) {
      case "/":
      case "/shop":
        return <Navbar />;
      case "/about":
      case "/contact":
        return <Navbar />;
      case "/login":
      case "/register":
      case "/forgotPassword":
        return <NavLogin />;
      default:
        return null;
    }
  };
  return (
    <>
      {renderNavbar()}
      <div className="container">
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/shop" element={<Shop />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/forgotPassword" element={<ForgotPassword />} />
        </Routes>
      </div>
    </>
  );
}

// Đảm bảo App được bao bọc bởi BrowserRouter
const WrappedApp = () => (
  <BrowserRouter>
    <App />
  </BrowserRouter>
);

export default WrappedApp;
