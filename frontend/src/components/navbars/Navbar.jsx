import { Link, useNavigate } from "react-router-dom";
import React, { useEffect, useRef, useState } from "react";
import logo from "../../assets/images/logo.png";
import userdefault from "../../assets/images/userdefault.svg";
import GuessMenu from "../menus/GuessMenu";
import { jwtDecode } from "jwt-decode";
import { IoCartOutline, IoSearchOutline } from "react-icons/io5";
import { CiUser } from "react-icons/ci";

export function Navbar() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const token = localStorage.getItem("user");

    if (token) {
      try {
        const parsedUser = JSON.parse(token);
        const decoded = jwtDecode(parsedUser.token);

        const username = decoded.name || "User";
        const avatar = decoded.user_image || userdefault;

        setUser({ username, avatar });
      } catch (error) {
        console.error("Failed to decode token", error);
      }
    }
  }, []);

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsMenuOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <nav className="bg-white p-1">
      <div className="navitem grid grid-cols-3 items-center">
        <div className="nar-left flex items-center justify-start pl-10">
          <img src={logo} alt="" className="logo w-20" />
          <h2 className="name font-montserrat font-bold text-3xl">BeluStyle</h2>
        </div>
        <div className="nar-center justify-center flex">
          <ul className="flex space-x-20 items-center">
            <li>
              <Link
                to="/"
                className="text-black hover:text-gray-400 font-poppins transition duration-300"
              >
                Home
              </Link>
            </li>
            <li>
              <Link
                to="/shop"
                className="text-black hover:text-gray-400 font-poppins transition duration-300"
              >
                Shop
              </Link>
            </li>
            <li>
              <Link
                to="/about"
                className="text-black hover:text-gray-400 font-poppins transition duration-300"
              >
                About
              </Link>
            </li>
            <li>
              <Link
                to="/contact"
                className="text-black hover:text-gray-400 font-poppins transition duration-300"
              >
                Contact
              </Link>
            </li>
          </ul>
        </div>
        <div className="navinfo items-center justify-end flex space-x-10 pr-10">
        <div className="relative" ref={dropdownRef}>
            {/* Avatar icon to toggle the menu */}
            <div
              className="cursor-pointer hover:text-gray-700 transition duration-300"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              <CiUser className="text-4xl" />
            </div>

            {/* Render the GuessMenu */}
            <GuessMenu isMenuOpen={isMenuOpen} />
          </div>
          <div className="searchBtn hover:text-gray-700 transition duration-300">
            <button>
              <IoSearchOutline className="text-3xl" />
            </button>
          </div>
          <div className="cartBtn hover:text-gray-700 transition duration-300">
            <button>
            <IoCartOutline className="text-3xl" />
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
