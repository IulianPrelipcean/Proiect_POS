import React, { useState } from "react";
import PropTypes from 'prop-types';
import './Login.css';
import NavBar from '../bookStore/NavBar';
import useToken from "../token/useToken";
import { ToastContainer, toast } from 'react-toastify';
import BookStore from "../bookStore/BookStore";
import { useNavigate } from "react-router-dom"; 



// async function loginUser(credentials){
//     return fetch('http://localhost:8080/api/bookcollection/books/ISBN-1',{ 
//         method: 'GET',
//         headers: {
//             'Constent-Type': 'application/json'
//         },
//         //body: JSON.stringify(credentials)
//         //body: JSON.stringify({"ISBN-10": "10"})
        
//     })
//         .then(data => data.json())
// }

// async function loginUser(credentials){
//     return fetch('http://localhost:8080/api/bookcollection/bookCheckStock',{ 
//         method: 'POST',
//         headers: {
//             'Constent-Type': 'application/json'
//             // 'Access-Control-Allow-Origin': '*'
//         },
//         body: JSON.stringify(credentials)
        
//     })
//         .then(data => data.json())
// }






// async function loginUser(credentials){
//     var response = fetch('http://localhost:8083/api/users/authenticate',{ 
//         method: 'POST',
//         headers: {
//             'Constent-Type': 'application/json'
//             // 'Access-Control-Allow-Origin': '*'
//         },
//         body: JSON.stringify(credentials)
        
//     })
//     .then(response => {
//         if(response.status == 401){
//             console.log("HAIDEM")
//         }
//         })

//         // .then(response => {console.log("reals sta: " + response.status)})
    
//     console.log("realStatus: " + response.status);

//     return response
// }



async function loginUser(credentials){
    return fetch('http://localhost:8083/api/users/authenticate',{ 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}


export default function Login(){
    
    const { token, setToken} = useToken()
    let navigate = useNavigate();

 
    const [username, setUserName] = useState();
    const [password, SetPassword] = useState();



    const handleSubmit = async e => {
        e.preventDefault();
        const authenticationResponse = await loginUser({
            username,
            password
        });
        if(authenticationResponse.status != "OK"){
            alert("Invalid credentials! ")
        }
        else{
            setToken(authenticationResponse.token);
            //alert("Login succesfully! \n Go to HOME page!")
            navigate("/")
        }
    }


    return (
        <div>
            {<NavBar/>}
            <div className="login-wrapper">
                <h1>Please Log In</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        <p>username</p>
                        <input type="text" onChange={e => setUserName(e.target.value)} />
                    </label>
                    <label>
                        <p>Password</p>
                        <input type="password" onChange={e => SetPassword(e.target.value)}/>
                    </label>
                    <div>
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    )

}

// Login.propTypes = {
//     setToken: PropTypes.func.isRequired
// };
