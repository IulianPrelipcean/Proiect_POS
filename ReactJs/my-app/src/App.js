import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route} from 'react-router-dom';

import useToken from './components/token/useToken';
import Login from './components/login/Login';
import Logout from './components/login/Logout';
import BookStore from './components/bookStore/BookStore'
import ShoppingCart from './components/shoppingCart/ShoppingCart'


export default function App(){

  // const {token, setToken} = useToken();

  // if(token == null){
  //   return <Login />
  // }

  return (
    <div>
      <div className="wrapper">
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<BookStore/>}> </Route>
            <Route path="/login" element={<Login/>}> </Route>
            <Route path="/logout" element={<Logout/>}> </Route>
            <Route path="/shoppingCart" element={<ShoppingCart/>}> </Route>
            
          </Routes>
        </BrowserRouter>

      </div>
    </div>
  )
  

}






// class App extends React.Component{
//   constructor(props){
//     super(props);
//     this.state = {
//       files:[]
//     };
//   }

//   componentDidMount(){
//     const reqHeaders = new Headers();
//     if(reqHeaders.has('Accept')){
//       reqHeaders.set('Accept', 'application/json');
//     }
//     else{
//       reqHeaders.append('Accept', 'application/json');
//     }

//     const requestParams = {
//       method: 'GET',
//       headers: reqHeaders,
//     };

//     fetch('http://localhost:8080/api/bookcollection/books/ISBN-1', requestParams)
//     .then(response => {return response.json()})
//     .then(data => {this.setState({files: data})})
//     .catch(console.log);

    
//     // this.setState({files: [{"filename": "nume1"}]})
//   }

//   render(){
//     return(
//       <FileList files={this.state.files}/>
//     )
//   }

// }

// class Book extends React.Component{
//   render(){
//     var bookName = "BookNameNotDefined"
//     return (
//       <h5>bookName</h5>
//     )
//   }
// }


// function App() {
//   var name = "my name";
//   console.log("what")
//   return (
//     <div className="App">
//       <h1>hello sunshine </h1>
//       <h2>{name}</h2>
    
//     </div>
//   );
// }

// export default Book;
