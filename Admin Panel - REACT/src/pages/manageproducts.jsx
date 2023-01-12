import React, { Component } from 'react';
import AddProducts from '../components/products/addproducts';
import UpdateProucts from '../components/products/updateproducts'
import ViewProducts from '../components/products/viewproducts'
import '../css/product-css/dashboard.css'

class Manageproducts extends Component {
    constructor(props) {
        super(props);
    }
    state = { 
        add_p : false,
        view_p :true
     }
    changeView =(e)=>{
        this.setState({
            add_p : false,
            view_p :false,
            [e.target.name] : true
        })
    }
    render() { 
        return (
            <div id="main-cont">
                <h2 id='text_header'>Manage Products</h2>
                <div id="header-cont">
                    <div className="btn_links">
                    <button className="btn_link" name="add_p" onClick={this.changeView}>Add Products</button><br/>
                    <button className="btn_link" name="view_p" onClick={this.changeView}>View Products</button><br/>
                </div>
                </div>
                <div id="cont">
                    {this.state.add_p ? (<AddProducts/>): ""}
                    {this.state.view_p ? (<ViewProducts/>) : ""}
                </div>
            </div>
         );
    }
}
 
export default Manageproducts;