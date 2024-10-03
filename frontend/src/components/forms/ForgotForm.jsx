import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";

import { loginUser } from "../../service/authService";
import { ForgotBtn } from "../buttons/Button";

export function ForgotForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();


  const onSubmit = async (data) => {
    try {
      await loginUser(data, navigate); 
    } catch (error) {
      console.error("Error during login:", error.message);
    }
  };

  return (
    <div>
      <h2 className="font-poppins text-5xl mb-6 text-left">Forgot password</h2>{" "}
      <p className="font-poppins text-1xl mb-6 text-left text-gray-600">Please enter your email to reset the password</p>
      {/* Increase font size */}
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
        <div>
          <label className="block mb-2 text-gray-500 text-xl">
            {" "}
            {/* Increase font size */}
            Your email
          </label>
          <input
            type="text"
            tabIndex={1}
            className="w-full p-3 border border-gray-300 rounded-lg focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all duration-200 outline-none text-lg" // Increase padding and font size
            {...register("email", { required: "Email is required" })}
          />
          {errors.email && (
            <p className="text-red-500 text-sm">{errors.email.message}</p>
          )}
        </div>
        <ForgotBtn tabindex="2"></ForgotBtn>
      </form>
    </div>
  );
}
