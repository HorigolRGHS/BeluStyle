import ProductList from "../components/lists/ProductList";
import { ShowMore } from "../components/buttons/Button";
import MainLayout from "../layouts/MainLayout";

const products = [
  {
    productId: 1,
    productName: "Product 1",
    productPrice: 19.99,
    productRating: 4,
    totalRating: 100,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 2,
    productName: "Product 2",
    productPrice: 29.99,
    productRating: 5,
    totalRating: 200,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 3,
    productName: "Product 3",
    productPrice: 39.99,
    productRating: 3,
    totalRating: 150,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 4,
    productName: "Product 4",
    productPrice: 49.99,
    productRating: 4,
    totalRating: 300,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 5,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 6,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 7,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 8,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 9,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
  {
    productId: 10,
    productName: "Product 5",
    productPrice: 59.99,
    productRating: 5,
    totalRating: 50,
    productImage:
      "https://dosi-in.com/file/detailed/487/dosiin-downtown-ao-local-brand-chinh-hang-graffiti-downtown-tee-487675487675.jpg?w=1000&h=1000&fit=fill&fm=webp",
  },
];

export function Shop() {
  return (
    <MainLayout>
      <div className="productshow flex flex-col">
        <div classproductName="flex bg-gray-100">
          <ProductList products={products}></ProductList>
        </div>
        <ShowMore/>
      </div>
    </MainLayout>
  );
}
