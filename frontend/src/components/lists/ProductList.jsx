import React from 'react';
import ProductItem from '../items/ProductItem';

const ProductList = ({ products }) => {
  console.log(products);
  return (
    <div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-2 place-items-center">
      {products.map((product) => (
        <ProductItem key={product.productId} product={product} />
      ))}
    </div>
  );
};

export default ProductList;
