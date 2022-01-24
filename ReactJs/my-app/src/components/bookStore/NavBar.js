import React from 'react';
import "./bookStoreStyle.css";
import { Link } from 'react-router-dom';

export default function NavBar(){
    return (
        <div>
            <div className="menuBar">
                <div className="buttons_menu">
                    <div>
                        <Link to="/">Home</Link>   
                        <Link to="/shoppingCart">ShoppingCart</Link>
                        <Link to="/login">LogIn</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};