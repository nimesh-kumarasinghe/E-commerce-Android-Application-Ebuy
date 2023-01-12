import React, { Component } from 'react';
import LoginService from '../services/LoginService';

class Logout extends Component {
    constructor(props) {
        super(props);
    }
    state = {  }
    logout =()=>{
        LoginService.logout();
    }
    render() { 
        return this.logout();
    }
}
 
export default Logout;