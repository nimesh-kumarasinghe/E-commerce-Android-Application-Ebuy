import React, { Component } from 'react';
import MyAccountService from '../services/MyAccountService';
import Cookies from 'universal-cookie';

class MyAccount extends Component {
    constructor(props) {
        super(props);
    }
    state = { 
        UserID: "",
        UserType: "",
        FirstName: "",
        LastName: "",
        Address: "",
        City: "",
        Country: "",
        PostalCode: "",
        PhoneNo: "",
        Email: "",
        UPassword: ""
    }

    componentDidMount(){
        const cookies = new Cookies();
        const id = cookies.get("user_id");

        MyAccountService.getUserDetails(id).then((res)=>{
            this.setState({UserID:res['data'][0]['UserID']});
            this.setState({UserType:res['data'][0]['UserType']});
            this.setState({FirstName:res['data'][0]['FirstName']});
            this.setState({LastName:res['data'][0]['LastName']});
            this.setState({Address:res['data'][0]['Address']});
            this.setState({City:res['data'][0]['City']});
            this.setState({Country:res['data'][0]['Country']});
            this.setState({PostalCode:res['data'][0]['PostalCode']});
            this.setState({PhoneNo:res['data'][0]['PhoneNo']});
            this.setState({Email:res['data'][0]['Email']});
            this.setState({UPassword:res['data'][0]['UPassword']});
        })
    }

    changeHandler = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
        });
    };
    submitHandler = (e) =>{
        e.preventDefault();
        console.log(this.state);

        MyAccountService.updateUser(this.state).then((res)=>{
            console.log(res.data);
        })
    }
    render() { 
        return ( 
            <div className = "container p-5 bg-dark text-light" style={{marginTop:"50px"}}>
                    <div>
                        <div className ="col-md-6 offset-md-3">
                            <h3 className ="text-center fw-bold">My Account</h3>
                            <div >
                            <form method="post">
                                <div class="form-group mb-4">
                                    <label className='text-light'>First Name</label>
                                    <input type="text" class="form-control" name="FirstName" 
                                    onChange={this.changeHandler}
                                    value={this.state.FirstName}
                                    />
                                </div>
                                <div class="form-group mb-4">
                                    <label className='text-light'>Last Name</label>
                                    <input type="text" class="form-control " name="LastName"  onChange={this.changeHandler} value={this.state.LastName}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Address</label>
                                    <input type="text" class="form-control " name="Address"  onChange={this.changeHandler} value={this.state.Address}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>City</label>
                                    <input type="text" class="form-control " name="City" onChange={this.changeHandler} value={this.state.City}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Country</label>
                                    <input type="text" class="form-control " name="Country" onChange={this.changeHandler} value={this.state.Country}/>
                                </div>
                                
                                <div class="form-group mb-4">
                                    <label className='text-light'>PostalCode</label>
                                    <input type="text" class="form-control " name="PostalCode" onChange={this.changeHandler} value={this.state.PostalCode}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Phone Number</label>
                                    <input type="text" class="form-control " name="PhoneNo" onChange={this.changeHandler} value={this.state.PhoneNo}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Email</label>
                                    <input type="text" class="form-control " name="Email" onChange={this.changeHandler} value={this.state.Email}/>
                                </div>

                                <button class="btn btn-primary" type='button' onClick={this.submitHandler}>UPDATE</button>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
         );
    }
}
 
export default MyAccount;