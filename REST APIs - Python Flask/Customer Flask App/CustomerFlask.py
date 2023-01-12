import mysql.connector
from flask import Flask,request
import json
import requests
import random
from flask_cors import CORS
import threading
# ------------------ DATABASE ------------------

dt = []

table_name = "customer"
primary_key = "CustomerID"

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
@app.route('/customers' , methods=['GET'])
def getdata():
   dt.clear()
   try:
       if(request.args.get("id")): #search by id (id -> parameter in URL?id="")
          dbdt = getData("select * from "+table_name+" where "+primary_key+" = '"+request.args.get("id")+"'")

       else: #getall
          dbdt  = getData("select * from "+table_name+"")

       counter = 0

       for x in dbdt:  # config JSON
           dt.append({
               "CustomerID": str(x[0]),
               "FirstName": str(x[1]),
               "LastName": str(x[2]),
               "Address": str(x[3]),
               "City": str(x[4]),
               "Country": str(x[5]),
               "PostalCode": str(x[6]),
               "PhoneNo": str(x[7]),
               "Email": str(x[8]),
               "CPassword": str(x[9])
           })
           counter = counter + 1

       json_ob = json.dumps(dt)
       return json_ob
   except:
      return "Error"


@app.route('/customers',methods=['POST'])
def putData():
   try:
      data = request.get_json() #get json data
      next_id = getMaxId()
      #insert query db (Add new data with -> , '"+data['json_id']+" )
      #INSERT INTO `product` (`product_id`, `name`, `description`, `price`, `img_loc`, `categoryid`, `sellerid`) VALUES ('1', 'test name', 'testing desc', '12', 'https://www.google.com', '1', '1');
      query = "INSERT INTO `customer` (`CustomerID`, `FirstName`, `LastName`, `Address`, `City`, `Country`, `PostalCode`, `PhoneNo`, `Email`, `CPassword`) VALUES ('"+next_id+"', '"+data['FirstName']+"', '"+data['LastName']+"', '"+data['Address']+"', '"+data['City']+"', '"+data['Country']+"', '"+data['PostalCode']+"', '"+data['PhoneNo']+"', '"+data['Email']+"', '"+data['CPassword']+"');"
      print(query)
      insertOrUpdateData(query)

      return "DATA INSERTED"
   except Exception as e:
      print(e)
      return "Error"

def sendMail(msg,email,subject):
    message = msg
    r = requests.post("https://duropackaging.com/v5/email", json={
        "to": email,
        "subject": subject,
        "body": message
    })

@app.route('/register',methods=['GET'])
def registerCustomer():

    customer_data = {
       "FirstName": request.args.get("FirstName"),
       "LastName": request.args.get("LastName"),
       "Address": request.args.get("Address"),
       "City": request.args.get("City"),
       "Country": request.args.get("Country"),
       "PostalCode": request.args.get("PostalCode"),
       "PhoneNo": request.args.get("PhoneNo"),
       "Email": request.args.get("Email"),
       "CPassword": request.args.get("CPassword")
    }
    r = requests.post("https://duropackaging.com/sv4/customers",json=customer_data)

    #Sending Welcome Mail

    recv_email = request.args.get("Email")
    subject = "Successfully joined with Ebuy"
    message = f"""
        Hi, {request.args.get("FirstName")} \n
        \n
        Welcome to ebuy!
        \n\n
        We are grateful to provide quality and globally recognized products.\n\n
        Have a nice day!
        Let's start shopping\n\n
        Thank You.
    """

    threading.Thread(target=sendMail, args=(message, recv_email, subject)).start()

    #Sending Welcome Mail

    response = [{
        "Status": "1"
    }]

    msg = json.dumps(response)
    return msg  # Customer Registered

@app.route('/customers', methods=['PUT'])
def updateData():
   try:
      data = request.get_json()#get json data

      #update query db - (use str(data['id']))
      #UPDATE `product` SET `name` = 'test namex', `description` = 'testing descx', `price` = '1212', `img_loc` = 'https://www.google.comx', `categoryid` = '12', `sellerid` = '12' WHERE `product`.`product_id` = 1;
      query = "UPDATE `customer` SET `FirstName` = '"+data['FirstName']+"', `LastName` = '"+data['LastName']+"', `Address` = '"+data['Address']+"', `City` = '"+data['City']+"', `Country` = '"+data['Country']+"', `PostalCode` = '"+data['PostalCode']+"', `PhoneNo` = '"+data['PhoneNo']+"', `Email` = '"+data['Email']+"', `CPassword` = '"+data['CPassword']+"' WHERE `customer`.`CustomerID` = "+str(data['CustomerID'])+";"
      print(query)
      insertOrUpdateData(query)

      return "DATA UPDATED"
   except:
      return "Error"

@app.route('/update-customers', methods=['GET'])
def updateCustomerData():
    try:
       CustomerID = request.args.get("CustomerID")
       FirstName= request.args.get("FirstName")
       LastName= request.args.get("LastName")
       Address= request.args.get("Address")
       City= request.args.get("City")
       Country= request.args.get("Country")
       PostalCode= request.args.get("PostalCode")
       PhoneNo=request.args.get("PhoneNo")

       user_data = requests.get("https://duropackaging.com/sv4/customers?id="+CustomerID+"").json()
       user_data[0]['FirstName'] = FirstName
       user_data[0]['LastName'] = LastName
       user_data[0]['Address'] = Address
       user_data[0]['City'] = City
       user_data[0]['Country'] = Country
       user_data[0]['PostalCode'] = PostalCode
       user_data[0]['PhoneNo'] = PhoneNo

       save = requests.put("https://duropackaging.com/sv4/customers",json=user_data[0])

       response = [{
           "Status": "1"
       }]

       msg = json.dumps(response)
       return msg

    except:
        response = [{
           "Status": "-1"
        }]

        msg = json.dumps(response)
        return msg

@app.route('/customers-del', methods=['POST'])
def deleteData():
   try:
      data = request.get_json()#get json data
      print(data)
      #print(data['product_id'])
      #delete query db
      query = "DELETE FROM `customer` WHERE `customer`.`CustomerID` = "+str(data['CustomerID'])+""
      insertOrUpdateData(query)
      print(query)
      return "DATA DELETED"
   except:
      return "Error"

@app.route('/validate',methods=['POST'])
def validateUser():
    data = request.get_json() #get use inputs
    print(data)
    query = "select CustomerID,Email,CPassword from customer where Email = '"+data['Email']+"' AND CPassword = '"+data['CPassword']+"'"
    dta = getData(query)
    print(dta)
    if(not dta):
        return "-1"
    else:
        print(dta)
        return str(dta[0][0])

@app.route('/validate',methods=['GET'])
def validateUserv():
    email = request.args.get("Email")
    password = request.args.get("CPassword")

    query = "select CustomerID,Email,CPassword from customer where Email = '"+email+"' AND CPassword = '"+password+"'"
    dta = getData(query)
    print(dta)

    if(not dta):
        fail_msg = [{
            "Status": "0"
        }]
        fail_j = json.dumps(fail_msg)
        return fail_j
    else:
        sucess_msg = [{
            "Status": "1",
            "CustomerID": dta[0][0]
        }]
        sucess_j = json.dumps(sucess_msg)
        return sucess_j

@app.route('/resetpassword',methods=['GET'])
def resetPassword():
    email = request.args.get("Email")
    query = "select CustomerID from customer WHERE Email = '"+email+"'"
    user_dt = getData(query)
    if(user_dt != []):
        ranno = random.randint(1000,9999)
        message = f"""
        Hi,\n
        Customer Please use below code to reset your password,\n\n
        Code : {ranno} \n\n
        Thank You.
        """
        print(message)

        r = requests.post("https://duropackaging.com/v5/email",json={
            "to":email,
            "subject":"Password Rest Request",
            "body":message
        })

        response = [{
            "Status":"1",
            "Code": ranno,
            "CustomerID": user_dt[0][0]
        }]

        msg = json.dumps(response)
        return msg
    else:
        response = [{
            "Status": "0"
        }]

        msg = json.dumps(response)
        return msg #User not found

@app.route('/setnewpassword',methods=['GET'])
def setNewPassword():
    customer_id = request.args.get("CustomerID")
    new_password = request.args.get("NewPassword")

    customer_current_data = requests.get("https://duropackaging.com/sv4/customers?id="+customer_id+"").json()
    customer_current_data[0]['CPassword'] = new_password

    new_customer_data = customer_current_data[0]
    r = requests.put("https://duropackaging.com/sv4/customers",json=new_customer_data)

    # Sending Welcome Mail

    recv_email = customer_current_data[0]['Email']
    subject = "Ebuy Account Password Reset"
    message = f"""
            Hi, {customer_current_data[0]['FirstName']} \n
            \n
            Your ebuy Account Password reset was successful.
            \n
            Have a nice day!
            Thank You.
        """

    threading.Thread(target=sendMail, args=(message, recv_email, subject)).start()

    # Sending Welcome Mail

    response = [{
        "Status": "1"
    }]

    msg = json.dumps(response)
    return msg  # Password Rest Successful


if __name__ == "__main__":
    app.run()

# ------------------ WEB SERVER ------------------