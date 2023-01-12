import axois from "axios";

const api_url_img = "https://duropackaging.com/sv1/users"

class MyAccountService{
    getUserDetails(id){
        return axois.get("https://duropackaging.com/sv1/users?id="+id);
    }
    updateUser(data){
        return axois.put("https://duropackaging.com/sv1/users",data)
    }
}
 
export default new MyAccountService();