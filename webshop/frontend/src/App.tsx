import './App.css'
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
// import Footer from './components/Footer';
import Home from './pages/Home';
import Products from './pages/Products';
import Persons from './pages/admin/Persons';
import PersonDetails from './pages/PersonDetails';
import Cart from './pages/Cart';
import MyOrders from './pages/MyOrders';
import AdminHome from './pages/admin/AdminHome';
import ProductAdd from './pages/admin/ProductAdd';
import ProductEdit from './pages/admin/ProductEdit';
import ProductsManage from './pages/admin/ProductsManage';
import CategoriesManage from './pages/admin/CategoriesManage';
import ProductDetails from './pages/ProductDetails';
import NotFound from './pages/NotFound';

function App() {

  // Rendipood:
  // - erinevad lehed (route-mine)
  // - tõlge
  // - darkmode/lightmode + localStorage
  // - https://fonts.google.com/
  // - MUI/Tailwind/Bootstrap
  // - soovi korral kümnevõistlus
  // - https://www.npmjs.com/package/react-toastify
  // - https://react-hot-toast.com/

  return (
    <>
      <Header />
      
      <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/products' element={<Products/>} />
        <Route path='/product/:product_id' element={<ProductDetails/>} />
        <Route path='/persons/:id' element={<PersonDetails/>} />

        <Route path='/admin' element={<AdminHome/>} />
        <Route path='/admin/manage-categories' element={<CategoriesManage/>} />
        <Route path='/admin/persons' element={<Persons/>} />
        <Route path='/admin/add-product' element={<ProductAdd/>} />
        <Route path='/admin/edit-product/:product_id' element={<ProductEdit/>} />
        <Route path='/admin/manage-products' element={<ProductsManage/>} />

        <Route path='/cart' element={<Cart/>} />
        <Route path='/my-orders' element={<MyOrders/>} />

        <Route path='/*' element={<NotFound/>} />
      </Routes>
      
      {/* <Footer /> */}
    </>
  )
}

export default App
