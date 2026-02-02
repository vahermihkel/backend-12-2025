// util sees on funktsioonid, mis ei tagasta HTMLi

import { CartProduct } from "../models/CartProduct";

// components/AlamKomponent.tsx <-- tagastab HTMLi

// Kui on vaja kasutada Hooki: useState või useEffect ja tahan taaskasutada

export function calculateCartSum(cartProducts: CartProduct[]) {
  let sum = 0;
  cartProducts.forEach(cp => {
    sum += cp.product.price * cp.quantity;
  });
  return sum;
}