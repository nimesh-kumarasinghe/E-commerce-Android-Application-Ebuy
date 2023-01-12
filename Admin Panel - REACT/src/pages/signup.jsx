import React, { Component } from "react";
import "../css/login.css"
import LoginService from "../services/LoginService";

class Signup extends Component {
    constructor(props) {
        super(props);
    }
    state = { 
        email : "",
        password : "",
        repassword : ""
     }
    submitHandler = (e) =>{
        e.preventDefault();
        console.log(this.state);
        if(this.state.password == this.state.repassword){
            LoginService.signup(this.state.email, this.state.password).then((res)=>{
                console.log(res.data);
            })
            alert("Account Created Successfully - You can now Login")
        }
        else{
            alert("Password and Confirm Password Not Equal");
        }
    }
    changeHandler = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
        });
    };
    render() { 
        return ( 
            <div id="login">
                <form id="login-form" onSubmit={this.submitHandler}>
                    <div id="login_txt">
                        <h1>Sing up</h1>
                    </div>
                    <div className="input_f">
                        <input
                            type="email"
                            placeholder="Enter Your Email"
                            id="email"
                            name="email"
                            onChange={this.changeHandler}
                        />
                    </div>
                    <div className="input_f">
                        <input
                            type="password"
                            placeholder="Enter Your Password"
                            id="password"
                            name="password"
                            onChange={this.changeHandler}
                        />
                    </div>
                    <div className="input_f">
                        <input
                            type="password"
                            placeholder="ReEnter Your Password"
                            id="re-password"
                            name="repassword"
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
                        <a href="/" id="sinup_link">Back to Login?</a>
                    </div>
                </form>
            </div>
         );
    }
}
 
export default Signup;