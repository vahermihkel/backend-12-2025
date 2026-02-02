// rfce ---> komponendi/lehe põhi
// uef ---> useEffect põhi


import { useContext, useEffect, useState } from 'react'
import { Product } from '../models/Product';
import { CartProduct } from '../models/CartProduct';
import { CartSumContext } from '../context/CartSumContext';
import { useDispatch } from 'react-redux';
import { increment } from '../store/counterSlice'
import { Link } from 'react-router-dom';

const backendUrl = import.meta.env.VITE_API_HOST;

function Products() {
  // useState --> Reacti erikood (Hook)
  // useState väljastab array: esimene väärtus on muutuja, teine on setter
  // setteril on eriomadused - kui see käivitatakse, siis muutub muutuja + renderdatakse HTML

  // const [count, setCount] = useState(1); 
  // const [count2, setCount2] = useState(10); // väärtused, mida rohkem ei muudeta HTMLs
  const [products, setProducts] = useState<Product[]>([]);
  const {increaseSum} = useContext(CartSumContext);
  const dispatch = useDispatch();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [sort, setSort] = useState("id,asc"); 
  const [totalElements, setTotalElements] = useState(0); 
  const [totalPages, setTotalPages] = useState(0); 


  /*
  // CONNECTION_REFUSED ---> backend ei tööta
  

  // fetch(URL) --> kuhu teise serverisse ma pöördun
  // res ---> KOGU TAGASTUS. headers. statusCode. jne
  // res.json() ---> mis kujul mul on returnitav väärtus.
  // json ---> MIDA SEE API ENDPOINT RETURNIB
 */

  /* VARIANT 2:
  // async function getProducts() {
  //   const res = await fetch("http://localhost:8080/products");
  //   const json = await res.json();
  //   console.log(json); // setProducts(json)
  // }
  // getProducts(); */

  // VANA REACT: useComponentDidMount    useComponentWillMount
  useEffect(() => {
    // VARIANT 1:
    fetch(`${backendUrl}/products?page=${page}&size=${size}&sort=${sort}`) 
      .then(res => res.json())
      .then(json => {
        setProducts(json.content);
        setTotalElements(json.totalElements);
        setTotalPages(json.totalPages);
      }) // setProducts(json)
  }, [page, size, sort]);
  // kui üks nendest muutujatest muutub, siis kogu päring teakse uuesti


  // KOJU: Categories täpselt sama asi.
  // uus useState
  // CrossOrigin
  // uus useEffect
  // uus model

  // Rendipood / Kümnevõistlus

  function addToCart(productClicked: Product) {
    const cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
    const cartProduct = cart.find(cp => cp.product.id === productClicked.id);
    if (cartProduct) {
      cartProduct.quantity++;
    } else {
      cart.push({product: productClicked, quantity: 1});
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    increaseSum(productClicked.price);
    dispatch(increment());
  }

  // LocalStorage-s on alati String väärtused
  // LocalStorage ei võimalda pushida/addida
  // LocalStorage update-miseks on ainult .setItem

  // LocalStorage-sse array lisamiseks:
  // 1. võtta LS-st vana seis (LS.getItem)
  // 2. kui ei ole sellist võtit LS-s, siis võta tühi array    (|| "[]")
  // 3. võtta jutumärgid maha (JSON.parse)
  // 4. lisa toode juurde (.push)
  // 5. pane jutumärgid tagasi (JSON.stringify)
  // 6. pane LS-sse tagasi (LS.setItem)

  function updateSize(newSize: number) {
    setSize(newSize);
    setPage(0);
  }

  return (
    <>
      <div>Tooteid kokku: {totalElements}</div>

      <br /><br />

      <div>Tooteid lehel:</div>
      <select onChange={(e) => updateSize(Number(e.target.value))} defaultValue={size}>
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
      </select>

      <br /><br />

      <div>Sorteeri:</div>
      <button onClick={() => setSort("id,asc")}>Vanemad eespool</button>
      <button onClick={() => setSort("id,desc")}>Uuemad eespool</button>
      <button onClick={() => setSort("name,asc")}>A-Z</button>
      <button onClick={() => setSort("name,desc")}>Z-A</button>
      <button onClick={() => setSort("price,asc")}>Hind kasvavalt</button>
      <button onClick={() => setSort("price,desc")}>Hind kahanevalt</button>


      <br /><br />

      {products.map(product => 
        <div key={product.id}>
          <div>{product.name}</div>
          <div>{product.price}€</div>
          <button onClick={() => addToCart(product)}>Add to cart</button>
          <Link to={`/product/${product.id}`}>
            <button>Vt lähemalt</button>
          </Link>
        </div>)}

      <button disabled={page === 0} onClick={() => setPage(page - 1)}>Eelmine</button>
      <span>{page+1}</span>
      <button disabled={page+1 >= totalPages} onClick={() => setPage(page + 1)}>Järgmine</button>

      {/* <div>{count}</div>
      <button onClick={() => setCount(prev => prev + 1)}>Suurenda</button>
      <button onClick={() => setCount(prev => prev + 1)}>Suurenda ka</button>

      <div>{count2}</div>
      <button onClick={() => setCount2(prev => prev + 1)}>Suurenda</button> */}
    </>
  )
}

export default Products
