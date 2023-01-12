from email.message import EmailMessage
import ssl
import smtplib
from flask import Flask,request
from flask_cors import CORS

# ------------------ WEB SERVER ------------------
app = Flask(__name__)

CORS(app)

@app.route('/email',methods=['POST'])
def putData():
   try:
       data = request.get_json()

       email_sender = "" #email address
       email_password = "" #app password
       email_reciver = data['to']

       subject = data['subject']
       body = data['body']

       em = EmailMessage()
       em['from'] = email_sender
       em['to'] = email_reciver
       em['subject'] = subject
       em.set_content(body)

       context = ssl.create_default_context()

       with smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) as smtp:
           smtp.login(email_sender, email_password)
           smtp.sendmail(email_sender, email_reciver, em.as_string())

       return "Mail Sent !!"
   except:
      return "Mail Not Sent !!"

if __name__ == "__main__":
    app.run()

# ------------------ WEB SERVER ------------------
