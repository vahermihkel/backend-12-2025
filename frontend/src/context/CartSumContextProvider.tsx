import { useState, type ReactNode } from "react";
import { CartSumContext } from "./CartSumContext";
import type { CartProduct } from "../models/CartProduct";
import { calculateCartSum } from "../util/calculations";

// Provider abil määran milline component saab kätte (skoopi)
export const CartSumContextProvider = ({children}: {children: ReactNode}) => {
  const cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
  const [sum, setSum] = useState(calculateCartSum(cart));

  const decreaseSum = (amount: number) => {
    setSum(sum - amount);
  }

  const increaseSum = (amount: number) => {
    setSum(sum + amount);
  }

  const resetSum = () => {
    setSum(0);
  }

  // const getCartSum =() => {
  //   // let sum = 0;
  //   // cart.forEach(cp => sum += cp.product.price * cp.quantity);
  //   // return sum;
  //   return ;
  // }
  
  return (
    <CartSumContext.Provider value={{sum, decreaseSum, increaseSum, resetSum}}>
      {children}
    </CartSumContext.Provider>
  )
}