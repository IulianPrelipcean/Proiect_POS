import React from 'react';

class FileInfo extends React.Component{
    render(){
        const tdStyleR = {
            width: '50%',
            border: '1px solid',
            padding: '3px 13px',
            textAlign: 'right',
        };

        const tdStyleL = {
            width: '50%',
            border: '1px solid',
            padding: '3px 13px',
            textAlign: 'left',
        };
        
        return (
            <tr>
                <td style={tdStyleL}>{this.props.file.isbn}</td>
                <td style={tdStyleR}>{this.props.file.title}</td>
            </tr>
        )
    }
}



class FileList extends React.Component{
    
    sampleJSON = {
        "arrOfNumbers": [1, 2, 3, 4],
        "arrOfStrings": ["a", "b", "c", "d"]
      }

    render(){

        console.log("\n\n----- here I am  -----\n\n");

        //console.log("\n array: " + this.sampleJSON.arrOfNumbers[0])
        console.log("\ndata my: " + this.props.files._links.self.href)    
        // const filelist = this.props.files.map(
        //     file => <FileInfo key={file.isbn} file={file} />
        // );


        //const filelist = this.props.files
        //const filelist = [{"isbn" : "here"}]

        const tableStyle ={
            width: '90%',
        };

        const thStyle = {
            width: '50%',
            border: '1px solid',
            padding: '3px 13px',
        };

        return (
            <center>
                <table style={tableStyle}>
                    <tbody>
                        <tr>
                            <th style={thStyle}>Name</th>
                            {/* <th style={thStyle}>Title - {this.props.files.isbn}</th> */}
                            <th style={thStyle}>Title - {this.props.files["isbn"]}</th>
                        </tr>
                        {/* {filelist} */}
                    </tbody>
                </table>

            </center>
        )

    }
}


export default FileList;




// [
//     {
//         "filename": "b.txt",
//         "size" : 20
//     },
//     {
//         "filename": "a.txt",
//         "size" : 21
//     },
//     {
//         "filename": "nop",
//         "size" : 222
//     }
// ]