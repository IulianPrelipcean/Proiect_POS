import React, { useState } from "react";
import PropTypes from 'prop-types';
import './Login.css';
import NavBar from '../bookStore/NavBar';


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

async function loginUser(credentials){
    return fetch('http://localhost:8080/api/bookcollection/bookCheckStock',{ 
        method: 'POST',
        headers: {
            'Constent-Type': 'application/json'
            // 'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify({"ISBN-10": "10"})
        
    })
        .then(data => data.json())
}


export default function Login({ setToken }){
    const [username, setUserName] = useState();
    const [password, SetPassword] = useState();


    const handleSubmit = async e => {
        e.preventDefault();
        const token = await loginUser({
            username,
            password
        });
        console.log("merge treava <1>:  " + token.isbn)
        console.log("merge treava <2>: " + token["ISBN-10"])
        setToken(token);
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

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};
