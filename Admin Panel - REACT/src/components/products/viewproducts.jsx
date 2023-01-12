import React, { Component } from 'react';
import ProductService from '../../services/ProductService';
import Cookies from 'universal-cookie';

class ViewProducts extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products:[]
        }
    }
    componentDidMount(){
        const cookies = new Cookies();
        const id = cookies.get("user_id");

        ProductService.getAllProducts(id).then((res) =>{
                this.setState({products:res.data});
        });
    }
    deleteProduct =(e)=>{
        ProductService.deleteProduct(e.target.value);
        document.getElementById("pro"+e.target.value).remove();
        console.log(e.target.value);
    }
    render() { 
        return (  
            <div className = "container p-5 bg-light">
                <h2 className='text-center'>Products</h2>
                <div className='row'>
                <div className ="card col-md-12 offset-md">
                    <table className='table table-striped table-bordered'>
                        <thead>
                            <tr>
                                <th>Product Image</th>
                                <th>Product Name</th>
                                <th>Product Description</th>
                                <th>Product Price</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.products.map(
                                    product=>
                                    <tr key = {product.ProductID} id={"pro"+product.ProductID}>
                                        <td><img src={ProductService.getImgUrl()+product.imgLocation} width="80" height="70" style={{'paddingLeft':'12px'}}/></td>
                                        <td>{product.ProductName}</td>
                                        <td>{product.PDescription}</td>
                                        <td>{product.Price}</td>
                                        <td>{product.Quantity}</td>
                                        <td><button value={product.ProductID} onClick={this.deleteProduct}>Delete</button></td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                    </div>
                </div>
                
            </div>
        );
    }
}
 
export default ViewProducts;