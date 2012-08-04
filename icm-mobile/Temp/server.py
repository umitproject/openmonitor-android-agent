import socket
import sys
import base64

import os
from Crypto.Hash import SHA
from Crypto.Signature import PKCS1_v1_5
from Crypto.PublicKey import RSA

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = '127.0.0.1'
port = int(8000)
s.bind((host,port))
s.listen(1)
conn, addr = s.accept()
print 'client is at', addr
data = conn.recv(100000000) 
print data 


keyPair= RSA.generate(1024,os.urandom)
exp=65537L
mod=132154093015998082312691973720924575827554196704894579838717282264784967462399400136948430400183926220839549817688747147836616654311572290676639490493583998835967302973283264616855491040345668482818449690898870341382338809192254767386633969499087290088354409290108805187682862668712316074336021139914724230967L
publicKey= RSA.construct((exp,mod))

test=SHA.new("This is my message")
verifier = PKCS1_v1_5.new(publicKey)
signature=bytes([-117,62,-8])
for i in signature:
   print i
temp = verifier.verify(test, signature)
print "This is the temp : " + str(temp)
z = raw_input()
conn.close()
