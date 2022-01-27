import React, { useEffect, useState } from 'react';
import { Button, Modal } from "react-bootstrap";
import "./bookStoreStyle.css";
import { Link } from 'react-router-dom';
import NavBar from './NavBar';
import useToken from '../token/useToken';


async function getAllBooksRequest(){
    return fetch('http://localhost:8080/api/bookcollection/books',{ 
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(data => data.json())
        .then(response => {
            //console.log("here:" + response._links.bookcollection.href)
            console.log("here:" + response._embedded.bookDTOList[0].isbn)
        })
}


function addBookISBN(book_isbn){
    // TODO  
    console.log("in special: " + book_isbn)
}



export default function BookStore(){
    
    const {token, setToken} = useToken()
    const [bookList, setBookList] = useState([])


    // used for showing book details using modals
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);



    useEffect(() => {
        console.log("primi")
            fetch('http://localhost:8080/api/bookcollection/books',{ 
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(data => data.json())
                .then(response => {
                    //console.log("here:" + response._links.bookcollection.href)
                    //console.log("here:" + response._embedded.bookDTOList[0].isbn)
                    setBookList(response._embedded.bookDTOList)
                })
        },
    []
    );

    const addBook = (book_isbn) =>{
        console.log("isbn to ADD: " + book_isbn)
    }


    const renderTable = () => {
        return bookList.map(book => {
            return (
                <tr>
                    <td>{book.isbn}</td>
                    <td>{book.title}</td>
                    <td>
                        <Button variant="primary" onClick={handleShow}>
                            Details
                        </Button>

                        <Modal show={show} onHide={handleClose}>
                            <Modal.Header closeButton>
                                <Modal.Title>Book details</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <ul>
                                    <li>ISBN: {book.isbn}</li>
                                    <li>titlu: {book.title}</li>
                                    <li>gen: {book.genre}</li>
                                    <li>editura: {book.publisher}</li>
                                    <li>an publicare: {book.release_year}</li>
                                    <li>numar pagini: {book.number_of_pages}</li>
                                    <li>pret: {book.price}</li>
                                    <li>stoc disponibil: {book.available_stock}</li>
                                </ul>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="secondary" onClick={handleClose}>
                                    Close
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </td>
                    {token ? (
                        <td><button value={book.isbn} onClick={() => addBookISBN(book.isbn)}>Add to cart</button></td>
                    ): (
                        <td><button><Link to="/login">Add to cart</Link></button></td>
                    )}
                    
                </tr>
            )
        })
    }



    return (
        
        <div>
            {<NavBar/>}
            <div className="bookContent">
                <h1>Book Store</h1>
                <table className="bookTable">
                    <thead>
                        <tr>
                            <th>ISBN</th>
                            <th>Title</th>
                            <th> </th>
                            <th> </th>
                        </tr>
                    </thead>
                    <tbody>
                        {renderTable()}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

