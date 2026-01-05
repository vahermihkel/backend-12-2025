import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import type { Product } from "../models/Product";

const backendUrl = import.meta.env.VITE_API_HOST;

function ProductDetails() {
  // useParams --> võtab URLi parameetreid
  // <Route path='/product/:product_id' element={<ProductDetails/>} />
  const {product_id} = useParams();
  const [product, setProduct] = useState<Product>();

  useEffect(() => {
      fetch(`${backendUrl}/products/${product_id}`) 
        .then(res => res.json())
        .then(json => setProduct(json)) // setProducts(json)
    }, [product_id]);

  if (product === undefined) {
    return <div>Product not found</div>
  }

  return (
    <div>
      <div>{product.id}</div> 
      <div>{product.name}</div>
      <div>{product.price}€</div>
      <div>Laos: {product.stock}tk</div>
      <div>{product.active ? "Aktiivne" : "Mitteaktiivne"}</div>
      <div>Kategooria: {product.category?.name}</div>

    </div>
  )
}

export default ProductDetails