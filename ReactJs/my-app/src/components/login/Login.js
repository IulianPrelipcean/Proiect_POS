import React, { useState } from "react";
import PropTypes from 'prop-types';
import './Login.css';
import NavBar from '../bookStore/NavBar';
import useToken from "../token/useToken";
import { ToastContainer, toast } from 'react-toastify';
import BookStore from "../bookStore/BookStore";



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


async function loginUser(credentials){
    return fetch('http://localhost:8083/api/users/authenticate',{ 
        method: 'POST',
        headers: {
            'Constent-Type': 'application/json'
            // 'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}




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



export default function Login(){
    
    const { token, setToken} = useToken()

    // if(!token){
    // console.log("in setToken: " + token)
    //     return <Login setToken={setToken} />
    // }

    //console.log("in Login")
    const [username, setUserName] = useState();
    const [password, SetPassword] = useState();

    //console.log("after useState")

    var loginFinised = false


    const handleSubmit = async e => {
        e.preventDefault();
        const authenticationResponse = await loginUser({
            username,
            password
        });
        if(authenticationResponse.status == "UNAUTHORIZED"){
            alert("Invalid credentials! ")
        }
        else{
            setToken(authenticationResponse.token);
            console.log("hai")
            loginFinised = true

            
        }
        // console.log("token:  " + authenticationResponse.token)
        // console.log("status: " + authenticationResponse.status)
        // //console.log("status: " + token["ISBN-10"])
        // if(authenticationResponse.status)
        // setToken(token);
    }



    if(loginFinised == true){
        console.log("manacamiai")
        return (
            <div>
                <BookStore/>
            </div>
        )
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
