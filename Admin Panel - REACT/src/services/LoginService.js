import axois from "axios";
import Cookies from 'universal-cookie';

const api_url = "https://duropackaging.com/sv1/validate"
const users_url = "https://duropackaging.com/sv1/users"

class LoginService{
    validate(email,password){
        var datax = {
            "Email":email,
            "UPassword":password
        }
        //console.log(datax);
        return axois.post(api_url,datax);
        
    }
    signup(email,password){
        const data = {
            "UserType": "seller",
            "FirstName": "NONE",
            "LastName": "NONE",
            "Address": "NONE",
            "City": "NONE",
            "Country": "NONE",
            "PostalCode": "NONE",
            "PhoneNo": "NONE",
            "Email": email,
            "UPassword": password
        }
        return axois.post(users_url,data);
    }
    forgotPassword(email){

    }
    logout(){
        const cookies = new Cookies();
        cookies.remove("logged",{path:"/"})
        cookies.remove("user_id",{path:"/"})
        window.location="/";
    }
}

export default new LoginService();