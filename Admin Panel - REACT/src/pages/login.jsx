import React, { Component } from "react";
import "../css/login.css";
import LoginService from "../services/LoginService";
import Cookies from 'universal-cookie';

class Login extends Component {
    constructor(props) {
        super(props);
    }
    state = { 
      email: "",
      password: "",
      status: "",
    }
    changeHandler =(e)=>{
        this.setState({
            [e.target.name]:e.target.value
        })
    }
    submitHandler =(e)=>{
        e.preventDefault();
        //console.log(this.state)
        LoginService.validate(this.state.email,this.state.password).then((res)=>{
            if(res.data != -1){
                const cookies = new Cookies();
                cookies.set("logged",true,{path:'/'})
                cookies.set("user_id",res.data,{path:'/'})
                window.location="/dashboard";
            }
            else{
                console.log("Error - " + res.data)
            }
        })
    }
    render() { 
        return (
            <div id="login">
                <form id="login-form" onSubmit={this.submitHandler}>
                    <div id="login_txt">
                        <h1>Login</h1>
                    </div>
                    <div className="input_f">
                        <input
                            type="email"
                            placeholder="Enter Your Email"
                            id="email"
                            name="email"
                            value={this.email}
                            onChange={this.changeHandler}
                        />
                    </div>
                    <div className="input_f">
                        <input
                            type="password"
                            placeholder="Enter Your Password"
                            id="password"
                            name="password"
                            value={this.password}
                            onChange={this.changeHandler}
                        />
                    </div>
                    <div className="input_f">
                        <input
                            type="submit"
                            id="submit"
                        />
                    </div>
                    <div className="input_f">
                        <br/>
                        <a href="/signup" id="sinup_link">Do not have an account?</a>
                    </div>
                </form>
            </div>
        );
    }
}
 
export default Login;