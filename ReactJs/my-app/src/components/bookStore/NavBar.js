import React from 'react';
import "./bookStoreStyle.css";
import { Link } from 'react-router-dom';
import useToken from '../token/useToken';


export default function NavBar(){

    // check if the token exists in order to decide which menubar we'll show
    var existToken = false

    const {token, setToken} = useToken()

    if(token == null){
        existToken = false
    }
    else{
        existToken = true
    }

    return (
        <div>
            <div className="menuBar">
                <div className="buttons_menu">
                    <div>
                        {existToken ? (   
                            <div>
                                <Link to="/">Home</Link>   
                                <Link to="/shoppingCart">ShoppingCart</Link>
                                <Link to="/logout">LogOut</Link>
                            </div>
                        ) : (<div>
                                <Link to="/">Home</Link>   
                                <Link to="/login">LogIn</Link>
                            </div>) }
                        
                    </div>
                </div>
            </div>
        </div>
    );
};