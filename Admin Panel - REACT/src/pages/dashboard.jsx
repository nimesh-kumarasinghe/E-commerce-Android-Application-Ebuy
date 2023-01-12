import React, { Component } from 'react';
import '../css/dashboard.css'

class Dashboard extends Component {
    constructor(props) {
        super(props);
    }
    state = {  }
    render() { 
        return ( 
            <div className="dash_main">
                <h2 id="dash_id">WELCOME TO EBUY</h2>
                <p id="des_text">You can manage Products / Orders from this Dashboard</p>
                <div className="btn_links">
                    <a href='/manage-products' className="btn_link">Manage Products</a><br/>
                    <a href='/manage-orders' className="btn_link">Manage Orders</a><br/>
                    <a href='/my-account' className="btn_link">Manage Account</a><br/>
                    <a href='/logout' className="btn_link">Logout</a><br/>
                </div>
            </div>
         );
    }
}
 
export default Dashboard;