import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { BiSolidHide, BiSolidShow } from "react-icons/bi";
import { FcGoogle } from "react-icons/fc";

import { HR } from "flowbite-react";
import { registerUser } from "../../service/authService";
import { GoogleLoginButton } from "../buttons/Button";

export function RegisterForm() {
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
      await registerUser(data, navigate);
    } catch (error) {
      console.error("Error during login:", error.message);
    }
  };
  const handleRememberMeChange = () => {
    setRememberMe((prev) => !prev);
  };

  return (
    <div>
      <h2 className="font-poppins text-5xl mb-6 text-left">Register</h2>{" "}
      {/* Increase font size */}
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
        <div>
          <label className="block mb-2 text-gray-500 text-xl"> Username</label>
          <input
            type="text"
            tabIndex={1}
            className="w-full p-3 border border-gray-300 rounded-lg focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg" // Increase padding and font size
            {...register("username", { required: "Username is required" })}
          />
          {errors.username && (
            <p className="text-red-500 text-sm">{errors.username.message}</p>
          )}
        </div>
        <div>
          <label className="block mb-2 text-gray-500 text-xl"> Full name</label>
          <input
            type="text"
            tabIndex={1}
            className="w-full p-3 border border-gray-300 rounded-lg focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg" // Increase padding and font size
            {...register("fullname", { required: "Full name is required" })}
          />
          {errors.fullname && (
            <p className="text-red-500 text-sm">{errors.fullname.message}</p>
          )}
        </div>
        <div>
          <label className="block mb-2 text-gray-500 text-xl"> Email</label>
          <input
            type="email"
            tabIndex={1}
            className="w-full p-3 border border-gray-300 rounded-lg focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg" // Increase padding and font size
            {...register("email", { required: "Email is required" })}
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
        <div className="agreeTerm flex flex-row">
          <input type="checkbox" id="rememberMe" required className="mr-2" />
          <p className="text-left text-xl">
            By continuting, you argee to the{" "}
            <Link className="underline font-semibold">Term of use</Link> and{" "}
            <Link className="underline font-semibold">Privacy Policy</Link>{" "}
          </p>
        </div>
        <button
          type="submit"
          tabIndex={3}
          className="w-full bg-gray-200 text-white p-4 rounded-full text-xl 
             hover:bg-black hover:text-white 
             transition-colors duration-300 ease-in-out"
        >
          Login
        </button>
      </form>
      <div className="otherLogin flex flex-col pt-10">
        <HR.Text
          className="inline-flex w-full items-center font-montserrat justify-center px-3 dark:border-gray-300"
          text="Or"
        />
        <div className="loginWithGoogle flex pt-10 justify-center">
          <GoogleLoginButton />
        </div>
      </div>
    </div>
  );
}
