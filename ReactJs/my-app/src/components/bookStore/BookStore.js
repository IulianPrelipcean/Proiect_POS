import React from 'react';
import { ToggleButtonGroup, ToggleButton, Button } from "react-bootstrap";
import "./bookStoreStyle.css";
import { Link } from 'react-router-dom';
import NavBar from './NavBar';


export default class  BookStore extends React.Component{
    
    render(){
        return (
            <div>
                {<NavBar/>}
                <h1>We are in books</h1>
            </div>
        );
    };
}

