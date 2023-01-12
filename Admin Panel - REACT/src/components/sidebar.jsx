import React, { Component } from 'react';
import "../css/sidebar.css"

class SideBar extends Component {
    constructor(props) {
        super(props);
    }
    state = {  }
    render() { 
        return ( 
            <div className="sidenav">
                <a href="/dashboard">Dashboard</a>
                <a href="/manage-products">Manage Products</a>
                <a href="/manage-orders">Manage Orders</a>
                <a href="/my-account">My Account</a>
                <a href="/logout">Logout</a>
            </div>
         );
    }
}
 
export default SideBar;