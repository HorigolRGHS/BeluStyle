import React from "react";
import { AiOutlineUser, AiOutlineUsergroupAdd } from "react-icons/ai";
import { RiLockLine } from "react-icons/ri";
import { Link } from "react-router-dom";

const GuessMenu = ({ isMenuOpen }) => {
  return (
    <div
      className={`absolute right-0 mt-2 w-56 bg-white shadow-lg rounded-lg z-50 transition-opacity duration-300 ease-in-out ${
        isMenuOpen ? "opacity-100 visible" : "opacity-0 invisible"
      }`}
    >
      <ul>
        <li>
          <Link
            to="/login"
            className="block px-4 py-2 text-gray-700 hover:bg-gray-100 items-center transition-transform duration-200 ease-in-out"
          >
            <div className="logintag flex items-center space-x-4">
              <AiOutlineUser className="text-2xl" />
              <p>Sign In</p>
            </div>
          </Link>
        </li>
        <li>
          <Link
            to="/register"
            className="block px-4 py-2 text-gray-700 hover:bg-gray-100 transition-transform duration-200 ease-in-out"
          >
            <div className="registertag flex items-center space-x-4">
              <AiOutlineUsergroupAdd className="text-2xl" />
              <p>Sign Up</p>
            </div>
          </Link>
        </li>
        <li>
          <Link
            to="/forgotPassword"
            className="block px-4 py-2 text-gray-700 hover:bg-gray-100 transition-transform duration-200 ease-in-out"
          >
            <div className="forgottag flex items-center space-x-4">
              <RiLockLine className="text-2xl" />
              <p>Forgot Password</p>
            </div>
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default GuessMenu;
