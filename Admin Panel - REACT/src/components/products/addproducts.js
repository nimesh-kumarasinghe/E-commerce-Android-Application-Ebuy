import React, { Component } from 'react';
import ProductService from '../../services/ProductService';
import { useState } from 'react';
import axios from 'axios';
import Cookies from 'universal-cookie';

function AddProducts(){
    
    const cookies = new Cookies();
    const [userid,setUserId] = useState(cookies.get("user_id"));
    const [img_url,setImgUrl] = useState(null);
    const [display,setDisplay] = useState(false);
    const [productdata,setProductdata] = useState({
        ProductName: "",
        PDescription: "",
        Price: "",
        Quantity: "",
        imgLocation: "",
        category_id: "",
        user_id: ""
    });
    
    const changeHandler = (e) => {
        setProductdata({...productdata,[e.target.name]:e.target.value})
        //console.log(productdata);
      };

    const submitHandler = ()=>{
        productdata.user_id = userid;
        productdata.imgLocation = img_url;
        console.log(productdata);
        ProductService.addProduct(productdata).then((res)=>{
            console.log(res.data);
            window.location = "/manage-products"
        })
    }
    const handleSubmit = (event) => {
        event.preventDefault()
        const form = document.getElementById('img-form-data');
        const formData = new FormData(form);
        console.log(formData);
        axios.post(ProductService.getUrl(),formData).then((res)=>{
            console.log(res.data);
            setImgUrl(res.data);
        })
        setDisplay(true);
    }
    

    return (
            <div className = "container p-5 bg-dark text-light">
                    <div>
                        <div className ="col-md-6 offset-md-3">
                            <h3 className ="text-center fw-bold">ADD PRODUCT</h3>
                            <div >
                            <form method="post">
                                <div class="form-group mb-4">
                                    <label className='text-light'>Product Name</label>
                                    <input type="text" class="form-control" name="ProductName" placeholder="Enter Product Name"
                                    onChange={changeHandler}
                                    />
                                </div>
                                <div class="form-group mb-4">
                                    <label className='text-light'>Product Description</label>
                                    <input type="text" class="form-control " name="PDescription" placeholder="Enter Product Description" onChange={changeHandler}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Product Price</label>
                                    <input type="text" class="form-control " name="Price" placeholder="Enter Product Price" onChange={changeHandler}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Product Quantity</label>
                                    <input type="text" class="form-control " name="Quantity" placeholder="Enter Product Price" onChange={changeHandler}/>
                                </div>

                                <div class="form-group mb-4">
                                    <label className='text-light'>Product category</label>
                                        <div class="input-group mb-4">
                                            <select class="form-select" name="category_id" onChange={changeHandler}>
                                                <option selected>Choose a category</option>
                                                <option value="0">Home</option>
                                                <option value="1">Bags & Shoes</option>
                                                <option value="2">Health & Beauty</option>
                                                <option value="3">Books, Movies & Music</option>
                                                <option value="4">Sporting Goods</option>
                                                <option value="5">Jewelry & Watches</option>
                                                <option value="6">Phones & Telecommunications</option>
                                                <option value="7">Toys & Hobbies</option>
                                                <option value="8">Electronics</option>
                                                <option value="9">Garden</option>
                                            </select>
                                        </div>
                                </div>
                                <div class="form-group mb-4">
                                    <form id='img-form-data'>
                                        <label className='text-light'>Product Image Upload</label>
                                        <input type="file" accept="image/png, image/gif, image/jpeg" class="form-control " name="photo" placeholder="Enter Product Price"/>
                                        <br/>
                                        <button class="btn btn-primary" type="button" onClick={handleSubmit}>Upload</button>
                                    </form>
                                </div>
                                <div>
                                    {
                                        display ? <img src={ProductService.getImgUrl()+img_url} width="100" height="100" id='img_uploads' style={{"padding":"1px 1px 10px 1px"}}/> : ""
                                    }
                                </div>
                                <button class="btn btn-primary" type='button' onClick={submitHandler}>Add Product</button>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
        );
}

export default AddProducts