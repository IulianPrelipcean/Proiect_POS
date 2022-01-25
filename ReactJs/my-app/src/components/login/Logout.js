import React, { useState } from "react";
import './Login.css';
import NavBar from '../bookStore/NavBar';
import useToken from "../token/useToken";
import BookStore from "../bookStore/BookStore"



async function destroyToken(credentials){
    return fetch('http://localhost:8083/api/users/authenticate',{ 
        method: 'POST',
        headers: {
            'Constent-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}



export default function Logout(){
    
    localStorage.removeItem("token")

    // TODO 
    // destroyToken()


    return (
        <div>
            <BookStore/>
        </div>
    )
}