import React from "react";
import { FaCartPlus, FaShareAlt } from 'react-icons/fa'; // Import icon cho nút
import { FcGoogle } from "react-icons/fc";

export function AddToCart({onClick, tabindex = "0"}) {
    return(
            <button onClick={onClick} tabIndex={tabindex} className="bg-white text-blue-500 font-bold py-2 px-4 rounded flex items-center mx-2 hover:bg-gray-50 transition-opacity duration-300"><FaCartPlus className="mr-2" />Add to cart</button>
    );
}

export function Share({onClick, tabindex = "0"}) {
    return (
        <button onClick={onClick} tabIndex={tabindex} className="bg-transparent text-white font-bold py-2 px-4 rounded flex items-center mx-2 transition-opacity duration-300 hover:bg-zinc-500 bg-opacity-20"><FaShareAlt className="mr-2" /> Share</button>
    );
}

export function ShowMore({onClick, tabindex = "0"}) {
    return(
        <button onClick={onClick} tabIndex={tabindex} className="bg-white text-blue-700 font-bold border border-blue-700 rounded-lg py-2 px-4 flex items-center hover:bg-blue-700 hover:text-white transition duration-300">
  Show More
</button>
    );
}

export function LoginBtn({onClick, tabindex = "0"}) {
    return(
        <button type="submit" onClick={onClick} tabIndex={tabindex} className="w-full bg-gray-200 text-white p-4 rounded-full text-xl hover:bg-black hover:text-white transition-colors duration-300 ease-in-out">
  Login
</button>
    );
}

export function ForgotBtn({onClick, tabindex = "0"}) {
  return(
      <button type="submit" onClick={onClick} tabIndex={tabindex} className="w-full bg-gray-200 text-white p-4 rounded-full text-xl hover:bg-black hover:text-white transition-colors duration-300 ease-in-out">
Reset password
</button>
  );
}


export function GoogleLoginButton() {
    const handleGoogleLogin = () => {
      window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };
  
    return (
      <button onClick={handleGoogleLogin} className="google-login-button">
        <FcGoogle className="icon text-3xl" />
      </button>
    );
  }
