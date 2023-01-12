import mysql.connector
from flask import Flask,request,send_from_directory
import json

from flask_cors import CORS
import os
from flask_uploads import UploadSet, configure_uploads, IMAGES
# ------------------ DATABASE ------------------

dt = []

table_name = "product"
primary_key = "ProductID"

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
           nextid = str((getData("SELECT MAX("+primary_key+") from "+table_name+"")[0][0]) + 1) #increment id by 1
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
@app.route('/products' , methods=['GET'])
def getdata():
   dt.clear()
   try:
       if(request.args.get("id")): #search by id (id -> parameter in URL?id="")
          dbdt = getData("select * from "+table_name+" where "+primary_key+" = '"+request.args.get("id")+"'")

       elif(request.args.get("userid")):  # search by id (id -> parameter in URL?id="")
           dbdt = getData("SELECT * FROM `product` WHERE `user_id` = '"+request.args.get("userid")+"'")
       elif (request.args.get("category_id")):  # search by id (id -> parameter in URL?id="")
           dbdt = getData("SELECT * FROM `product` WHERE `category_id` = '"+request.args.get("category_id")+"'")
       elif (request.args.get("search")):  # search by id (id -> parameter in URL?id="")
           dbdt = getData("SELECT * FROM `product` WHERE `ProductName` LIKE '%"+request.args.get("search")+"%' OR `PDescription` LIKE '%"+request.args.get("search")+"%'")
       else: #getall
          dbdt  = getData("select * from "+table_name+"")

       counter = 0

       for x in dbdt:  # config JSON
           dt.append({
               "ProductID": str(x[0]),
               "ProductName": str(x[1]),
               "PDescription": str(x[2]),
               "Price": str(x[3]),
               "Quantity": str(x[4]),
               "imgLocation": str(x[5]),
               "category_id": str(x[6]),
               "user_id": str(x[7])
           })
           counter = counter + 1

       json_ob = json.dumps(dt)
       return json_ob
   except:
      return "Error"


@app.route('/products',methods=['POST'])
def putData():
   try:
      data = request.get_json() #get json data
      next_id = getMaxId()
      #insert query db (Add new data with -> , '"+data['json_id']+" )
      #INSERT INTO `product` (`product_id`, `name`, `description`, `price`, `img_loc`, `categoryid`, `sellerid`) VALUES ('1', 'test name', 'testing desc', '12', 'https://www.google.com', '1', '1');
      query = "INSERT INTO `product` (`ProductID`, `ProductName`, `PDescription`, `Price`, `Quantity`, `imgLocation`, `category_id`, `user_id`) VALUES ('"+str(next_id)+"', '"+data['ProductName']+"', '"+data['PDescription']+"', '"+data['Price']+"', '"+data['Quantity']+"', '"+data['imgLocation']+"', '"+data['category_id']+"', '"+data['user_id']+"');"
      print(query)
      insertOrUpdateData(query)

      return "DATA INSERTED"
   except:
      return "Error"


@app.route('/products', methods=['PUT'])
def updateData():
   try:
      data = request.get_json()#get json data

      #update query db - (use str(data['id']))
      #UPDATE `product` SET `name` = 'test namex', `description` = 'testing descx', `price` = '1212', `img_loc` = 'https://www.google.comx', `categoryid` = '12', `sellerid` = '12' WHERE `product`.`product_id` = 1;
      query = "UPDATE `product` SET `ProductName` = '"+data['ProductName']+"', `PDescription` = '"+data['PDescription']+"', `Price` = '"+data['Price']+"', `Quantity` = '"+data['Quantity']+"', `imgLocation` = '"+data['imgLocation']+"', `category_id` = '"+data['category_id']+"', `user_id` = '"+data['user_id']+"' WHERE `product`.`ProductID` = "+str(data['ProductID'])+";"
      print(query)
      insertOrUpdateData(query)

      return "DATA UPDATED"
   except:
      return "Error"


@app.route('/products-del', methods=['POST'])
def deleteData():
   try:
      data = request.get_json()#get json data
      print(data)
      #print(data['product_id'])
      #delete query db
      query = "DELETE FROM `product` WHERE `product`.`ProductID` = "+str(data['ProductID'])+""

      del_orders = "DELETE from orders where orders.OrderID IN (select order_products.order_id from order_products where order_products.product_id = '"+str(data['ProductID'])+"')"
      del_order_p = "DELETE from order_products WHERE order_products.product_id = '"+str(data['ProductID'])+"'"
      insertOrUpdateData(del_orders)
      insertOrUpdateData(del_order_p)

      insertOrUpdateData(query)
      print(query)
      return "DATA DELETED"
   except:
      return "Error"

#Upload Image


basedir = os.path.abspath(os.path.dirname(__file__))
app.config['UPLOADED_PHOTOS_DEST'] = os.path.join(basedir, 'uploads')
photos = UploadSet('photos', IMAGES)
configure_uploads(app, photos)

@app.route('/images', methods=['GET','POST'])
def upload_file():
    file = request.files['photo']
    file_ex = file.filename.split('.')[1]
    print(file_ex)
    file.filename = f"img-{str(getMaxId())}.{file_ex}"
    filename = photos.save(file)
    print(filename)
    return filename

UPLOADS_FOLDER = './uploads/'

@app.route("/imgs/<path:filename>")
def images(filename):
    sp = os.path.join(basedir, 'uploads')
    return send_from_directory(sp,filename)
#


@app.route('/category', methods=['GET'])
def getCategory():
    cate_id = request.args.get("id")
    query = "SELECT `CategoryName` FROM `product_category` WHERE `CategoryID` = '"+str(cate_id)+"'"
    data = getData(query)
    response = [{
        "CategoryName": data[0][0]
    }]
    msg = json.dumps(response)
    return msg

if __name__ == "__main__":
    app.run()

# ------------------ WEB SERVER ------------------