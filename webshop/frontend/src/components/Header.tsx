import { Link } from 'react-router-dom';
// JavaScripti: useNavigate
// HTMLi: Link, Route, Routes, Home

import { useTranslation } from 'react-i18next';
import { useContext } from 'react';
import { CartSumContext } from '../context/CartSumContext';
import { useAppSelector } from '../store/store';

const Header = () => {
  const { t, i18n } = useTranslation();
  const {sum} = useContext(CartSumContext);
  const count = useAppSelector(state => state.counter.value)

  function updateLanguage(newLang: string) {
    i18n.changeLanguage(newLang);
    localStorage.setItem("language", newLang);
  } 

  return (
    <div>
      <header>
        <nav className='navbar navbar-expand-lg navbar-light bg-light'>
          <div className='container-fluid'>
            <Link className='navbar-brand' to="/">Veebipood</Link>
            <button className='navbar-toggler' type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
              <span className='navbar-toggler-icon'></span>
            </button>
            <div className='collapse navbar-collapse' id="navbarNav">
              <ul className='navbar-nav'>
                <li className='nav-item'>
                    <Link className='nav-link' aria-current="page" to="/products">{t('header.products')}</Link>
                </li>
                <li className='nav-item'>
                    <Link className='nav-link' to="/admin/persons">Persons</Link>
                </li>
                <li className='nav-item'>
                    <Link className='nav-link' to="/cart">Cart</Link>
                </li>
                <li className='nav-item'>
                    <Link className='nav-link' to="/my-orders">My orders</Link>
                </li>
                <li className='nav-item'>
                    <Link className='nav-link' to="/admin">Admin</Link>
                </li>
              </ul>
            </div>
              <span>{sum.toFixed(2)}€ / {count} pcs</span>
              <button onClick={() => updateLanguage("et")}>Eesti</button>
              <button onClick={() => updateLanguage("en")}>English</button>        
          </div>
         <div>
        </div>
        </nav>
      </header>
    </div>
  )
}

export default Header