import mysql.connector
from flask import Flask,request,send_from_directory
import json
import requests
from datetime import date
from flask_cors import CORS
import os
from flask_uploads import UploadSet, configure_uploads, IMAGES

import threading

# ------------------ DATABASE ------------------

dt = []

table_name = "orders"
primary_key = "OrderID"

try:
    connection = mysql.connector.connect(
       host='127.0.0.1',
       database='ebuydb',
       user='root',
       password=''
    )

    def getData(query):
       con.execute(query)
       return con.fetchall()

    def insertOrUpdateData(query):
       con.execute(query)
       connection.commit()
       return con.rowcount


    def getMaxId():
        try:
            nextid = str(
                (getData("SELECT MAX(" + primary_key + ") from " + table_name + "")[0][0]) + 1)  # increment id by 1
            print(nextid)
            return nextid
        except:
            return "1"

    if connection.is_connected():
        print("DATABASE CONNECTED !!")
        con = connection.cursor()

except:
   print("DATABASE NOT CONNECTED !! ")

# ------------------ DATABASE ------------------

# ------------------ WEB SERVER ------------------
app = Flask(__name__)


CORS(app)
@app.route('/orders' , methods=['GET'])
def getdata():
   dt.clear()
   try:
       if(request.args.get("id")): #search by id (id -> parameter in URL?id="")
          dbdt = getData("select * from "+table_name+" where "+primary_key+" = '"+request.args.get("id")+"'")

       elif(request.args.get("CustomerID")): #search by id (id -> parameter in URL?id="")
          dbdt = getData("SELECT * FROM `orders` WHERE `customer_id` = '"+request.args.get("CustomerID")+"'")

       else: #getall
          dbdt  = getData("select * from "+table_name+"")

       counter = 0

       for x in dbdt:  # config JSON
           dt.append({
               "OrderID": str(x[0]),
               "Amount": str(x[1]),
               "OrderDate": str(x[2]),
               "customer_id": str(x[3]),
               "Status": str(x[4])
           })
           counter = counter + 1

       json_ob = json.dumps(dt)
       return json_ob
   except:
      return "Error"


@app.route('/orders',methods=['POST'])
def putData():
   try:
      data = request.get_json() #get json data
      next_id = getMaxId()
      #insert query db (Add new data with -> , '"+data['json_id']+" )
      #INSERT INTO `product` (`product_id`, `name`, `description`, `price`, `img_loc`, `categoryid`, `sellerid`) VALUES ('1', 'test name', 'testing desc', '12', 'https://www.google.com', '1', '1');
      query = "INSERT INTO `orders` (`OrderID`, `Amount`, `OrderDate`, `customer_id`,`Status`) VALUES ('"+next_id+"', '"+data['Amount']+"', '"+data['OrderDate']+"', '"+data['customer_id']+"', '"+data['Status']+"');"
      print(query)
      insertOrUpdateData(query)

      return "DATA INSERTED"
   except Exception as e:
      print(e)
      return "Error"

# ---------- Email --------
def sendMail(msg,email,subject):
    message = msg
    r = requests.post("https://duropackaging.com/v5/email", json={
        "to": email,
        "subject": subject,
        "body": message
    })
# ---------- Email --------
def getCustomerEmail(customerid):

    r = requests.get("https://duropackaging.com/sv4/customers?id="+customerid+"").json()

    #Sending Welcome Mail

    recv_email = r[0]['Email']
    subject = "Placing an order"
    message = f"""
        Hi, {r[0]['FirstName']}
        \n
        Your order was successfully placed. For further information contact our delivery customer support.
        \n
        Have a nice day!
        Thank You.\n
        Ebuy
    """
    threading.Thread(target=sendMail, args=(message, recv_email, subject)).start()
    #Sending Welcome Mail

#---------

@app.route('/customerorder',methods=['GET'])
def customerOrder():
    product_id = request.args.get("ProductID")
    customer_id = request.args.get("CustomerID")
    qty = request.args.get("Quantity")
    r = requests.get("https://duropackaging.com/sv2/products?id="+product_id+"").json()

    if(int(r[0]['Quantity']) <=0):
        response = [{
            "Status": "-1"
        }]

        msg = json.dumps(response)
        return msg
    else:
        today_date = date.today()
        price = r[0]['Price']

        order_f = {
            "Amount": str(price),
            "OrderDate": str(today_date),
            "customer_id": str(customer_id),
            "Status": "Processing"
        }
        order_id = getMaxId()
        order_req = requests.post("https://duropackaging.com/sv3/orders",json=order_f)

        query_x = "INSERT INTO `order_products` (`order_id`, `product_id`, `quantity`) VALUES ('"+order_id+"', '"+product_id+"', '"+qty+"');"
        insertOrUpdateData(query_x)

        # Deduct qty
        update_p = r[0]
        update_p['Quantity'] = str(int(update_p['Quantity']) - 1)
        rp = requests.put("https://duropackaging.com/sv2/products",json=update_p)
        # Deduct qty


        threading.Thread(target=getCustomerEmail(customer_id)).start()

        response = [{
            "Status": "1",
            "OrderID":str(order_id)
        }]

        msg = json.dumps(response)
        return msg

@app.route('/orders', methods=['PUT'])
def updateData():
   try:
      data = request.get_json()#get json data

      #update query db - (use str(data['id']))
      #UPDATE `product` SET `name` = 'test namex', `description` = 'testing descx', `price` = '1212', `img_loc` = 'https://www.google.comx', `categoryid` = '12', `sellerid` = '12' WHERE `product`.`product_id` = 1;
      query = "UPDATE `orders` SET `Amount` = '"+data['Amount']+"', `OrderDate` = '"+data['OrderDate']+"', `customer_id` = '"+data['customer_id']+"', `Status` = '"+data['Status']+"' WHERE `orders`.`OrderID` = "+str(data['OrderID'])+";"
      print(query)
      insertOrUpdateData(query)

      return "DATA UPDATED"
   except:
      return "Error"


@app.route('/orders-del', methods=['POST'])
def deleteData():
   try:
      data = request.get_json()#get json data
      print(data)
      #print(data['product_id'])
      #delete query db
      query = "DELETE FROM `orders` WHERE `orders`.`OrderID` = "+str(data['OrderID'])+""
      insertOrUpdateData(query)
      print(query)
      return "DATA DELETED"
   except:
      return "Error"

# -- GET ORDER DETAILS --

@app.route('/user-orders',methods=['POST'])
def userOrders():
    dt.clear()
    data = request.get_json()
    order_products_q = "select order_products.order_id,order_products.product_id,order_products.quantity from order_products where order_products.product_id in (select product.ProductID from product where product.user_id = '"+data['UserID']+"')"
    order_p = getData(order_products_q)
    for x in order_p:
        print(x)
        customer_id_q = "select orders.customer_id,orders.Status from orders WHERE orders.OrderID = '"+str(x[0])+"'"
        customer_p = getData(customer_id_q)
        print(customer_p)

        dt.append({
            "OrderID": str(x[0]),
            "ProductID": str(x[1]),
            "Quantity": str(x[2]),
            "customer_id": str(customer_p[0][0]),
            "Status": str(customer_p[0][1]),
        })
    json_ob = json.dumps(dt)
    return json_ob


# -- GET ORDER DETAILS --

@app.route('/user-order-custom',methods=['GET'])
def userCustomOrders():
    try:
        customer_id = request.args.get("CustomerID")
        res = []
        cus_dt = requests.get("https://duropackaging.com/sv3/orders?CustomerID="+customer_id+"").json()
        for x in cus_dt:
            order_id = x['OrderID']
            print(order_id)
            pro_query = "SELECT `product_id`,`quantity` FROM `order_products` WHERE `order_id` = '" + order_id + "'"
            pro_data = getData(pro_query)
            product_id = pro_data[0][0]
            qty = pro_data[0][1]
            p_data = requests.get("https://duropackaging.com/sv2/products?id=" + str(product_id) + "").json()
            print(p_data[0])

            res.append({
                "OrderID": x['OrderID'],
                "Amount": x['Amount'],
                "OrderDate": x['OrderDate'],
                "OrderStatus": x['Status'],
                "ProductName": p_data[0]['ProductName'],
                "OrderQTY": str(qty),
                "imgLocation": p_data[0]['imgLocation']
            })
        print(res)
        msg = json.dumps(res)
        return msg
    except:
        response = [{
            "Status": "-1"
        }]

        msg = json.dumps(response)
        return msg

if __name__ == "__main__":
    app.run()

# ------------------ WEB SERVER ------------------