import React, { Component } from 'react';
import OrderService from '../services/OrderService';
import Cookies from 'universal-cookie';

class ManageOrders extends Component {
    constructor(props) {
        super(props);
    }
    state = { 
        orders : [],
        load:false,
        showcustomerdetails :false,

        customer : [],
     }

    componentDidMount(){
        const cookies = new Cookies();
        const id = cookies.get("user_id");
        if(this.state.load == false){
            OrderService.getAllOrders(id).then((res)=>{
                this.setState({orders:res.data});
                console.log(res.data);
            })
            this.state.load=true;
        }
    }
    getCustomer =(e)=>{
        if(this.state.showcustomerdetails == false){   
            const id = e.target.value
            OrderService.getcustomer(id).then((res)=>{
                this.setState({customer:res.data[0]});
            })
            console.log(this.state.customer);
            this.setState({showcustomerdetails:true});
        }
        else{
            this.setState({showcustomerdetails:false});
        }
    }
    getProductname(e,id){
        console.log(e)
        const name = ""
        OrderService.getProductName(e).then((res)=>{
            console.log(res.data[0]['ProductName']);
            document.getElementById(id).innerText = res.data[0]['ProductName'];
        })
    }

    orderComplete =(e)=>{
        
        OrderService.getOrderById(e.target.value).then((res)=>{
            const current = res.data[0]
            current['Status'] = "COMPLETED"
            console.log(current);
            OrderService.updateOrder(current);
        })
    }

    render() { 
        return (
            <div className = "container p-5 bg-light">
                <h2 className='text-center'>Order Details</h2>
                {
                    this.state.showcustomerdetails?
                    (<div style={{"backgroundColor":"black","color":"white","border-radius":"10px","padding":"10px 10px 10px 10px","marginBottom":"10px"}} >
                    <h4>CUSTOMER DETAILS</h4>
                    <ul>
                        <li>First Name : {this.state.customer['FirstName']}</li>
                        <li>LastName : {this.state.customer['LastName']}</li>
                        <li>Address : {this.state.customer['Address']}</li>
                        <li>City : {this.state.customer['City']}</li>
                        <li>Country : {this.state.customer['Country']}</li>
                        <li>PostalCode : {this.state.customer['PostalCode']}</li>
                        <li>PhoneNo : {this.state.customer['PhoneNo']}</li>
                        <li>Email : {this.state.customer['Email']}</li>
                    </ul>  
                    </div>) : ("")
                }
                
                <div className='row'>
                <div className ="card col-md-12 offset-md">
                    <table className='table table-striped table-bordered'>
                        <thead>
                            <tr>
                                <th>ORDER ID</th>
                                <th>PRODUCT Name</th>
                                <th>QTY</th>
                                <th>CUSTOMER ID</th>
                                <th>STATUS</th>
                                <th>ACTION</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.orders.map(
                                    order=>
                                    <tr key = {order.OrderID}>
                                        <td>{order.OrderID}</td>
                                        <td id={"tb-"+order.ProductID}>{this.getProductname(order.ProductID,"tb-"+order.ProductID)}</td>
                                        <td>{order.Quantity}</td>
                                        <td>{order.customer_id}<button value={order.customer_id} style={{"marginLeft":"15px","border-radius":"6px"}} onClick={this.getCustomer}>VIEW DETAILS</button></td>
                                        <td>{order.Status}</td>
                                        <td><button value={order.OrderID} style={{"marginLeft":"15px","border-radius":"6px"}} name={"com-"+order.OrderID} onClick={this.orderComplete} id={"btn-"+order.OrderID}>COMPLETE</button></td>
                                        
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
 
export default ManageOrders;