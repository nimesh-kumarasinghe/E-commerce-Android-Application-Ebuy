import mysql.connector
from flask import Flask,request
import json
from flask_cors import CORS

import requests
import threading
# ------------------ DATABASE ------------------

dt = []

table_name = "users"
primary_key = "UserID"

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
@app.route('/users' , methods=['GET'])
def getdata():
   try:
       if(request.args.get("id")): #search by id (id -> parameter in URL?id="")
          dbdt = getData("select * from "+table_name+" where "+primary_key+" = '"+request.args.get("id")+"'")

       else: #getall
          dbdt  = getData("select * from "+table_name+"")

       counter = 0

       for x in dbdt: #config JSON
          dt.append( {
             "UserID":x[0],
             "UserType" : x[1],
             "FirstName" : x[2],
             "LastName": x[3],
             "Address":x[4],
             "City": x[5],
             "Country": x[6],
             "PostalCode": x[7],
             "PhoneNo": x[8],
             "Email": x[9],
             "UPassword": x[10],
          })
          counter = counter + 1

       json_ob = json.dumps(dt)
       return json_ob
   except Exception as e:
       return str(e)

def sendMail(msg,email,subject):
    message = msg
    r = requests.post("https://duropackaging.com/v5/email", json={
        "to": email,
        "subject": subject,
        "body": message
    })

@app.route('/users',methods=['POST'])
def putData():
   try:
      data = request.get_json() #get json data
      next_id = getMaxId()

      #insert query db (Add new data with -> , '"+data['json_id']+" )
      #INSERT INTO `vendors` (`id`, `first_name`, `last_name`, `email`, `password`) VALUES ('4', 'Haritha', 'Pahansith', 'cv', 'cv');
      query = "INSERT INTO `users` (`UserID`, `UserType`, `FirstName`, `LastName`, `Address`, `City`, `Country`, `PostalCode`, `PhoneNo`, `Email`, `UPassword`) VALUES ('"+next_id+"', '"+data['UserType']+"', '"+data['FirstName']+"', '"+data['LastName']+"', '"+data['Address']+"', '"+data['City']+"', '"+data['Country']+"', '"+data['PostalCode']+"', '"+data['PhoneNo']+"', '"+data['Email']+"', '"+data['UPassword']+"');"
      print(query)
      insertOrUpdateData(query)

      #Email Message
      msg = f"""
              Hi, {data['FirstName']} 
              \n
              Welcome to ebuy!
              \n
              We are happy to say that your vendor account was created successfully!\n
              You are able add product products and mange order order through our site.
              Let's start shopping
              \n
              Have a nice day!\n
              Let's start selling!!\n
              Ebuy
              """
      threading.Thread(target=sendMail,args=(msg,data['Email'],"Vendor Registration")).start()
      # Email Message

      return "DATA INSERTED"
   except:
      return "Error"


@app.route('/users', methods=['PUT'])
def updateData():
   try:
      data = request.get_json()#get json data

      #update query db - (use str(data['id']))
      #UPDATE `vendors` SET `first_name` = 'Harithax', `last_name` = 'Pahansithx', `email` = 'harithapahansith222@gmail.comx', `password` = '1234x' WHERE `vendors`.`id` = 6;
      query = "UPDATE `users` SET `UserType` = '"+data['UserType']+"', `FirstName` = '"+data['FirstName']+"', `LastName` = '"+data['LastName']+"', `Address` = '"+data['Address']+"', `City` = '"+data['City']+"', `Country` = '"+data['Country']+"', `PostalCode` = '"+data['PostalCode']+"', `PhoneNo` = '"+data['PhoneNo']+"', `Email` = '"+data['Email']+"', `UPassword` = '"+data['UPassword']+"' WHERE `users`.`UserID` = "+str(data['UserID'])+";"
      print(query)
      insertOrUpdateData(query)

      return "DATA UPDATED"
   except:
      return "Error"


@app.route('/users', methods=['DELETE'])
def deleteData():
   try:
      data = request.get_json()#get json data
      print(data)
      #delete query db
      query = "DELETE FROM `users` WHERE `users`.`UserID` = "+str(data['UserID'])+""
      print(query)
      insertOrUpdateData(query)

      return "DATA DELETED"
   except:
      return "Error"

# - other functions
@app.route('/validate',methods=['POST'])
def validateUser():
    data = request.get_json() #get use inputs

    query = "select UserID ,Email,UPassword from users where Email = '"+data['Email']+"' AND UPassword = '"+data['UPassword']+"'"
    dta = getData(query)
    print(dta)
    if(not dta):
        return "-1"
    else:
        print(dta)
        return str(dta[0][0])

#

if __name__ == "__main__":
    app.run()

# ------------------ WEB SERVER ------------------