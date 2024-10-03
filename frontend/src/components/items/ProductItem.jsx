import React, { useState } from "react";
import { Link } from "react-router-dom"; // Import Link for navigation
import { AddToCart, Share } from "../buttons/Button";

const ProductItem = ({ product }) => {
  return (
    <div>
      <Link to={`/products/${product.productId}`} className="block">
        <div className="max-w-sm rounded overflow-hidden shadow-lg bg-white group relative">
          {/* Image */}
          <img
            className="w-full h-64 object-cover"
            src={product.productImage}
            alt={product.productName}
          />

          {/* Product Info */}
          <div className="p-4">
            <h3 className="text-lg font-bold text-gray-900">
              {product.productName}
            </h3>
            <p className="text-gray-700 text-base mb-2">
              ${product.productPrice.toFixed(2)}
            </p>

            {/* Star Rating */}
            <div className="flex items-center mb-4">
              <span className="text-yellow-400">
                {"★".repeat(Math.floor(product.productRating))}
                {"☆".repeat(5 - Math.floor(product.productRating))}
              </span>
              <span className="ml-2 text-gray-600">
                ({product.totalRating})
              </span>
            </div>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default ProductItem;
