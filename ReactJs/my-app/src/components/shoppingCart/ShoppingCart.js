import React, { useState } from 'react';
import NavBar from '../bookStore/NavBar';
import "./shoppingCartStyle.css";
import { Button, Modal } from "react-bootstrap";


function deleteBookFromCart(book_isbn){
    //TODO am nevoie de id
}


export default function ShoppingCart(){


    const [bookList, setBookList] = useState()

    const renderTable = () => {
        if(bookList != null){
            return bookList.map(book => {
                return (
                    <tr>
                        <td>{book.isbn}</td>
                        <td>{book.title}</td>
                        <td>{book.quantity}</td>
                        <td><button value={book.isbn} onClick={() => deleteBookFromCart(book.isbn)}>Delete books</button></td>
                    </tr>
                )
            })
        }
    }


    return (
        <div>
            {<NavBar/>}
            <div className="productsContent">
                <h1>Shoping Cart</h1>
                <table className="productsTable">
                    <thead>
                        <tr>
                            <th>ISBN</th>
                            <th>Title</th>
                            <th>Quantity</th>
                            <th> </th>
                        </tr>
                    </thead>
                    <tbody>
                        {renderTable()}
                    </tbody>
                </table>
                <Button>Finish Order</Button>
            </div>
        </div>  
    );
}



