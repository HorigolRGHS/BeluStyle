import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { BiSolidHide, BiSolidShow } from "react-icons/bi";
import { FcGoogle } from "react-icons/fc";

import { HR } from "flowbite-react";
import { loginUser } from "../../service/authService";
import { GoogleLoginButton, LoginBtn } from "../buttons/Button";

export function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const [showPassword, setShowPassword] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);
  const navigate = useNavigate();

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const onSubmit = async (data) => {
    try {
      await loginUser(data, navigate); 
    } catch (error) {
      console.error("Error during login:", error.message);
    }
  };
  const handleRememberMeChange = () => {
    setRememberMe((prev) => !prev);
  };

  return (
    <div>
      <h2 className="font-poppins text-5xl mb-6 text-left">Login</h2>{" "}
      {/* Increase font size */}
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
        <div>
          <label className="block mb-2 text-gray-500 text-xl">
            {" "}
            {/* Increase font size */}
            Email or username
          </label>
          <input
            type="text"
            tabIndex={1}
            className="w-full p-3 border border-gray-300 rounded-lg focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg" // Increase padding and font size
            {...register("username", { required: "is required" })}
          />
          {errors.email && (
            <p className="text-red-500 text-sm">{errors.email.message}</p>
          )}
        </div>
        <div>
          <div className="flex items-center justify-between mb-2">
            <label className="text-xl text-gray-500">Password</label>{" "}
            {/* Increase font size */}
            <button
              type="button"
              className="flex items-center text-gray-500 text-xl"
              onClick={toggleShowPassword}
            >
              {showPassword ? (
                <>
                  <BiSolidHide className="mr-1 text-3xl text-gray-500" />{" "}
                  {/* Icon for hiding password */}
                  Hide {/* Text for hiding password */}
                </>
              ) : (
                <>
                  <BiSolidShow className="mr-1 text-3xl text-gray-500" />{" "}
                  {/* Icon for showing password */}
                  Show {/* Text for showing password */}
                </>
              )}
            </button>
          </div>
          <input
            type={showPassword ? "text" : "password"}
            tabIndex={2}
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg text-gray-700" // Increase padding and font size
            {...register("password", { required: "Password is required" })}
          />
          {errors.password && (
            <p className="text-red-500 text-sm">{errors.password.message}</p>
          )}
        </div>
        <div className="flex items-center">
          <input
            type="checkbox"
            id="rememberMe"
            checked={rememberMe}
            onChange={handleRememberMeChange}
            className="mr-2"
          />
          <label htmlFor="rememberMe" className="text-gray-500 text-xl">
            Remember Me
          </label>
        </div>
        <p className="text-left text-xl">
          By continuting, you argee to the{" "}
          <Link className="underline font-semibold">Term of use</Link> and{" "}
          <Link className="underline font-semibold">Privacy Policy</Link>{" "}
        </p>
        <LoginBtn tabindex="3"></LoginBtn>
      </form>
      <div className="OtherFeature pt-10 flex justify-center">
        <Link to="/forgotPassword">
          <p className="font-semibold font-montserrat underline">
            Forgot password?
          </p>
        </Link>
      </div>
      <div className="register flex justify-center pt-5">
          <p className="font font-montserrat">Don't have an account? <Link to="/register" className="underline font-medium font-montserrat">Sign up</Link></p>
        </div>
      <div className="otherLogin flex flex-col pt-10">
        <HR.Text
          className="inline-flex w-full items-center font-montserrat justify-center px-3 dark:border-gray-300"
          text="Or continue with"
        />
        <div className="loginWithGoogle flex pt-10 justify-center">
          <GoogleLoginButton/>
        </div>
      </div>
    </div>
  );
}
