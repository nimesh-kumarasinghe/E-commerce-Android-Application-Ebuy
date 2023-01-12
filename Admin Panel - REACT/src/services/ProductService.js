import axois from "axios";

const api_url_img = "https://duropackaging.com/sv2/images"
//const api_url_img = "http://127.0.0.1:5000/images"
const api_products = "https://duropackaging.com/sv2/products"
const get_img_url = "https://duropackaging.com/sv2/imgs/"
//const get_img_url = "http://127.0.0.1:5000/imgs/"

class ProductService{
    getUrl(){
        return api_url_img;
    }
    addProduct(p_data){
        return axois.post(api_products,p_data);
    }
    getAllProducts(id){
        return axois.get(api_products+"?userid="+id);
    }
    deleteProduct(id){
        console.log(id);
        const data = {
            "ProductID":id
        }
        axois.post(api_products+"-del",data);
    }
    getImgUrl(){
        return get_img_url;
    }
}
 
export default new ProductService();