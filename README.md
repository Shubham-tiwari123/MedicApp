## MedicApp
_MedicApp is a platform for hospitals that help them to store their patient 
record safely using blockchain. These records can be accessed by those 
hospitals which are connected through the platform using the patient-id._

_It helps the hospital to get the patient medical history in very less time, which is 
useful during an emergency. It reduces the patient burden for maintaining the document of 
his/her medical record._ 

#### Why Blockchain?
1. Blockchain only allows insertion of a new record. Hence no medical block can 
   be deleted once it gets stored in the chain.
2. It uses the hashing technique, which is used to verify if a record is 
   tempered or not.
3. It uses an encryption technique so that a valid person can only view the data. 
   This provides security to the data stored in blocks.
   
#### Tech Stack
JAVA, Servlet, JSP, MongoDB, RestAPI, JSON, Maven, Socket, RSA Encryption Algorithm,
SHA-256 Hashing Algorithm, Postman

#### Implementation
1. Used JAVA Socket to exchange the keys between Medic Server and Hospital(Client).
2. Client(Hospital) will first calculate the hashvalue for the block(medical record).
Then this hasvalue along with the medical record is encrypted using client private
key and the encrypted data is send to server. 
3. On server side first the data is decrypted using client public-key. This way the
server verifies weather the data is transmitted from authorised source or not. Once 
data gets decrypted then the hashvalue for the record is again calculated and this
value is matched with the hashvalue sent from client. If the value matched then it
is considered that the value is not tempered.
4. If data is secured then it is encrypted using  server public-key and stored in 
mongodb.
    
#### Future Expansion    

1. Develop a mobile application for patients so that they then also view their
   medical records.
2. Connect insurance companies to this platform. This will help patients to file 
   for their refunds easily.

