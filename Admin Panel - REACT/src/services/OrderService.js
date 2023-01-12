import axois from "axios";

const api_orders = "https://duropackaging.com/sv3/user-orders"

class OrderService{
    getAllOrders(id){
        const data = {
        UserID: id,
        }
        return axois.post(api_orders,data);
    }
    getcustomer(id){
        return axois.get("https://duropackaging.com/sv4/customers?id="+id)
    }
    getProductName(id){
        return axois.get("https://duropackaging.com/sv2/products?id="+id);
    }
    getOrderById(id){
        return axois.get("https://duropackaging.com/sv3/orders?id="+id);
    }
    updateOrder(data){
        return axois.put("https://duropackaging.com/sv3/orders",data)
    }
}

export default new OrderService();