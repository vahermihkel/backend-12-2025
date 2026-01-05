import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import type { Product } from "../../models/Product";
import { Category } from "../../models/Category";

const backendUrl = import.meta.env.VITE_API_HOST;

function ProductEdit() {
  // useParams --> võtab URLi parameetreid
  // <Route path='/product/:product_id' element={<ProductDetails/>} />
  const {product_id} = useParams();
  const [product, setProduct] = useState<Product>();
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch(`${backendUrl}/categories`) 
      .then(res => res.json())
      .then(json => setCategories(json))
  }, []);

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
      <div>Ajutine kuvamine: {JSON.stringify(product)}</div>
      <label>ID</label> <br />
      <input value={product.id} disabled type="text" /> <br />
      <label>Name</label> <br />
      <input value={product.name} onChange={(e) => setProduct({...product, name: e.target.value})} type="text" /> <br />
      <label>Price</label> <br />
      <input value={product.price} onChange={(e) => setProduct({...product, price: Number(e.target.value)})} type="number" /> <br />
      <label>Stock</label> <br />
      <input value={product.stock} onChange={(e) => setProduct({...product, stock: Number(e.target.value)})} type="number" /> <br />
      <label>Active</label> <br />
      <input checked={product.active} onChange={(e) => setProduct({...product, active: e.target.checked})} type="checkbox" /> <br />
      <label>Category</label> <br />
      {/* <input value={product.name} onChange={(e) => setProduct({...product, category: e.target.value})} type="text" /> <br /> */}
      <select value={product.category?.id} 
        onChange={(e) => setProduct({...product, category: {id: Number(e.target.value), name: ""}})}>
        {categories.map(category => 
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        )}
      </select> <br />
      <button>Edit</button>


      {/* <div>{product.id}</div> 
      <div>{product.name}</div>
      <div>{product.price}€</div>
      <div>Laos: {product.stock}tk</div>
      <div>{product.active ? "Aktiivne" : "Mitteaktiivne"}</div>
      <div>Kategooria: {product.category?.name}</div> */}

    </div>
  )
}

export default ProductEdit