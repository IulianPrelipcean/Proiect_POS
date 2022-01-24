import { useState } from 'react';

export default function useToken(){
    const getToken = () => {
        console.log("i m in getToken()")
        const tokenString = localStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        console.log("token is: " + userToken["ISBN-10"])
        //return userToken?.token
        return tokenString
    };

    console.log("before useState")
    const [token, setToken] = useState(getToken());
    console.log("in useToken: " + token)

    const saveToken = userToken => {
        localStorage.setItem('token', JSON.stringify(userToken));
        setToken(userToken.token);
    };

    return {
        setToken: saveToken,
        token
    }
}